#include "utils.hh"

#include <string>

void throw_apl_exception( JNIEnv *env, const std::string &message )
{
    jclass exceptionCl = env->FindClass( "org/gnu/apl/AplException" );
    if( exceptionCl != NULL ) {
        env->ThrowNew( exceptionCl, message.c_str() );
    }
}
