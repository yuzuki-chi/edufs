package Test;

import java.io.*;

public class EntryProgram {
    public static void main(String ... args) throws IOException {
        File file = new File("/Users/yuzukichi/IdeaProjects/DeviceIO.DeviceIO/src/Test/a");
        InputStream is = new FileInputStream(file);

        byte[] b = new byte[255];
        int offset = 0;
        int len = 255;

        int read = is.read(b, offset, len);
        System.out.println("読み込んだバイト数: " + read);
        for(int i=0; i<b.length; i++) { System.out.print((char)b[i]); }

        is.close();
    }
}
