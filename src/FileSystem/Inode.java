package FileSystem;

import java.nio.Buffer;

public class Inode {

    /**
     * inodeオブジェクト (inode object)
     *  個々のファイルについての一般的な情報を保存するオブジェクト.
     *  ディスクに保存されているファイル制御ブロック(file control block)に該当する.
     *  inode番号によってファイルシステム上のファイルが一意に識別できる.
     */

    ListHead iHash; //ハッシュリスト用のポインタ
    ListHead iList; //inodeリスト用のポインタ
    ListHead iDentry; //dエントリリスト用のポインタ
    ListHead iDirtyBuffers; //更新済みバッファリスト用のポインタ
    ListHead iDirtyDataBuffers; //更新済みデータバッファリスト用のポインタ
    long iIno;   //inode番号
    int iCount; //利用カウンタ
    kdevT iDev; //デバイス番号
    umodeT iMode;   //ファイルの種類とアクセス権
    nlinkT iNlink;  //ハードリンクの数
    uidT iUid;      //所有者のユーザID
    gidT iGid;      //グループID
    kdevT iRdev;    //実デバイス番号
    offT iSize;     //ファイルの長さ (byte)
    timeT iAtime;   //最終ファイルアクセス時刻
    timeT iMtime;   //最終ファイル書き込み時刻
    timeT iCtime;   //最終inode更新時刻
    int iBlkbits;   //ブロックの大きさ (bit数)
    long iBlksize;  //ブロックの大きさ (byte)
    long iBlocks;   //ファイルのブロック数
    long  iVersion; //バージョン番号. 毎回使用後に自動的に増加
    Semaphore iSem;      //inodeアクセス用のセマフォ
    Semaphore iZombie;   //inodeアクセス用の補助的なセマフォ. inodeの削除やinodeの名称変更時に使用
    InodeOperations iOp;    //inode操作
    FileOperations iFop;    //デフォルトのファイル操作
    SuperBlock iSb;         //スーパブロックオブジェクトへのポインタ
    WaitQueueHeadT iWait;   //inodeウェイトキュー
    FileLock iFlock;        //ファイルロックリストへのポインタ
    AddressSpace iMapping;  //アドレス空間オブジェクトへのポインタ
    AddressSpace iData;     //アドレス空間オブジェクト (ブロック型デバイスファイル用)
    Dquot iDquit;           //inodeディスククォータ
    ListHead iDevices;      //ブロック型デバイスファイルのinodeのリストのポインタ
    PipeInodeInfo iPipe;    //ファイルがパイプである場合に使用
    BlockDevice iBdev;      //ブロック型デバイスドライバへのポインタ
    CharDevice iCdev;       //キャラクタ型のデバイスドライバへのポインタ
    long iDnotifyMask;      //ディレクトリ通知イベント用のビットマスク
    DnotifyStruct iDnotify; //ディレクトリ通知に使用
    long iState;            //inode状態フラグ
    int iFlags;             //ファイルシステムのマウントフラグ
    char iSock;             //そのファイルがソケットであれば0以外の値を持つ
    AtomicT iWriteCount;    //書き込みプロセス用の利用カウンタ
    int iAttrFlags;         //ファイル作成フラグ
    long iGeneration;       //inodeのバージョン番号
    union u;                //ファイルシステム固有の情報

    int create(Inode dir, Dentry dentry, umodeT um) {
        /* ディレクトリdir内のdエントリオブジェクトdentryに対応する, 通常ファイル用に新しいディスクinodeを作成する */
        return 0;
    }

    Dentry lookup(Inode dir, Dentry dentry) {
        /* ディレクトリdirを検索する.
           dエントリオブジェクトdentryに含まれるファイル名に対応するinodeを見つけるためである.
         */
        return null;
    }

    int link(Dentry oldDentry, Inode dir, Dentry newDentry) {
        /* ファイルoldDenrryを参照する新しいハードリンクをディレクトリdirの中に作成する.
           新しいハードリンクの名前はnewDentryで指定する.
         */
        return 0;
    }

    int symlink(Inode dir, Dentry dentry, String symname) {
        /* ディレクトリdirの中にdエントリオブジェクトdentryに対応するシンボリックリンク用に新しいinodeを作成する. */
        return 0;
    }

    int mkdir(Inode dir, Dentry dentry, umodeT um) {
        /* ディレクトリdirの中のdエントリオブジェクトdentryで指定されたディレクトリを作成する. */
        return 0;
    }

    int rmdir(Inode dir, Dentry dentry) {
        /* dエントリオブジェクトdentryで指定するサブディレクトリをディレクトリdirから削除する */
        return 0;
    }

    int mknod(Inode dir, Dentry dentry, umodeT um, DevT devt) {
        /* ディレクトリdir内にdエントリオブジェクトdentryに対応する, 特殊ファイル用の新しいディスクinodeを作成する.
           引数modeとrdevはそれぞれ, ファイルの種類とデバイスのメジャー番号を指定する.
         */
        return 0;
    }

    int rename(Inode oldDir, Dentry oldDentry, Inode newDir, Dentry newDentry) {
        /* oldEntryによって特定されるファイルをoldDirディレクトリからnewDirディレクトリへ移動する.
           新しいファイル名は, newDentryが指し示すdエントリオブジェクトに入っている.
         */
        return 0;
    }

    int readlink(Dentry dentry, Buffer buff, int buflen) {
        /* bufferによて指定されるメモリ領域内に,
           dentryによって指定されるシンボリックリンクに対応するファイルのパス名を書き込む.
         */
        return 0;
    }

    int followLink(Inode inode, Inode dir) {
        /* inodeオブジェクトinodeで指定するシンボリックリンクを解決する.
           シンボリックリンクが相対パス名の場合, 指定されたディレクトリdirから検索処理を開始する.
         */
        return 0;
    }

    int truncate(Inode inode) {
        /* inodeに対応するファイルのサイズを変更する.
           メソッドを呼び出す前に, inodeオブジェクトのiSizeメンバを, 必要とされる新しいサイズに設定しておく必要がある.
         */
        return 0;
    }

    int permission(Inode inode, int mask) {
        /* 指定されたアクセスモードmaskがinodeが管理しているファイルに許可されているかどうかを確認する. */
        return 0;
    }

    int revalidate(Dentry dentry) {
        /* キャッシュされているinodeの情報を使って良いかを確認し, その後キャッシュされたinodeの属性を更新する.
           通常ネットワークファイルシステムが使うため, 今回はいらないかも
         */
        return 0;
    }

    int setattr(Dentry dentry, Iattr iattr) {
        /* dエントリオブジェクトdentryに対応するinodeのinode属性を更新する. */
        return 0;
    }

    int getattr(Dentry dentry, Iattr iattr) {
        /* dエントリオブジェクトdentryに対応するinodeからinode属性を取得する.
           このメソッドは, ネットワークファイルシステムで使用する. 必要があればキャッシュしているinode属性をリフレッシュする.
         */
        return 0;
    }
}
