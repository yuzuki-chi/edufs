package FileSystem.Edufs;

import FileSystem.Value.ValueType;

enum GroupDescEnum implements BlockEnum {
    bg_block_bitmap(0, ValueType.INTEGER),
    bg_inode_bitmap(4, ValueType.INTEGER),
    bg_inode_table(8, ValueType.INTEGER),
    bg_free_blocks_count(12, ValueType.SHORT),
    bg_free_inodes_count(14, ValueType.SHORT),
    bg_used_dirs_count(16, ValueType.SHORT),
    bg_pad(18, ValueType.SHORT),
    // bg_reserved(20, DeviceIO.DeviceIO.Values.ValueType.STRING, 12),
    ;

    private long offset; //offset from 1024byte.
    private int byteSize; //byte size of Element.
    private byte[] byteValue; //FileSystem.Value of byte.

    private ValueType valueType; //Type of value.

    GroupDescEnum(long offset, ValueType valueType, int byteSize) {
        this.offset = offset;
        this.byteSize = byteSize;
        this.valueType = valueType;
    }

    GroupDescEnum(long offset, ValueType valueType) {
        this(offset, valueType, valueType.getByteSize());
    }

    public long getOffset() { return offset; }

    public int getByteSize() { return byteSize; }

    public ValueType getValueType() { return valueType; }
}
