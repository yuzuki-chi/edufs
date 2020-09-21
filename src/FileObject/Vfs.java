package FileObject;

public interface Vfs {
    /**
     * VFS
     *  アプリケーションプログラムとそれぞれのファイルシステムとの間にあるレイヤ
     */

    /** VFSが取り扱うシステムコールに基づく **/
    //ファイルシステムのマウント/アンマウント
    int mount(String source, String target, String fstype, int flags) throws Exception;
    int unmount();

    //ファイルシステム情報の取得
    int sysfs();

    //ファイルシステムの統計情報を取得
    int statfs();
    int fstatfs();
    int ustat();

    //ルートディレクトリの変更
    int chroot();
    int pivot_root();

    //カレントディレクトリの操作
    int chdir();
    int fchdir();
    int getcwd();

    //ディレクトリの作成/削除
    int mkdir();
    int rmdir();

    //ディレクトリエントリの操作
    int getdents();
    int readdir();
    int link();
    int unlink();
    int rename();

    //シンボリックリンクの操作
    int readlink();
    int symlink();

    //ファイル所有者の変更
    int chown();
    int fchown();
    int lchown();

    //ファイル属性の変更
    int chmod();
    int fchmod();
    int utime();

    //ファイル状態の読み取り
    int stat();
    int fstat();
    int lstat();
    int access();

    //ファイルのオープン/クローズ
    int open(String filename, int mode);
    int close();
    int create();
    int umask();

    //ファイルディスクリプタの操作
    int dup();
    int dup2();
    int fcntl();

    //非同期I/O通知
    int select();
    int poll();

    //ファイルサイズの変更
    int truncate();
    int ftruncate();

    //ファイルポインタの変更
    int lseek();
    int _llseek();

    //ファイルのI/O操作
    int read(int fd, byte[] b, int size);
    int write();
    int readv();
    int writev();
    int sendfile();
    int readhead();

    //ファイルポインタの変更とファイルアクセス
    int pread();
    int pwrite();

    //ファイルのメモリマッピング操作
    int mmap();
    int munmap();
    int madvise();
    int mincore();

    //ファイルデータの同期
    int fdatasync();
    int fsync();
    int sync();
    int msync();

    //ファイルロックの操作
    int flock();
}
