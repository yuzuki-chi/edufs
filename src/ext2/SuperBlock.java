package ext2;

public class SuperBlock {
    private long s_inodes_count, s_blocks_count, s_r_blocks_count, s_free_blocks_count, s_free_inodes_count, s_first_data_block, s_log_block_size, s_log_frag_size, s_blocks_per_group, s_frags_per_group, s_inodes_per_group, s_mtime, s_wtime, s_lastcheck, s_checkinterval, s_creator_os, s_rev_level; //int
    private long s_mnt_count, s_max_mnt_count, s_magic, s_state, s_errors, s_minor_rev_level, s_def_resuid, s_def_resgid; //short

    private long s_first_ino,  s_feature_compat, s_feature_incompat, s_feature_ro_compat, s_algo_bitmap; //int
    private long s_inode_size, s_block_group_nr; //short
    private String s_uuid, s_volume_name, s_last_mounted;

    private long s_prealloc_blocks, s_prealloc_dir_blocks; //byte
    private long s_prealloc_alignment; //short

    private long s_journal_inum, s_journal_dev, s_last_orphan; //int
    private String  s_journal_uuid;

    private long[] s_hash_seed; //int[]
    private long s_def_hash_version; //byte

    private long s_default_mount_options, s_first_meta_bg; //int
    private long[] s_reserved; //int[]
}
