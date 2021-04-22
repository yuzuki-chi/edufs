package FileSystem.Edufs;

import java.io.RandomAccessFile;

class Bitmap {
    private byte[] byteMap;
    private final RandomAccessFile raf;
    private long offset;

    /* super block's info */
    private final int blockSize;
    private final long groupSize;

    /**
     * @param raf 対象となるRandomAccessFile
     * @param sb super block
     * @param offset 開始ブロック番号
     * @param group ブロックグループ番号
     */
    public Bitmap(RandomAccessFile raf, SuperBlock sb, long offset, int group) {
        this.raf = raf;
        this.blockSize = 1024 << sb.getLogBlockSize();
        this.groupSize = sb.getBlocksPerGroup() * blockSize;
        this.offset = (group * groupSize) + (offset * blockSize);
        this.init();
    }

    public void init() {
        this.readMap();
    }

    /**
     * TODO:
     * init() -> for(グループ数) readMap(group)
     * get(index) <- Bitmapから特定のビットを読み出す (isSetと同義)
     * set(index) <- Bitmapと実際のメモリに書き込む
     */

    public void readMap() {
        byteMap = new byte[blockSize];
        try {
            raf.seek(offset);
            for (int i=0; i<blockSize; i++) {
                byteMap[i] = this.rev(raf.readByte());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte rev(byte b) {
        int i = Byte.toUnsignedInt(b);
        i = (i & 0x55555555) << 1 | (i >>> 1) & 0x55555555;
        i = (i & 0x33333333) << 2 | (i >>> 2) & 0x33333333;
        i = (i & 0x0f0f0f0f) << 4 | (i >>> 4) & 0x0f0f0f0f;
        return (byte) i;
    }

    public boolean isSet(int index) {
        //指定されたinodeが使用中かどうかを返す
        int bytev = ((index-1) / 8);    //(0から数えて)bytev番目のbyteの
        int bitv = (index-1) % 8;       //(0から数えて)bitv番目に格納されてる
        byte targetb = this.byteMap[bytev];

        int target = Integer.parseInt(String.valueOf(String.format("%08d", Integer.valueOf(Integer.toBinaryString(Byte.toUnsignedInt(targetb)))).charAt(bitv))); //もがき苦しんでる跡（黒歴史）（足りない頭で考えた決死の策）
        if(target != 0) return false;
        else return true;
    }

    public void output() {
        int cnt = 1; //応急
        for(int i=0; i<blockSize; i++) {
            System.out.print(Byte.toUnsignedInt(byteMap[i]) + " ");
            if (cnt % 8 == 0) System.out.println("");
            cnt++;
        }
    }
}