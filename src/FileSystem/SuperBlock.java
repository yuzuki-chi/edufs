package FileSystem;

import FileObject.VfsMount;

import java.nio.Buffer;

public class SuperBlock {

    /**
     * スーパーブロックオブジェクト (Super Block Object)
     *  マウントされたファイルシステムに関する情報を保存するオブジェクト.
     *  ディスクに保存されているファイルシステム制御ブロック(filesystem control block)に該当する.
     */

    ListHead sList;                 //スーパーブロックのポインタ
    kdevT sDev;                     //デバイス番号
    long sBlocksize;                //ブロックサイズ (byte)
    char sBlocksizeBits;            //ブロックサイズ (bitの桁数)
    char sDirt;                     //変更された(汚れた)かどうかのフラグ
    long sMaxbytes;                 //ファイルサイズの最大値
    FileSystemType sType;           //ファイルシステム種別へのポインタ
    //SuperOperations sOp;            //スーパーブロックのメソッド //TODO
    DquotOperations dqOp;           //ディスククォータメソッド  //TODO
    long sFlags;                    //マウントフラグ
    long sMagic;                    //ファイルシステムマジック番号
    Dentry sRoot;                   //マウントディレクトリのdエントリオブジェクト
    RwSemaphore sUmount;            //アンマウント用のセマフォ //todo
    Semaphore sLock;                //スーパーブロックのセマフォ   //todo
    int sCount;                     //参照カウンタ
    AtomicT sActive;                //補助参照カウンタ    //todo
    ListHead sDirty;                //変更されたinodeのリスト
    ListHead sLockedInodes;         //I/O処理中のinodeのリスト
    ListHead sFiles;                //スーパーブロックに割り当てられたファイルオブジェクトのリスト
    BlockDevice sBdev;              //ブロック型デバイスドライバディスクリプタへのポインタ //TODO
    ListHead sInstances;            //同じFS種別用のスーパーブロックオブジェクトのリスト
    QuotaMountOptions sDquot;       //ディスククォータ用のオプション //TODO
    Union u;                        //ファイルシステム固有の情報. ext2fsの場合 ext2_sb_infoクラスが保存される


    void readInode(Inode inode) {
        /* 引数としてinodeオブジェクトのアドレスを受け取って, ディスク上のデータを元に, そのinodeオブジェクトのメンバを適切に初期化する.
           ディスクから読み取るべきファイルシステム上の特定のinodeは, inodeオブジェクトの"iIno"によって指定する
         */
    }

    void readInode2(Inode inode, long p) {
        /* readInodeから, pが指す64bitの数値でinodeを識別する.
           アーキテクチャが64bitに完全対応したら不要なメソッド
         */
    }

    void dirtyInode(Inode inode) {
        /* inodeが汚れたと印をつける時に呼び出す.
           ジャーナルを更新する機能のため, Ext2は使わない気がする
        */
    }

    void writeInode(Inode inode, int flag) {
        /* ファイルシステム上のinodeを, 引数として渡られたinodeオブジェクトの内容で更新する.
           更新すべきディスク上のファイルシステム上のinodeは, inodeオブジェクトの"iIno"によって指定する
         */
    }

    void putInode(Inode inode) {
        /* 引数にinodeオブジェクトを受け取って, そのinodeオブジェクトを解放する.
           オブジェクトが他のプロセスによって使用されている場合は, 必ずしもメモリ解放とはならない
         */
    }

    void deleteInode(Inode inode) {
        /* ファイルのデータブロック, ディスクinode, VFSのinodeを削除する */
    }

    void putSuper(SuperBlock sb) {
        /* 引数にスーパーブロックオブジェクトのアドレスを受け取り, そのスーパーブロックを解放する.
           対応するファイルシステムがアンマウントされたときなど (Javaいらない気がする) */
    }

    void writeSuper(SuperBlock sb) {
        /* 指定されたオブジェクトの内容でファイルシステムスーパーブロックを更新する */
    }

    void writeSuperLockfs(SuperBlock sb) {
        /* ファイルシステムの更新をブロックし, 指定したオブジェクトの内容でスーパーブロックを更新する.
           ジャーナリングでは必要, でも今のところ使われてない
         */
    }

    void unlockfs(SuperBlock sb) {
        /* ファイルシステムの更新に対するブロックを解除する.
           writeSuperLockfsが使われてないからこれも使わないかも
         */
    }

    void statfs(SuperBlock sb, Buffer buf) {
        /* ファイルシステムについての統計情報をbugバッファに入れて返す */
    }

    void remountFs(SuperBlock sb, int flags, int data) {
        /* 新しいオプションでファイルシステムを再マウントする. */
    }

    void clearInode(Inode inode) {
        /* putInodeと同様. ただし, 指定されたinodeに対応するファイルに関するデータを含む全てのページも解放する. */
    }

    void umoutBegin(SuperBlock sb) {
        /* アンマウント操作が開始された. 対応するマウント操作を中断する (ネットワークファイルシステムのみ使用) */
    }

    void fhToDentry(SuperBlock sb, int fid, int fhLen, int fhType) { //本当はfidはFid型
        /* 引数として指定されたファイルハンドルに対応するdエントリオブジェクトを返す.
           このメソッドはネットワークファイルシステム用のカーネルスレッド(knfsd)が使用する.
         */
    }

    void dentryToFh(Dentry dentry, int fid, int lenp, int needParent) {
        /* dエントリオブジェクトに対応するファイルハンドルを取得する.
           このメソッドはNFSカーネルスレッド(knfsd)が使用する.
         */
    }

    void showOptions(/*SeqFile sf,*/ VfsMount vfsMount) {
        /* ファイルシステム固有のオプションを表示する */
    }
}
