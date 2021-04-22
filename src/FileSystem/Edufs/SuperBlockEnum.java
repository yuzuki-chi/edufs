package FileSystem.Edufs;

import FileSystem.Value.ValueType;

enum SuperBlockEnum implements BlockEnum {
    s_inodes_count(0, ValueType.INTEGER),
    s_blocks_count(4, ValueType.INTEGER),
    s_r_blocks_count(8, ValueType.INTEGER),
    s_free_blocks_count(12, ValueType.INTEGER),
    s_free_inodes_count(16, ValueType.INTEGER),
    s_first_data_block(20, ValueType.INTEGER),
    s_log_block_size(24, ValueType.INTEGER),
    s_log_frag_size(28, ValueType.INTEGER),
    s_blocks_per_group(32, ValueType.INTEGER),
    s_frags_per_group(36, ValueType.INTEGER),
    s_inodes_per_group(40, ValueType.INTEGER),
    s_mtime(44, ValueType.INTEGER),
    s_wtime(48, ValueType.INTEGER),
    s_mnt_count(52, ValueType.SHORT),
    s_max_mnt_count(54, ValueType.SHORT),
    s_magic(56, ValueType.SHORT),
    s_state(58, ValueType.SHORT),
    s_errors(60, ValueType.SHORT),
    s_minor_rev_level(62, ValueType.SHORT),
    s_lastcheck(64, ValueType.INTEGER),
    s_checkinterval(68, ValueType.INTEGER),
    s_creator_os(72, ValueType.INTEGER),
    s_rev_level(76, ValueType.INTEGER),
    s_def_resuid(80, ValueType.SHORT),
    s_def_resgid(82, ValueType.SHORT),

    s_first_ino(84, ValueType.INTEGER),
    s_inode_size(88, ValueType.SHORT),
    s_block_group_nr(90, ValueType.SHORT),
    s_feature_compat(92, ValueType.INTEGER),
    s_feature_incompat(96, ValueType.INTEGER),
    s_feature_ro_compat(100, ValueType.INTEGER),
    s_uuid(104, ValueType.UUID),
    s_volume_name(120, ValueType.STRING, 16),
    s_last_mounted(136, ValueType.STRING, 64),
    s_algo_bitmap(200, ValueType.INTEGER),

    s_prealloc_blocks(204, ValueType.BYTE),
    s_prealloc_dir_blocks(205, ValueType.BYTE),
    s_prealloc_alignment(206, ValueType.SHORT),

    s_journal_uuid(208, ValueType.UUID),
    s_journal_inum(224, ValueType.INTEGER),
    s_journal_dev(228, ValueType.INTEGER),
    s_last_orphan(232, ValueType.INTEGER),

    s_hash_seed(236,ValueType.INTARRAY,  4 * 4),
    s_def_hash_version(252, ValueType.BYTE),
    //s_padding(253, 3, DeviceIO.DeviceIO.Values.ValueType.INTEGER),

    s_default_mount_options(256, ValueType.INTEGER),
    s_first_meta_bg(260, ValueType.INTEGER),
    s_reserved(264, ValueType.INTARRAY, 190 * 4),
    ;

    private long offset; //offset from 1024byte.
    private int byteSize; //byte size of Element.
    private ValueType valueType; //Type of value.

    /**
     * @param offset スーパーブロック開始から見た相対オフセット
     * @param valueType ValueType
     * @param byteSize デフォルトはValueTypeに決められたバイトサイズ
     */
    SuperBlockEnum(long offset, ValueType valueType, int byteSize) {
        this.offset = offset;
        this.byteSize = byteSize;
        this.valueType = valueType;
    }

    SuperBlockEnum(long offset, ValueType valueType) {
        this(offset, valueType, valueType.getByteSize());
    }

    public long getOffset() { return offset; }

    public int getByteSize() { return byteSize; }

    public ValueType getValueType() { return valueType; }
}