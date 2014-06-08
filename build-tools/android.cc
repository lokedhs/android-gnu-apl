#include <string>

#include "android.hh"
#include "utils.hh"
#include "java_ostream.hh"

#include "org_gnu_apl_Native.h"

#include "Command.hh"

int init_apl( int argc, const char *argv[] );

JNIEXPORT jint JNICALL Java_org_gnu_apl_Native_init( JNIEnv *, jclass )
{
    const char *argv[] = { "apl", "--silent", "--rawCIN", NULL };

    int ret = init_apl( 3, argv );
    return ret;
}

JNIEXPORT void JNICALL Java_org_gnu_apl_Native_evalWithIo( JNIEnv *env, jclass,
                                                           jstring expr,
                                                           jobject cin,
                                                           jobject cout,
                                                           jobject cerr)
{
    JavaOStream cin_java( env, cin );
    JavaOStream cout_java( env, cout );
    JavaOStream cerr_java( env, cerr );

    JniStringWrapper expr_java_str( env, expr );
    UCS_string expr_ucs( expr_java_str.get_string() );

    Command::process_line( expr_ucs );
}
