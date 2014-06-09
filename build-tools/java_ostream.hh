#ifndef JAVA_OSTREAM_HH
#define JAVA_OSTREAM_HH

#include <streambuf>
#include <iostream>
#include <ostream>

#include <jni.h>

using namespace std;

void write_string_to_java_stream( JNIEnv *env, jobject writer, const char *s, std::streamsize n );

template<class T, class traits = std::char_traits<T> >
class JavaStreambuf : public std::basic_streambuf<T, traits>
{
protected:
    typename traits::int_type overflow( typename traits::int_type c ) {
        std::cout << "overflow called" << std::endl;
        return traits::not_eof( c );
    }

    std::streamsize xsputn( const char *s, std::streamsize n ) {
        std::cout << "xsputn:" << s << ":" << std::endl;
        write_string_to_java_stream( env, java_writer, s );
        return strlen( s );
    }

    JNIEnv *env;
    jobject java_writer;

public:
    void init( JNIEnv *env_in, jobject java_writer_in ) { env = env_in; java_writer = java_writer_in; }
};

template<class T, class traits = std::char_traits<T> >
class BasicJavaOStream : public std::basic_ostream<T, traits>
{
public:
    BasicJavaOStream ( JNIEnv *env, jobject java_writer )
        : std::basic_ios<T, traits>( &sbuf ), std::basic_ostream<T, traits>( &sbuf ) {
        sbuf.init( env, java_writer );
    }

private:
    JavaStreambuf<T, traits> sbuf;
};

typedef BasicJavaOStream<char> JavaOStream;
typedef BasicJavaOStream<wchar_t> WJavaOStream;

#endif
