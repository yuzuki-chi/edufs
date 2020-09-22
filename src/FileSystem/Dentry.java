package FileSystem;

import FileObject.Directory;

public class Dentry {
    /**
     * dエントリオブジェクト (dentry object)
     *  ディレクトリエントリと, 対応するファイルとのリンクについて情報を保存するオブジェクト.
     */

    AtomicT dCount;     //dエントリオブジェクト利用カウンタ
    int dFlags;         //dエントリのフラグ
    Inode dInode;       //ファイル名に対応するinode
    Dentry dParent;     //親ディレクトリのdエントリオブジェクト
    ListHead dHash;     //ハッシュテーブルエントリ中のリストへのポインタ
    ListHead dLru;      //未使用dエントリリストへのポインタ
    ListHead dChild;    //同じ親ディレクトリを持つdエントリオブジェクトをリストにするためのポインタ
    ListHead dSubdirs;  //親ディレクトリのdエントリオブジェクトが, その子のdエントリオブジェクトリストを指すためのポインタ
    ListHead dAlias;    //同じinodeを共有するdエントリオブジェクトのリスト (エイリアス, 別名)
    int dMounted;       //dエントリがファイルシステムのマウントポイントの場合, このフラグを1にする
    Qstr dName;         //ファイル名
    long dTime;         //dRevalidateメソッドによって使用される
    DentryOperations dOp;   //dエントリのメソッド群
    SuperBlock dSb;     //ファイルのスーパーブロックオブジェクト
    long dVfsFlags;     //dエントリキャッシュのフラグ群
    //void dFsdata;       //ファイルシステム依存のデータ
    String dIname;      //短いファイル名を記録する領域

    int dRevalidate(Dentry dentry, int flag) {
        /* ファイルのパス名の変換に使用する前に, dエントリオブジェクトが, まだ有効であるかどうかを調べる.
           デフォルトのVFS関数では何も行わない. (ネットワークファイルシステムが固有の関数を持つ)
         */
        return 0;
    }

    int dHash(Dentry dentry, String name) {
        /* ハッシュ値を作成する. この関数はdエントリハッシュテーブルで利用するファイルシステム独自のハッシュ関数.
           引数dentryは, パス名要素が入っているディレクトリを指定する.
           引数nameは, 検索するべきパス名の要素と, ハッシュ関数によって生成された値との両方を含む構造体を指す.
         */
        return 0;
    }

    int dCompare(Directory dir, String name1, String name2) {
        /* 2つのファイル名を比較する.
           name1は, dirによって参照されるディレクトリに属していなければならない.
           デフォルトのVFS関数では, 普通の文字列比較を行う.
           (各ファイルシステムでは独自でこの方法を実装していて, 例えばMS-DOSでは大文字と小文字を区別しない)
         */
        return 0;
    }

    int dDelete(Dentry dentry) {
        /* dエントリオブジェクトへの最後の参照が削除された時に呼び出される (dCountが0になったとき)
           デフォルトのVFS関数では何も行わない.
         */
        return 0;
    }

    int dRelease(Dentry dentry) {
        /* dエントリオブジェクトが解放される時に呼び出される (スラブアロケータへの解放)
           デフォルトのVFS関数では何も行わない.
         */
        return 0;
    }

    int dIput(Dentry dentry, Inode ino) {
        /* dエントリオブジェクトが「負」になると呼び出される. (対応するinodeが無くなった時)
           デフォルトのVFS関数では, inodeオブジェクトを解放するためにiput()を呼び出す.
         */
        return 0;
    }
}
