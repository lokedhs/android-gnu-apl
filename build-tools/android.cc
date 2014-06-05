#include <string>

#include "org_gnu_apl_Native.h"

#include "Executable.hh"

int init_apl( int argc, const char *argv[] );

class JniStringWrapper
{
public:
    JniStringWrapper( JNIEnv *env_in, jstring jnistring_in )
        : env( env_in ), jnistring( jnistring_in )
        {
            native_string = env->GetStringUTFChars( jnistring, 0 );
        }

    ~JniStringWrapper() { env->ReleaseStringUTFChars( jnistring, native_string ); }
    const char *get_string( void ) { return native_string; }

private:
    JNIEnv *env;
    jstring jnistring;
    const char *native_string;
};

/*
class JniStringReference
{
public:
    JniStringReference( JNIEnv *env_in, const std::string &string_in )
        : env( env_in )
        {
            env->NewStringUTF( string_in.c_str() );
        }

    ~JniStringReference() { env->DeleteLocalRef( jnistring ); }
    jstring get_jstring( void ) { return jnistring; }

private:
    JNIEnv *env;
    jstring jnistring;
};
*/

void throw_apl_exception( JNIEnv *env, const std::string &message )
{
    jclass exceptionCl = env->FindClass( "org/gnu/apl/AplException" );
    if( exceptionCl != NULL ) {
        env->ThrowNew( exceptionCl, message.c_str() );
    }
}

JNIEXPORT jint JNICALL Java_org_gnu_apl_Native_init( JNIEnv *env, jclass )
{
    const char *argv[] = { "apl", "--silent", "--rawCIN", NULL };

    int ret = init_apl( 3, argv );
    return ret;
}

JNIEXPORT jobject JNICALL Java_org_gnu_apl_Native_evalExpression( JNIEnv *env, jclass cl, jstring expr )
{
    JniStringWrapper s( env, expr );
    
    Executable *statements = 0;
    try {
        statements = StatementList::fix( s.get_string(), LOC );
    }
    catch (Error err) {
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
        env->Throw( (jthrowable)ex );
        return NULL;
    }

    if( statements == NULL ) {
        throw_apl_exception( env, "Parse error" );
        return NULL;
    }

    return NULL;
}
