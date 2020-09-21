package File;

import FileSystem.*;
import ext2.*;

import java.util.ArrayList;
import java.util.List;

public class VfsInmpl implements Vfs {
    private static List<FileImpl> openFileDescList = new ArrayList<FileImpl>();
    private static VfsMount mnt;

    private static VfsInmpl vfsImpl = new VfsInmpl();
    private VfsInmpl()
    {
    }

    /**
     * 指定したファイルをオープンしてfdを返す
     * @param pathname オープンするファイルの絶対パス
     * @param mode アクセス許可
     * @return ファイルディスクリプタ. エラー時は-1を返す
     */
    public static int open(String pathname, int mode) {
        FileImpl file = new FileImpl(pathname, mode);
        openFileDescList.add(file);
        return file.getFd();
    }

    /**
     * 指定したバイト分だけfdから読み込みを行う. 読み込んだバイト分だけファイルオフセットを進める
     * @param fd 読み込むファイルのファイルディスクリプタ
     * @param b ファイルから読み込んだデータを格納する配列
     * @param size ファイルから読み込みたいデータバイト数
     * @return 実際に読み込んだバイト数. ファイルオフセットがsizeを超えた場合は0を返す.
     */
    public static int read(int fd, byte[] b, int size) {
        final int PAGE_SIZE = 4096;
        byte[] getData = new byte[PAGE_SIZE];
        int ret = 0;

        FileImpl f = openFileDescList.get(fd);
        if (size < PAGE_SIZE) {
            f.getPage(getData, 0);
            for (int i=0; i<size; i++) {
                b[i] = getData[i];
            }
            ret = size;
        } else {
            //TODO: sizeに応じた回数readをして読み出す
        }
        return ret;
    }
}
