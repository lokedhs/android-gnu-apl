#include "java_ostream.hh"

void write_string_to_java_stream( JNIEnv *env, jobject writer, const char *s )
{
    jstring java_string = env->NewStringUTF( s );

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
