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

    /**
     * ファイルシステムをマウントするmountシステムコールと同様の動作を想定
     * @param source fsを構成するデバイス名. デバイスが無い場合はダミーを指定
     * @param target fsをマウントするディレクトリ名
     * @param fstype マウントするfsのファイルシステムタイプ
     * @return 成功時は0, エラー時は-1を返す
     */
    public static int mount(String source, String target, String fstype) {
        int retval = 0;
        long dataPage; //おそらくインスタンス化必須
        long typePage; //こちらもインスタンス化必須
        long devPage;
        int flags = 0; //TODO これはここにあるべきでは無い

//        lock_kernel();      //カーネルロック

        FileSystemTypeObjectImpl type = new FileSystemTypeObjectImpl(fstype); //ファイルシステム名から対応するfile_system_typeディスクリプタを取得
        SuperBlock sb;
        mnt = new VfsMount();

        //TODO ファイルシステムディスクリプタを初期化（各リストや参照カウンタ、デバイス名など） alloc_vfsmnt(name)を参照

        //TODO 初期化したスーパーブロックオブジェクトを取得
        sb = type.getSb(type, flags, source); //fsTypeObjからsbを見つける
        //この時点のsbは既にext2のsbである必要がある.

        //TODO マウント情報を初期化していく
        mnt.setSb(sb);
        mnt.setRoot(sb.getSroot());
        mnt.setMountpoint(sb.getSroot());
        mnt.setParent(mnt);
//        mnt.setNamespace(namespace);
//        putFilesystem(type); //fsTypeObjectDescを追加してるんかな？

//        unlock_kernel();    //カーネルロック解除

        return retval;
    }
}
