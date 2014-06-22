package com.dhsdevelopments.aplandroid;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.*;

public class Installer
{
    private static final String BASENAME = "files";

    public static File install( Context context ) throws IOException {
        File dest = context.getDir( BASENAME, Context.MODE_PRIVATE );
        File prefsFile = new File( dest, "etc/gnu-apl.d/preferences" );
        if( !prefsFile.exists() ) {
            Log.d( "trigger file not found, installing data" );
            AssetManager am = context.getAssets();
            copyDirs( am, BASENAME, dest );
            symlink( "lib_file_io.so", "lib_file_io.so.0.0.0" );
            symlink( "lib_file_io.so.0", "lib_file_io.so.0.0.0" );
            symlink( "lib_template_F0.so", "lib_template_F0.so.0.0.0" );
            symlink( "lib_template_F0.so.0", "lib_template_F0.so.0.0.0" );
            symlink( "lib_template_F12.so", "lib_template_F12.so.0.0.0" );
            symlink( "lib_template_F12.so.0", "lib_template_F12.so.0.0.0" );
            symlink( "lib_template_OP1.so", "lib_template_OP1.so.0.0.0" );
            symlink( "lib_template_OP1.so.0", "lib_template_OP1.so.0.0.0" );
            symlink( "lib_template_OP2.so", "lib_template_OP2.so.0.0.0" );
            symlink( "lib_template_OP2.so.0", "lib_template_OP2.so.0.0.0" );
        }
        else {
            Log.d( "files already installed" );
        }
        return dest;
    }

    private static void symlink( String linkName, String file ) {

    }

    private static void copyDirs( AssetManager am, String srcRoot, File destRoot ) throws IOException {
        ensureDir( destRoot, "etc" );
        ensureDir( destRoot, "etc/gnu-apl.d" );

        copyFile( am, srcRoot, destRoot, "etc/gnu-apl.d", "preferences" );
    }

    private static void ensureDir( File destRoot, String name ) {
        File dir = new File( destRoot, name );
        if( dir.isFile() ) {
            throw new RuntimeException( dir.getPath() + " should be a directory, was a file" );
        }
        if( !dir.mkdir() ) {
            Log.w( "Directory already exists: " + dir.getPath() );
        }
    }

    @SuppressWarnings("TryFinallyCanBeTryWithResources")
    private static void copyFile( AssetManager am, String srcRoot, File destRoot, String dir, String fileName ) throws IOException {
        InputStream in = am.open( srcRoot + "/" + dir + "/" + fileName );
        try {
            OutputStream out = new FileOutputStream( new File( destRoot, dir + "/" + fileName ) );
            try {
                byte[] buf = new byte[1024 * 16];
                int n;
                while( (n = in.read( buf )) != -1 ) {
                    out.write( buf, 0, n );
                }
            }
            finally {
                out.close();
            }
        }
        finally {
            in.close();
        }
    }
}
