//#include <android/log.h>
#include <string>
#include <vector>
#include <string.h>
#include <stdlib.h>

#include "android.hh"
#include "utils.hh"
#include "java_ostream.hh"

#include "org_gnu_apl_Native.h"

#include "Command.hh"

static jmethodID append;

template<class T, class traits = std::char_traits<T> >
class JavaAplStreambuf : public std::basic_streambuf<T, traits>
{
public:
    JavaAplStreambuf() : env( NULL ), writer( NULL ) {}
    void set_writer( JNIEnv *env_in, jobject writer_in ) { env = env_in; writer = writer_in; }
    void unset_writer( void ) { env = NULL; writer = NULL; }

protected:
    void put_buffer( const typename traits::char_type *s, std::streamsize n ) {
        if( env != NULL && writer != NULL ) {
            for( int i = 0 ; i < n ; i++ ) {
                buf.push_back( s[i] );
            }
            int end = buf.size();
            // At this point we might have a broken UTF-8 sequence at the end
            if( (buf[buf.size() - 1] & 0x80) == 0x80 ) {
                // OK, last character is part of a multibyte sequence
                // Find the start of the sequence
                int p = end - 1;
                while( p > 0 && (buf[p] & 0xC0) != 0xC0 ) {
                    p--;
                }
                if( (buf[p] & 0xC0) == 0xC0 ) {
                    // p now points to the start of the final multibyte sequence
                    // Check if this sequence is broken
                    int num_ones = 0;
                    int v = static_cast<int>(buf[p]) & 0xFF;
                    while( v & 0x80 ) {
                        v <<= 1;
                        num_ones++;
                    }
                    // v now contains the number of left-most bits
                    // this number is equal to the size of the multibyte sequence
                    if( num_ones > end - p ) {
                        end = p;
                    }
                }
                else {
                    end = p;
                }
            }

            if( end > 0 ) {
                char result_buf[end + 1];
                for( int i = 0 ; i < end ; i++ ) {
                    result_buf[i] = buf[i];
                }
                result_buf[end] = 0;

                jstring buf_javastring = env->NewStringUTF( result_buf );
                if( buf_javastring == NULL ) {
                    throw new JavaExceptionThrown( "Error creating string" );
                }

                jobject ret = env->CallObjectMethod( writer, append, buf_javastring );
                if( env->ExceptionCheck() ) {
                    env->DeleteLocalRef( buf_javastring );
                    throw JavaExceptionThrown( "Error writing string" );
                }

                buf.erase( buf.begin(), buf.begin() + end );

                env->DeleteLocalRef( ret );
                env->DeleteLocalRef( buf_javastring );
            }
        }
    }

    typename traits::int_type overflow( typename traits::int_type c ) {
        typename traits::char_type buf[2];
        buf[0] = c;
        buf[1] = 0;
        put_buffer( buf, 1 );
        return traits::not_eof( c );
    }

    std::streamsize xsputn( const typename traits::char_type *s, std::streamsize n ) {
        put_buffer( s, n );
        return n;
    }

    JNIEnv *env;
    jobject writer;
    std::vector<typename traits::char_type> buf;
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

JNIEXPORT jint JNICALL Java_org_gnu_apl_Native_init( JNIEnv *env, jclass, jstring path_jstring )
{
    const char *argv[] = { "apl", "--silent", "--rawCIN", NULL };

    JniStringWrapper path_string( env, path_jstring );

    setenv( "APL_LIB_ROOT", path_string.get_string(), 1 );

    jclass javaIoWriterCl = env->FindClass( "java/io/Writer" );
    if( javaIoWriterCl == NULL ) {
        return 0;
    }

    append = env->GetMethodID( javaIoWriterCl, "append", "(Ljava/lang/CharSequence;)Ljava/io/Writer;");
    if( append == NULL ) {
        return 0;
    }

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
    cin_streambuf.set_writer( env, cin );
    cout_streambuf.set_writer( env, cout );
    cerr_streambuf.set_writer( env, cerr );
    uerr_streambuf.set_writer( env, uerr );

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

    cin_streambuf.unset_writer();
    cout_streambuf.unset_writer();
    cerr_streambuf.unset_writer();
    uerr_streambuf.unset_writer();
}
