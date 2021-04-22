package FileSystem.Block;

import java.io.*;

/**
 * Blocks class
 * 指定したファイルの指定したブロック範囲をBlock型で読み出す
 */
public class Blocks {
    private final RandomAccessFile raf;
    private final long offset;
    private final int length;

    public Blocks(RandomAccessFile raf, long offset, int length) {
        this.raf = raf;
        this.offset = offset;
        this.length = length;
    }

    public byte[] read() {
        /* for[ Block new -> read -> 必要に応じてエンディアンなど ] */
        byte[] b = new byte[length];
        for(int cnt = 0; cnt < length; cnt++) {
            b[cnt] = new Block(raf, offset + Integer.toUnsignedLong(cnt)).read();
        }
        return b;
    }

    /**
     * @param b 書き込みを行うバイト列
     * @return int 書き出したバイト数
     */
    public int write( byte[] b ) {
        /* todo: 必要に応じてエンディアン変換が必須 */
        int readc = 0;
        for (int cnt = 0; cnt < length; cnt++) {
            readc += new Block(raf, offset).write(b[cnt]);
        }
        return 0;
    }
}
