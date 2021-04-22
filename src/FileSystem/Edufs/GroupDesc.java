package FileSystem.Edufs;

import java.io.RandomAccessFile;

class GroupDesc extends ControlBlock<GroupDescEnum> {
    private long bg_block_bitmap,       //ブロックビットマップのブロック番号
                 bg_inode_bitmap,       //inodeビットマップのブロック番号
                 bg_inode_table;        //最初のinodeテーブルがあるブロックのブロック番号
    private long bg_free_blocks_count,  //グループ内の空きブロック数
                 bg_free_inodes_count,  //グループ内の空きinode数
                 bg_used_dirs_count,    //グループ内のディレクトリ数
                 bg_pad; //short        //ワード境界のアライン用

    private int gdOffset;

    public GroupDesc(RandomAccessFile raf, int blockSize) throws Exception {
        super(raf);
//        this.gdOffset = dio.BLOCK_SIZE <=1 ? dio.BLOCK_SIZE * 2 : dio.BLOCK_SIZE * 1;
        this.gdOffset = blockSize <= 1 ? blockSize * 2 : blockSize * 1;
        this.setElements();
    }

    private void setElements() {
        //TODO: いずれEnumを廃止する予定
        this.bg_block_bitmap = (long) super.read(gdOffset + GroupDescEnum.bg_block_bitmap.getOffset(), GroupDescEnum.bg_block_bitmap.getValueType()).getValue();
        this.bg_inode_bitmap = (long) super.read(gdOffset + GroupDescEnum.bg_inode_bitmap.getOffset(), GroupDescEnum.bg_inode_bitmap.getValueType()).getValue();
        this.bg_inode_table = (long) super.read(gdOffset + GroupDescEnum.bg_inode_table.getOffset(), GroupDescEnum.bg_inode_table.getValueType()).getValue();
        this.bg_free_blocks_count = (long) super.read(gdOffset + GroupDescEnum.bg_free_blocks_count.getOffset(), GroupDescEnum.bg_free_blocks_count.getValueType()).getValue();
        this.bg_free_inodes_count = (long) super.read(gdOffset + GroupDescEnum.bg_free_inodes_count.getOffset(), GroupDescEnum.bg_free_blocks_count.getValueType()).getValue();
        this.bg_used_dirs_count = (long) super.read(gdOffset + GroupDescEnum.bg_used_dirs_count.getOffset(), GroupDescEnum.bg_used_dirs_count.getValueType()).getValue();
        this.bg_pad = (long) super.read(gdOffset + GroupDescEnum.bg_pad.getOffset(), GroupDescEnum.bg_pad.getValueType()).getValue();
    }

    public long getBlockBitmap() { return bg_block_bitmap; }

    public long getInodeBitmap() { return bg_inode_bitmap; }

    public long getInodeTable() { return bg_inode_table; }

    public long getFreeBlocksCount() { return bg_free_blocks_count; }

    public long getFreeInodesCount() { return bg_free_inodes_count; }

    public long getUsedDirsCount() { return bg_used_dirs_count; }

    public long getBg_pad() { return bg_pad; }

    @Override
    public String toString() {
        return "DeviceIO.GroupDesc{" +
                "bg_block_bitmap=" + bg_block_bitmap +
                ", bg_inode_bitmap=" + bg_inode_bitmap +
                ", bg_inode_table=" + bg_inode_table +
                ", bg_free_blocks_count=" + bg_free_blocks_count +
                ", bg_free_inodes_count=" + bg_free_inodes_count +
                ", bg_used_dirs_count=" + bg_used_dirs_count +
                ", bg_pad=" + bg_pad +
                '}';
    }
}
