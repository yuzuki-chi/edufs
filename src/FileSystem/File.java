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
    loffT fPos;         //現在使用しているファイルのオフセット (ファイルポインタ)
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
}
