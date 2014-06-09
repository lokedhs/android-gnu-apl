#include <string>

#include "android.hh"
#include "utils.hh"
#include "java_ostream.hh"

#include "org_gnu_apl_Native.h"

#include "Command.hh"

template<class T, class traits = std::char_traits<T> >
class JavaAplStreambuf : public std::basic_streambuf<T, traits>
{
public:
    JavaAplStreambuf() : env( NULL ), writer( NULL ) {}
    void set_env( JNIEnv *env_in ) { env = env_in; }
    void set_writer( jobject writer_in ) { writer = writer_in; }

protected:
    typename traits::int_type overflow( typename traits::int_type c ) {
        return traits::not_eof( c );
    }

    std::streamsize xsputn( const typename traits::char_type *s, std::streamsize n ) {
        if( env != NULL && writer != NULL ) {
            write_string_to_java_stream( env, writer, s, n );
        }
        return n;
    }

    JNIEnv *env;
    jobject writer;
};

JavaAplStreambuf<char> cin_streambuf;
JavaAplStreambuf<char> cout_streambuf;
JavaAplStreambuf<char> cerr_streambuf;
JavaAplStreambuf<char> uerr_streambuf;

std::ostream CIN( &cin_streambuf );
std::ostream COUT( &cout_streambuf );
std::ostream CERR( &cerr_streambuf );
std::ostream UERR( &uerr_streambuf );

int init_apl( int argc, const char *argv[] );

JNIEXPORT jint JNICALL Java_org_gnu_apl_Native_init( JNIEnv *env, jclass )
{
    const char *argv[] = { "apl", "--silent", "--rawCIN", NULL };

    cin_streambuf.set_env( env );
    cout_streambuf.set_env( env );
    cerr_streambuf.set_env( env );
    uerr_streambuf.set_env( env );

    int ret = init_apl( 3, argv );
    return ret;
}

JNIEXPORT void JNICALL Java_org_gnu_apl_Native_evalWithIo( JNIEnv *env, jclass,
                                                           jstring expr,
                                                           jobject cin,
                                                           jobject cout,
                                                           jobject cerr,
                                                           jobject uerr )
{
    cin_streambuf.set_writer( cin );
    cout_streambuf.set_writer( cout );
    cerr_streambuf.set_writer( cerr );
    uerr_streambuf.set_writer( uerr );

    JniStringWrapper expr_java_str( env, expr );
    UTF8_string utf8( expr_java_str.get_string() );
    UCS_string expr_ucs( utf8 );

    try {
        Command::process_line( expr_ucs );
    }
    catch( JavaExceptionThrown &e ) {
        // Exception is already set to the correct value
    }
    catch( ErrorWithComment &e ) {
        jclass exceptionCl = env->FindClass( "org/gnu/apl/AplException" );
        if( exceptionCl != NULL ) {
            env->ThrowNew( exceptionCl, e.get_message().c_str() );
        }
    }

    cin_streambuf.set_writer( NULL );
    cout_streambuf.set_writer( NULL );
    cerr_streambuf.set_writer( NULL );
    uerr_streambuf.set_writer( NULL );
}
