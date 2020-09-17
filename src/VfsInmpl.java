import java.util.ArrayList;
import java.util.List;

public class VfsInmpl implements Vfs {
    private static List<FileImpl> openFileDescList = new ArrayList<FileImpl>();

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
        FileImpl f = (FileImpl) openFileDescList.get(fd);
        int ret = f.getPage(b, 0);
        return ret;
    }
}
