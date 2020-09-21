package FileSystem;

import ext2.Ext2fs;
import ext2.Ext2fsSuperBlock;

import java.util.List;

public class FileSystemTypeObjectImpl implements FileSystemTypeObject {
    String name; //ファイルシステム名, mountシステムコールでユーザが指定するファイルシステムタイプ
    int fsFlags; //ファイルシステムタイプのフラグ. FSのmount時の挙動をVFSに通知する
                    /*
                        #define FS_REQUIRES_DEV   1 // マウントを行うのにブロックデバイスが必要なファイルシステム
                        #define FS_BINARY_MOUNTDATA     2   //マウントオプションをバイナリーで解釈する. セキュリティ関連で使用
                        #define FS_HAS_SUBTYPE          4   //fuseとfuseblkで作成したファイルシステムタイプを区別するのに使用
                        #define FS_USERNS_MOUNT         8   //ユーザー名前空間のルートとしてマウントする
                        #define FS_USERNS_DEV_MOUNT     16  //ユーザー名前空間のマウントでデバイスファイルにアクセスする
                        #define FS_RENAME_DOES_D_MOVE   32768   //FSはリネームを行っているときにその内部でd_moveを行う
                     */
    FileSystemTypeObject next;
    List<SuperBlock> fsSupers; //このファイルシステムタイプでマウントされているスーパーブロックオブジェクトのハッシュリスト

    Ext2fsSuperBlock sb;

    public FileSystemTypeObjectImpl(String name, int fsFlags) {
        this.name = name;
        this.fsFlags = fsFlags;
    }

    /**
     * @param fsType mountシステムコールで指定されたファイルシステムタイプのオブジェクト
     * @param flags mountシステムコールで指定されたマウントフラグ
     * @param devName mountシステムコールで指定されたデバイス名
     * @return ファイルシステムのルートのdエントリー (Dentry型)
     */
    public Dentry mount(String fsType, int flags, String devName) throws Exception {
        // -> ここでExt2のmount処理へ飛ぶ
        FileSystem ext2fs = new Ext2fs();
        return ext2fs.mount(fsType, flags, devName);
    }

    /**
     * ファイルシステム固有のマウント処理を実行する.
     * 主にスーパーブロックの読み込みやルートディレクトリの読み込みを行う.
     * @param type
     * @param flags
     * @param source マウント前のデバイスファイルのパス
     * @return 読み込んだFS固有のスーパーブロック
     */
    public SuperBlock getSb(FileSystemTypeObject type, int flags, String source) throws Exception {
        sb = new Ext2fsSuperBlock(source); //本当はここにext2があるべきでは無い
        sb.init();
        return sb;
    }

    public int killSb() {
        return 0;
    }
}
