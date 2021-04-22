package FileSystem.Edufs;

import FileSystem.Value.ValueType;

enum InodeEnum implements BlockEnum {
    i_mode(0, ValueType.SHORT),
    i_uid(2, ValueType.SHORT),
    i_size(4, ValueType.INTEGER),
    i_atime(8, ValueType.INTEGER),
    i_ctime(12, ValueType.INTEGER),
    i_mtime(16, ValueType.INTEGER),
    i_dtime(20, ValueType.INTEGER),
    i_gid(24, ValueType.SHORT),
    i_links_count(26, ValueType.SHORT),
    i_blocks(28, ValueType.INTEGER),
    i_flags(32, ValueType.INTEGER),
    i_osd1(36, ValueType.INTEGER),
    i_block(40, ValueType.INTARRAY, 15 * 4),
    i_generation(100, ValueType.INTEGER),
    i_file_acl(104, ValueType.INTEGER),
    i_dir_acl(108, ValueType.INTEGER),
    i_faddr(112, ValueType.INTEGER),
    /**
     * i_osd2
     */
    //Hurd
    bg_inode_table(116, ValueType.BYTE),
    h_i_fsize(117, ValueType.BYTE),
    h_i_mode_high(118, ValueType.SHORT),
    h_i_uid_high(120, ValueType.SHORT),
    h_i_gid_high(122, ValueType.SHORT),
    h_i_author(124, ValueType.INTEGER),
    //Linux
    l_i_frag(116, ValueType.BYTE),
    l_i_fsize(117, ValueType.BYTE),
    i_pad1(118, ValueType.SHORT),
    l_i_uid_high(120, ValueType.SHORT),
    l_i_gid_high(122, ValueType.SHORT),
    l_i_reserved(124, ValueType.INTEGER),
    //Masix
    m_i_frag(116, ValueType.BYTE),
    m_i_fsize(117, ValueType.BYTE),
    //m_pad1(118, DeviceIO.DeviceIO.Values.ValueType.STRING, 10), //予約
    ;

    private long offset; //offset from 1024byte.
    private int byteSize; //byte size of Element.
    private byte[] byteValue; //FileSystem.Value of byte.
    private ValueType valueType; //Type of value.

    InodeEnum(long offset, ValueType valueType, int byteSize) {
        this.offset =  offset;
        this.byteSize = byteSize;
        this.valueType = valueType;
    }

    InodeEnum(long offset, ValueType valueType) {
        this(offset, valueType, valueType.getByteSize());
    }

    public long getOffset() { return offset; }

    public int getByteSize() { return byteSize; }

    public ValueType getValueType() { return valueType; }
}
