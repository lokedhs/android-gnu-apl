#ifndef UTILS_HH
#define UTILS_HH

#include "jni.h"

#include <string>

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

void throw_apl_exception( JNIEnv *env, const std::string &message );

#endif
