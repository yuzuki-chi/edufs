import File.VfsMount;
import FileSystem.Dentry;
import FileSystem.FileSystemTypeObjectImpl;
import FileSystem.SuperBlock;

/**
 * System Call Service Routine
 */
public class SystemCall {

    /**
     * ファイルシステムをマウントする
     * @param source マウントするファイルパス
     * @param target マウント先のファイルパス
     * @param fstype ファイルシステムタイプ
     * @param flags マウントフラグ
     * @return 成功した場合0, 失敗した場合-1を返す
     */
    public static int sys_mount(String source, String target, String fstype, int flags) throws Exception {
        int retval = 0;
        long dataPage; //おそらくインスタンス化必須
        long typePage; //こちらもインスタンス化必須
        long devPage;

//        lock_kernel();      //カーネルロック

        FileSystemTypeObjectImpl type = new FileSystemTypeObjectImpl(fstype, 0); //ファイルシステム名から対応するfile_system_typeディスクリプタを取得
        Dentry dentry = type.mount(fstype, flags, source);
        if(dentry == null) return -1;

//        unlock_kernel();    //カーネルロック解除

        return retval;
    }

    public static int sys_open(String pathname, int mode) {
        return 0;
    }

    public static int sys_read(int fd, byte[] b, int size) {
        return 0;
    }
}
