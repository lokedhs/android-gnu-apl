#ifndef JAVA_OSTREAM_HH
#define JAVA_OSTREAM_HH

#include <streambuf>
#include <iostream>
#include <ostream>

#include <jni.h>

class ErrorWithComment
{
public:
    ErrorWithComment( const std::string &message_in ) : message( message_in ) {}
    std::string get_message( void ) { return message; }

private:
    std::string message;
};

class JavaExceptionThrown : public ErrorWithComment
{
public:
    JavaExceptionThrown( const std::string &message_in ) : ErrorWithComment( message_in ) {}
};

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
        write_string_to_java_stream( env, java_writer, s, n );
        return n;
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
