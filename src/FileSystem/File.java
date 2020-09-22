package FileSystem;

import FileObject.VfsMount;

public class File {

    /**
     * ファイルオブジェクト (file object)
     *  オープンされているファイルとプロセスとの間のやりとりに関する情報を保存するオブジェクト.
     *  この情報は, 各プロセスがファイルにアクセスしてる間にカーネルメモリ内に存在しているだけ.
     *  ファイルがオープンされた時に生成される.
     */

    ListHead fList;     //ファイルオブジェクトの汎用的なリスト用のポインタ
    Dentry fDenrry;     //オープンする時に利用したファイル名を表すdエントリオブジェクトへのポインタ
    VfsMount fVfsmnt;   //そのファイルを含むファイルシステムへのポインタ
    FileOperations fOp; //ファイル操作テーブルへのポインタ
    AtomicT fCount;     //ファイルオブジェクトの利用カウンタ
    int fFlags;         //ファイルのオープン時に指定されたフラグ
    modeT fMode;        //プロセスのアクセスモード
    LoffT fPos;         //現在使用しているファイルのオフセット (ファイルポインタ)
    long fReada;        //先読みフラグ
    long fRamax;        //先読みする最大ページ数
    long fRaend;        //最後の先読みのあとのファイルポインタ
    long fRalen;        //先読みするバイト数
    long fRawin;        //先読みするページ数
    FownStruct fOwner;  //シグナル経由での非同期I/O用のデータ
    int fUid;           //ユーザのUID
    int fGid;           //ユーザのGID
    int fError;         //ネットワーク経由の書き込み操作時のエラー番号
    long fVersion;      //バージョン番号. ファイルアクセス時, 毎回自動的に増加
    //void privateData;   //端末ドライバ用
    Kiobuf fIobuf;      //ダイレクトアクセスバッファ用のディスクリプタ
    long fIobufLock;    //ダイレクトI/O用のロック

    int llseek(File file, LoffT offset, int origin) {
        /* ファイルポインタを更新する */
        return 0;
    }

    int read(File file, Buffer buf, SizeT count, LoffT offset) {
        /* offsetで指定される一から開始し, countで指定されるバイト数をファイルから読み取る.
           その後, (通常ファイルポインタである)*offsetの値を増やす
         */
        return 0;
    }

    int write(File file, Buffer buf, SizeT count, LoffT offset) {
        /* offsetで指定される一から開始し, countバイト分をファイルに書き込む.
           その後, (通常ファイルポインタである)*offsetの値を増やす
         */
        return 0;
    }

    int readdir(Inode dir, Dentry dirent, Inode filldir) { //filldirがよくわからない
        /* ディレクトリの次のディレクトリエントリをdirentに返す.
           filldir引数には, ディレクトリエントリ中のメンバを取り出す補助関数のアドレスが入る
         */
        return 0;
    }

    int poll(File file, PollTable pollTable) {
        /* ファイルに変更が行われているかどうかを確認し, 行わなければ行われるまで休止する */
        return 0;
    }

    int ioctl(Inode inode, File file, int cmd, String ... arg) {
        /* 主にデバイスファイルに適用され, 対応するハードウェアデバイスに命令を送る.
           通常ファイルやソケットファイルにも定義される.
         */
        return 0;
    }

    int mmap(File file, VmArea vma) {
        /* プロセスアドレス空間へ, ファイルのメモリマッピングを行う */
        return 0;
    }

    int open(Inode inode, File file) {
        /* 新しいファイルオブジェクトを生成し, 対応するinodeオブジェクトとリンクすることにより, ファイルをオープンする.*/
        return 0;
    }

    int flush(File file) {
        /* オープンファイルへの参照がクローズされた時 (ファイルオブジェクトのfCountが減った時) に呼び出される.
           実際の用途は, ファイルシステムによって異なる.
         */
        return 0;
    }

    int release(Inode inode, File file) {
        /* ファイルオブジェクトを解放する. オープンファイルへの最後の参照がクローズされた時
         (ファイルオブジェクトのfCountが0になった時)に呼び出される.
         */
        return 0;
    }

    int fsync(File file, Dentry dentry) {
        /* ファイルのキャッシュされたデータのすべてをディスクに書き込む */
        return 0;
    }

    int fasync(int fd, File file, int on) {
        /* シグナルによる非同期I/Oの通知を許可または禁止する */
        return 0;
    }

    int lock(File file, int cmd, FileLock fileLock) {
        /* ファイルをロックする */
        return 0;
    }

    int readv(File file, Buffer vector, int count , LoffT offset) {
        /* ファイルからバイト列を読み込み, vectorで示したバッファに結果を入れる.
           バッファの数はcountで指定する.
         */
        return 0;
    }

    int writev(File file, Buffer vector, int count, LoffT offset) {
        /* vectorで示すバッファ内のバイト列をファイルに書き込む.
           バッファの数はcountで指定する.
         */
        return 0;
    }

    int sendpage(File file, LoffT offset, int size, Pointer pointer, Fill fill) { //わからん
        /* あるファイルから別のファイルにデータを転送する. ソケットで使用する */
        return 0;
    }

    long getUnmappedArea(File file, long addr, long len, long offset, long flags) {
        /* ファイルをマップするために, 未使用アドレスの範囲を取得する.
           (フレームバッファのメモリマッピングで使用する)
         */
        return 0;
    }
}
