#include <string>

#include "android.hh"
#include "utils.hh"

#include "org_gnu_apl_Native.h"

int init_apl( int argc, const char *argv[] );

JNIEXPORT jint JNICALL Java_org_gnu_apl_Native_init( JNIEnv *env, jclass )
{
    const char *argv[] = { "apl", "--silent", "--rawCIN", NULL };

    int ret = init_apl( 3, argv );
    return ret;
}
