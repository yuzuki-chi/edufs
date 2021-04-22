package FileSystem.Edufs;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

class InodeTable {
    private final long offset;  //inode table の開始ブロック
    private final RandomAccessFile raf;
    private long inodeOffset;

    /* super block's info */
    private final SuperBlock sb;
    private final long groupSize, deviceSize;
    private final int blockSize;

    private List<InodeImpl> inodeList = new ArrayList();    //全てのinodeを保持するリスト

    /**
     * inodeテーブルを保持するクラス. 全ブロックグループのinodeをもつ
     * @param raf 対象となるRandomAccessFile
     * @param offset inodeテーブルが開始する相対ブロック
     */
    public InodeTable(RandomAccessFile raf, SuperBlock sb, long offset) {
        this.raf = raf;
        this.offset = offset;
        this.sb = sb;
        this.blockSize = 1024 << sb.getLogBlockSize();
        this.groupSize = sb.getBlocksPerGroup() * blockSize;
        this.deviceSize = sb.getBlocksCount() * blockSize;

        this.init();
    }

    private void addList(InodeImpl inode) { inodeList.add(inode); }

    public List<InodeImpl> get() { return inodeList; }

    public InodeImpl get(int index) {
        try {
            return inodeList.get(index - 1);
        } catch ( Exception e ) {
            return null;
        }
    }

    private void init() {
        //全ブロックグループのinodeをつなげる
        long read = 0;
        int group = 0;
        while(true) {
            System.out.println("Checking INODE TABLE from Group " + group + "/" + (int)(this.deviceSize/this.groupSize) + " ... ");
            this.init(group);
            read += this.groupSize;
            group ++;
            if(read > this.deviceSize) break;
        }
    }

    private void init(int group) {
        //group ... setするブロックグループ
        for(int i = 0; i < sb.getInodesPerGroup(); i++) {
            //inodeOffset ... inodeのある絶対アドレス (byte)
            inodeOffset = (group * this.groupSize) + (offset * this.blockSize + (sb.getInodeSize() * i));
            try {
                this.addList(new InodeImpl(raf, inodeOffset));
            } catch (Exception e) {
                System.out.println("error: DeviceIO.InodeTable「inodeを追加できませんでした」");
            }
        }
    }
}