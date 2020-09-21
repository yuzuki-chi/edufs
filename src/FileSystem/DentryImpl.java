package FileSystem;

import java.util.List;

public class DentryImpl implements Dentry {
    String Dname;   //ファイル名
    long Dinode;    //ファイル名に対応するinode
    List<String> Dalias;    //ファイルの名前リスト

    Dentry Dparent; //親ディレクトリ
    List<String> Dchild; //同一親ディレクトリの子ファイルリスト
    List<String> Dsubdirs; //親ディレクトリ配下の子ファイルリスト

    List<String> Dhash; //ハッシュリスト (いらない気がする)

}
