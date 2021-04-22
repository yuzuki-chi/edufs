package Test;

import FileSystem.Edufs.*;
import FileSystem.Edufs.FileObj;
import FileSystem.FileSystem;

public class Main {
    public final static String deviceName = "/dev/disk2s1";

    /*APIの使用例*/
    public static void main(String ... args) throws Exception {
        Edufs edufs = new Edufs(deviceName);

        //ex1). 指定したディレクトリ以下のファイルをtreeコマンドのように出力する
        edufs.tree( edufs.getRoot() );
        System.out.println("NEXT---");

        //ex2). 指定したディレクトリ直下のファイルをlsコマンドのように出力する
        edufs.ls( edufs.getRoot() );
        System.out.println("NEXT---");

        //ex3). 指定したファイルをopenし, 指定したバイト数readし, closeする
        FileObj file = edufs.open( "hello.txt", "r" );

        int size = 4096;
        byte[] b = new byte[size];
        long offset = 0;

        int r = file.read(b, offset, size);
        System.out.println("読み出したバイト数: " + r);
        System.out.println( new String(b) );

        file.close();
    }
}
