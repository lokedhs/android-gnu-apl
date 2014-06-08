#include "android.hh"
#include "utils.hh"

#include "org_gnu_apl_Native.h"

JNIEXPORT jobject JNICALL Java_org_gnu_apl_Native_evalExpression( JNIEnv *env, jclass cl, jstring expr )
{
    JniStringWrapper s( env, expr );
    
    Executable *statements = 0;
    try {
        statements = StatementList::fix( s.get_string(), LOC );
    }
    catch( Error err ) {
        jclass execptionCl = env->FindClass( "org/gnu/apl/AplExecException" );
        if( execptionCl == NULL ) {
            return NULL;
        }
        jmethodID constructor = env->GetMethodID( execptionCl, "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V" );
        if( constructor == NULL ) {
            return NULL;
        }
        jstring line1 = env->NewStringUTF( UTF8_string( err.get_error_line_1() ).c_str() );
        jstring line2 = env->NewStringUTF( UTF8_string( err.get_error_line_2() ).c_str() );
        jstring line3 = env->NewStringUTF( UTF8_string( err.get_error_line_3() ).c_str() );
        jobject ex = env->NewObject( execptionCl, constructor, line1, line2, line3 );
        if( ex != NULL ) {
            env->Throw( (jthrowable)ex );
        }
        return NULL;
    }

    if( statements == NULL ) {
        throw_apl_exception( env, "Parse error" );
        return NULL;
    }

    // Section grabbed from Command.cc
    {
        const Token_string & body = statements->get_body();
        if( body.size() == 3
            && body[0].get_tag() == TOK_ESCAPE
            && body[1].get_Class() == TC_END
            && body[2].get_tag() == TOK_RETURN_STATS )
        {
          delete statements;

          // remove all SI entries up to (including) the next immediate
          // execution context
          //
          for(bool goon = true ; goon ; ) {
              StateIndicator * si = Workspace::SI_top();
              if( si == 0 ) {
                  break;   // SI empty
              }

              const Executable * exec = si->get_executable();
              Assert(exec);
              goon = exec->get_parse_mode() != PM_STATEMENT_LIST;
              si->escape();   // pop local vars of user defined functions
              Workspace::pop_SI(LOC);
          }
          // TODO: is null the right thing to return at this point?
          return NULL;
        }
   }

    Workspace::push_SI(statements, LOC);

    for (;;) {
        //
        // NOTE that the entire SI may change while executing this loop.
        // We should therefore avoid references to SI entries.
        //
        Token token = Workspace::SI_top()->get_executable()->execute_body();

// Q(token)

        // start over if execution has pushed a new context
        //
        if (token.get_tag() == TOK_SI_PUSHED) {
            continue;
        }

        // maybe call EOC handler and repeat if true returned
        //
    check_EOC:
        if (Workspace::SI_top()->call_eoc_handler(token)) {
            continue;
        }

        // the far most frequent cases are TC_VALUE and TOK_VOID
        // so we handle them first.
        //
        if (token.get_Class() == TC_VALUE || token.get_tag() == TOK_VOID ) {
            if (Workspace::SI_top()->get_executable()->get_parse_mode() == PM_STATEMENT_LIST) {
                if (attention_raised) {
                    attention_raised = false;
                    interrupt_raised = false;
                    ATTENTION;
                }

                break;   // will return to calling context
            }

            Workspace::pop_SI(LOC);

            // we are back in the calling SI. There should be a TOK_SI_PUSHED
            // token at the top of stack. Replace it with the result from
            //  the called (just poped) SI.
            //
            {
                Prefix & prefix =
                    Workspace::SI_top()->get_prefix();
                Assert(prefix.at0().get_tag() == TOK_SI_PUSHED);

                copy_1(prefix.tos().tok, token, LOC);
            }
            if (attention_raised) {
                attention_raised = false;
                interrupt_raised = false;
                ATTENTION;
            }

            continue;
        }

        if (token.get_tag() == TOK_BRANCH) {
            StateIndicator * si = Workspace::SI_top_fun();

            if (si == 0) {
                Workspace::more_error() = UCS_string(
                    "branch back into function (→N) without "
                    "suspended function");
                SYNTAX_ERROR;   // →N without function,
            }

            // pop contexts above defined function
            //
            while (si != Workspace::SI_top()) {
                Workspace::pop_SI(LOC);
            }

            const Function_Line line = Function_Line(token.get_int_val());

            if (line == Function_Retry) {
                si->retry(LOC);
            }
            else {
                si->goon(line, LOC);
            }
            continue;
        }

        if (token.get_tag() == TOK_ESCAPE) {
            // remove all SI entries up to (including) the next immediate
            // execution context
            //
            for (bool goon = true ; goon ;) {
                StateIndicator * si = Workspace::SI_top();
                if (si == 0)   break;   // SI empty

                const Executable * exec = si->get_executable();
                Assert(exec);
                goon = exec->get_parse_mode() != PM_STATEMENT_LIST;
                si->escape();   // pop local vars of user defined functions
                Workspace::pop_SI(LOC);
            }
            return NULL;

            Assert(0 && "not reached");
        }

        if (token.get_tag() == TOK_ERROR) {
            // clear attention and interrupt flags
            //
            attention_raised = false;
            interrupt_raised = false;

            // check for safe execution mode. Entries in safe execution mode
            // can be far above the current SI entry. The EOC handler will
            // unroll the SI stack.
            //
            for (StateIndicator * si = Workspace::SI_top() ; si ; si = si->get_parent()) {
                if (si->get_safe_execution()) {
                    // pop SI entries above the entry with safe_execution
                    //
                    while (Workspace::SI_top() != si) {
                        Workspace::pop_SI(LOC);
                    }

                    goto check_EOC;
                }
            }
            
            Workspace::get_error()->print(CERR);
            if (Workspace::SI_top()->get_level() == 0) {
                Value::erase_stale(LOC);
                IndexExpr::erase_stale(LOC);
            }
            return NULL;
        }

        // we should not come here.
        //
        Q1(token)  Q1(token.get_Class())  Q1(token.get_tag())  FIXME;
    }

    // pop the context for the statements
    //
    Workspace::pop_SI(LOC);

    return NULL;
}
