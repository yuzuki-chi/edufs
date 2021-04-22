package ext2;

import FileSystem.Dentry;
import FileSystem.SuperBlock;
import FileSystem.SuperBlockEnum;

public class Ext2fsSuperBlock extends SuperBlock {
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

    private Dentry droot;

    public Ext2fsSuperBlock(String source) throws Exception {
        super(source);
        this.init();
    }

    /**
     * 実際にExt2のスーパーブロックの読み出しを行っていく.
     */
    public void init() {
        this.s_inodes_count =  super.readInt(SuperBlockEnum.s_inodes_count);
        this.s_blocks_count =  super.readInt(SuperBlockEnum.s_blocks_count);
        this.s_r_blocks_count =  super.readInt(SuperBlockEnum.s_r_blocks_count);
        this.s_free_blocks_count =  super.readInt(SuperBlockEnum.s_free_blocks_count);
        this.s_free_inodes_count =  super.readInt(SuperBlockEnum.s_free_inodes_count);
        this.s_first_data_block =  super.readInt(SuperBlockEnum.s_first_data_block);
        this.s_log_block_size =  super.readInt(SuperBlockEnum.s_log_block_size);
        this.s_log_frag_size =  super.readInt(SuperBlockEnum.s_log_frag_size);
        this.s_blocks_per_group =  super.readInt(SuperBlockEnum.s_blocks_per_group);
        this.s_frags_per_group =  super.readInt(SuperBlockEnum.s_frags_per_group);
        this.s_inodes_per_group =  super.readInt(SuperBlockEnum.s_inodes_per_group);
        this.s_mtime =  super.readInt(SuperBlockEnum.s_mtime);
        this.s_wtime =  super.readInt(SuperBlockEnum.s_wtime);
        this.s_lastcheck =  super.readInt(SuperBlockEnum.s_lastcheck);
        this.s_checkinterval =  super.readInt(SuperBlockEnum.s_checkinterval);
        this.s_creator_os =  super.readInt(SuperBlockEnum.s_creator_os);
        this.s_rev_level =  super.readInt(SuperBlockEnum.s_rev_level);
        this.s_mnt_count =  super.readInt(SuperBlockEnum.s_mnt_count);
        this.s_max_mnt_count =  super.readInt(SuperBlockEnum.s_max_mnt_count);
        this.s_magic =  super.readInt(SuperBlockEnum.s_magic);
        this.s_state =  super.readInt(SuperBlockEnum.s_state);
        this.s_errors =  super.readInt(SuperBlockEnum.s_errors);
        this.s_minor_rev_level =  super.readInt(SuperBlockEnum.s_minor_rev_level);
        this.s_def_resuid =  super.readInt(SuperBlockEnum.s_def_resuid);
        this.s_def_resgid =  super.readInt(SuperBlockEnum.s_def_resgid);

        this.s_first_ino =  super.readInt(SuperBlockEnum.s_first_ino);
        this.s_feature_compat =  super.readInt(SuperBlockEnum.s_feature_compat);
        this.s_feature_incompat =  super.readInt(SuperBlockEnum.s_feature_incompat);
        this.s_feature_ro_compat =  super.readInt(SuperBlockEnum.s_feature_ro_compat);
        this.s_algo_bitmap =  super.readInt(SuperBlockEnum.s_algo_bitmap);
        this.s_inode_size =  super.readInt(SuperBlockEnum.s_inode_size);
        this.s_block_group_nr =  super.readInt(SuperBlockEnum.s_block_group_nr);
        this.s_uuid = super.readUuid(SuperBlockEnum.s_uuid);
        this.s_volume_name = super.readString(SuperBlockEnum.s_volume_name);
        this.s_last_mounted = super.readString(SuperBlockEnum.s_last_mounted);

        this.s_prealloc_blocks =  super.readInt(SuperBlockEnum.s_prealloc_blocks);
        this.s_prealloc_dir_blocks =  super.readInt(SuperBlockEnum.s_prealloc_dir_blocks);
        this.s_prealloc_alignment =  super.readInt(SuperBlockEnum.s_prealloc_alignment);

        this.s_journal_inum =  super.readInt(SuperBlockEnum.s_journal_inum);
        this.s_journal_dev =  super.readInt(SuperBlockEnum.s_journal_dev);
        this.s_last_orphan =  super.readInt(SuperBlockEnum.s_last_orphan);
        this.s_journal_uuid = super.readUuid(SuperBlockEnum.s_journal_uuid);

        this.s_hash_seed = super.readArray(SuperBlockEnum.s_hash_seed, 4);
        this.s_def_hash_version =  super.readInt(SuperBlockEnum.s_def_hash_version);

        this.s_default_mount_options =  super.readInt(SuperBlockEnum.s_default_mount_options);
        this.s_first_meta_bg =  super.readInt(SuperBlockEnum.s_first_meta_bg);
        this.s_reserved = super.readArray(SuperBlockEnum.s_reserved);
    }

    public Dentry getSroot() {
        //GroupDescにあるinodeテーブルからルートディレクトリを探索してそのパス(Dentry)を返す
        return droot;
    }
}
