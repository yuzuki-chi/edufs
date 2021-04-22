package FileSystem.Edufs;

import FileSystem.Value.ValueType;

/**
 * Directory
 * リビジョン0
 */

enum DirEntryEnum implements BlockEnum{
    inode(0, ValueType.INTEGER),
    rec_len(4, ValueType.SHORT),
    name_len(6, ValueType.SHORT),
    name(7, ValueType.BYTEARRAY),
    ;

    private long offset; //offset from 1024byte.
    private int byteSize; //byte size of Element.
    private ValueType valueType; //Type of value.
    private final int dirOffset = 4096 * 502;

    DirEntryEnum(long offset, ValueType valueType, int byteSize) {
        this.offset = dirOffset + offset;
        this.byteSize = byteSize;
        this.valueType = valueType;
    }

    DirEntryEnum(long offset, ValueType valueType) {
        this(offset, valueType, valueType.getByteSize());
    }

    public long getOffset() { return offset; }

    public int getByteSize() { return byteSize; }

    public ValueType getValueType() { return valueType; }
}

/**
 * Directory
 * リビジョン1以降
 */
enum DirEntryEnum_2 implements BlockEnum{
    inode(0, ValueType.INTEGER),
    rec_len(4, ValueType.SHORT),
    name_len(6, ValueType.BYTE),
    file_type(7, ValueType.BYTE),
    name(8, ValueType.BYTEARRAY),
    ;

    private long offset; //offset from 1024byte.
    private int byteSize; //byte size of Element.
    private ValueType valueType; //Type of value.

    DirEntryEnum_2(long offset, ValueType valueType, int byteSize) {
        this.offset = offset;
        this.byteSize = byteSize;
        this.valueType = valueType;
    }

    DirEntryEnum_2(long offset, ValueType valueType) {
        this(offset, valueType, valueType.getByteSize());
    }

    public long getOffset() { return offset; }

    public int getByteSize() { return byteSize; }

    public ValueType getValueType() { return valueType; }
}