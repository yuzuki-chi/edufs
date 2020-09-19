package ext2;

import FileSystem.SuperBlock;

public class Ext2fsSuperBlock implements SuperBlock {

    public Ext2fsSuperBlock() {
    }

    /**
     * 実際にExt2のスーパーブロックの読み出しを行っていく.
     */
    public void init() {
    }

    public String getSroot() {
        String sroot = "";
        //GroupDescにあるinodeテーブルからルートディレクトリを探索してそのパスを返す
        return sroot;
    }
}
