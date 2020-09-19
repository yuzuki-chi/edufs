package FileSystem;

import ext2.Ext2fsSuperBlock;

public class FileSystemTypeObjectImpl implements FileSystemTypeObject {
    Ext2fsSuperBlock sb;

    public FileSystemTypeObjectImpl(String fsType) {
    }

    /**
     * ファイルシステム名から対応するFileSystemTypeディスクリプタを返す
     * @param fstype ファイルシステム名
     * @return ファイルシステムタイプディスクリプタ
     */
    public int getFsType(String fstype) {
        return 0;
    }

    /**
     * ファイルシステム固有のマウント処理を実行する.
     * 主にスーパーブロックの読み込みやルートディレクトリの読み込みを行う.
     * @param type
     * @param flags
     * @param source マウント前のデバイスファイルのパス
     * @return 読み込んだFS固有のスーパーブロック
     */
    public SuperBlock getSb(FileSystemTypeObject type, int flags, String source) {
        sb = new Ext2fsSuperBlock(); //本当はここにext2があるべきでは無い
        sb.init();
        return sb;
    }
}
