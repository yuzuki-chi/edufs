package Test;

import FileSystem.Block.Blocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class BlockTest {
    public static void main(String[] args) {
        File file = new File("/Users/yuzukichi/IdeaProjects/DeviceIO/src/Test/sample.txt");

        try {

            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            byte[] b = new Blocks(raf, 0, 30).read();
            System.out.println(new String(b));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
