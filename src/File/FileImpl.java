package File;

import java.io.FileInputStream;
import java.io.IOException;

public class FileImpl implements File {
    final int PAGE_SIZE = 4096;

    private String pathname;
    private int mode, fd;
    private long fileAddr;
    static int fdcnt = 0;

    public FileImpl(String pathname, int mode) {
        this.fd = fdcnt++;
        this.pathname = pathname;
        this.mode = mode;
    }

    /**
     * @param b ファイルから読み込んだデータを格納する配列
     * @param pageOffset 読み込むファイルの相対オフセット
     * @return 実際に読み込んだバイト数. エラー時は-1を返す
     */
    public int getPage(byte[] b, int pageOffset) {
        int ret = 0;
        try {
            FileInputStream fis = new FileInputStream(pathname);
            ret = fis.read(b, (int) (fileAddr + (PAGE_SIZE * pageOffset)), PAGE_SIZE);
        } catch (IOException e) {
            return -1;
        }
        return ret;
    }

    public int getFd() { return fd; }
}
