#include "java_ostream.hh"

void write_string_to_java_stream( JNIEnv *env, jobject writer, const char *s, std::streamsize n )
{
    char str_buf[n + 1];
    memcpy( str_buf, s, n );
    str_buf[n] = 0;
    jstring java_string = env->NewStringUTF( str_buf );

    jclass cl = env->FindClass( "java/io/Writer" );
    if( cl == NULL ) {
        throw "foo";
    }
    jmethodID append = env->GetMethodID( cl, "append", "(Ljava/lang/CharSequence;)Ljava/io/Writer;" );
    if( append == NULL ) {
        throw "bar";
    }
    env->CallObjectMethod( writer, append, java_string );
}
