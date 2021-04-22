package FileSystem.Edufs;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Arrays;

class SuperBlock extends ControlBlock<SuperBlockEnum> {
    private long s_inodes_count,        //inodeの総数
                 s_blocks_count,        //ファイルシステムの大きさ (block)
                 s_r_blocks_count,      //予約ブロック数
                 s_free_blocks_count,   //空きブロック数
                 s_free_inodes_count,   //空きinode数
                 s_first_data_block,    //使用可能なブロック群の先頭ブロック番号（常に1）
                 s_log_block_size,      //ブロックサイズ (1024の乗数)
                 s_log_frag_size,       //フラグメントのサイズ
                 s_blocks_per_group,    //1グループあたりのブロック数
                 s_frags_per_group,     //1グループあたりのフラグメント数
                 s_inodes_per_group,    //1グループあたりのinode数
                 s_mtime,               //最終マウント操作時間
                 s_wtime,               //最終書き込み操作時刻
                 s_lastcheck,           //最終fsck時間
                 s_checkinterval,       //fsckを行う時間間隔
                 s_creator_os,          //ファイルシステムを作成したOS
                 s_rev_level;           //改訂レベル
    private long s_mnt_count,           //マウント操作カウンタ
                 s_max_mnt_count,       //fsckを行う前までのマウント操作回数
                 s_magic,               //マジック番号
                 s_state,               //状態フラグ
                 s_errors,              //エラー検出時の動作
                 s_minor_rev_level,     //マイナー改訂レベル
                 s_def_resuid,          //予約ブロック用のデフォルトUID
                 s_def_resgid;          //予約ブロック用のデフォルトGID
    private long s_first_ino,           //最初の未予約inode番号
                 s_feature_compat,      //互換性のある機能の有効/無効を指定するビットマップ
                 s_feature_incompat,    //互換性のない機能の有効/無効を指定するビットマップ
                 s_feature_ro_compat,   //読み取り専用時に互換性のある機能のオン/オフを指定するビットマップ
                 s_algo_bitmap;         //圧縮処理で使用
    private long s_inode_size,          //ディスク上のinode構造体のサイズ
                 s_block_group_nr;      //このスーパーブロックのブロックグループ番号
    private String s_uuid,              //128ビットのファイルシステム識別子
                 s_volume_name,         //ボリューム名
                 s_last_mounted;        //前回のマウントポインタのパス名

    private long s_prealloc_blocks,     //先行割り当てを行うブロック数
                 s_prealloc_dir_blocks; //ディレクトリの場合に先行割り当てを行うブロック数
    private long s_prealloc_alignment;  //

    private long s_journal_inum,
                 s_journal_dev,
                 s_last_orphan; //int
    private String  s_journal_uuid;

    private long[] s_hash_seed; //int[]
    private long s_def_hash_version; //byte

    private long s_default_mount_options,
                 s_first_meta_bg; //int
    private long[] s_reserved; //構造体の大きさをちょうど1024バイトとするため, 足りない分をnullで埋めるためのメンバ

    private final int SUPERBLOCK_OFFSET = 1024;

    public SuperBlock(RandomAccessFile raf) throws FileNotFoundException {
        super(raf);
        setElements();
    }

    public void setElements() {
        this.s_inodes_count = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_inodes_count.getOffset(), SuperBlockEnum.s_inodes_count.getValueType()).getValue();
        this.s_blocks_count = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_blocks_count.getOffset(), SuperBlockEnum.s_blocks_count.getValueType()).getValue();
        this.s_r_blocks_count = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_r_blocks_count.getOffset(), SuperBlockEnum.s_r_blocks_count.getValueType()).getValue();
        this.s_free_blocks_count = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_free_blocks_count.getOffset(), SuperBlockEnum.s_free_blocks_count.getValueType()).getValue();
        this.s_free_inodes_count = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_free_inodes_count.getOffset(), SuperBlockEnum.s_free_inodes_count.getValueType()).getValue();
        this.s_first_data_block = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_first_data_block.getOffset(), SuperBlockEnum.s_first_data_block.getValueType()).getValue();
        this.s_log_block_size = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_log_block_size.getOffset(), SuperBlockEnum.s_log_block_size.getValueType()).getValue();
        this.s_log_frag_size = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_log_frag_size.getOffset(), SuperBlockEnum.s_log_frag_size.getValueType()).getValue();
        this.s_blocks_per_group = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_blocks_per_group.getOffset(), SuperBlockEnum.s_blocks_per_group.getValueType()).getValue();
        this.s_frags_per_group = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_frags_per_group.getOffset(), SuperBlockEnum.s_frags_per_group.getValueType()).getValue();
        this.s_inodes_per_group = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_inodes_per_group.getOffset(), SuperBlockEnum.s_inodes_per_group.getValueType()).getValue();
        this.s_mtime = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_mtime.getOffset(), SuperBlockEnum.s_mtime.getValueType()).getValue();
        this.s_wtime = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_wtime.getOffset(), SuperBlockEnum.s_wtime.getValueType()).getValue();
        this.s_lastcheck = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_lastcheck.getOffset(), SuperBlockEnum.s_lastcheck.getValueType()).getValue();
        this.s_checkinterval = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_checkinterval.getOffset(), SuperBlockEnum.s_checkinterval.getValueType()).getValue();
        this.s_creator_os = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_creator_os.getOffset(), SuperBlockEnum.s_creator_os.getValueType()).getValue();
        this.s_rev_level = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_rev_level.getOffset(), SuperBlockEnum.s_rev_level.getValueType()).getValue();
        this.s_mnt_count = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_mnt_count.getOffset(), SuperBlockEnum.s_mnt_count.getValueType()).getValue();
        this.s_max_mnt_count = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_max_mnt_count.getOffset(), SuperBlockEnum.s_max_mnt_count.getValueType()).getValue();
        this.s_magic = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_magic.getOffset(), SuperBlockEnum.s_magic.getValueType()).getValue();
        this.s_state = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_state.getOffset(), SuperBlockEnum.s_state.getValueType()).getValue();
        this.s_errors = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_errors.getOffset(), SuperBlockEnum.s_errors.getValueType()).getValue();
        this.s_minor_rev_level = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_minor_rev_level.getOffset(), SuperBlockEnum.s_minor_rev_level.getValueType()).getValue();
        this.s_def_resuid = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_def_resuid.getOffset(), SuperBlockEnum.s_def_resuid.getValueType()).getValue();
        this.s_def_resgid = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_def_resgid.getOffset(), SuperBlockEnum.s_def_resgid.getValueType()).getValue();

        this.s_first_ino = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_first_ino.getOffset(), SuperBlockEnum.s_first_ino.getValueType()).getValue();
        this.s_feature_compat = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_feature_compat.getOffset(), SuperBlockEnum.s_feature_compat.getValueType()).getValue();
        this.s_feature_incompat = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_feature_incompat.getOffset(), SuperBlockEnum.s_feature_incompat.getValueType()).getValue();
        this.s_feature_ro_compat = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_feature_ro_compat.getOffset(), SuperBlockEnum.s_feature_ro_compat.getValueType()).getValue();
        this.s_algo_bitmap = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_algo_bitmap.getOffset(), SuperBlockEnum.s_algo_bitmap.getValueType()).getValue();
        this.s_inode_size = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_inode_size.getOffset(), SuperBlockEnum.s_inode_size.getValueType()).getValue();
        this.s_block_group_nr = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_block_group_nr.getOffset(), SuperBlockEnum.s_block_group_nr.getValueType()).getValue();
        this.s_uuid = (String) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_uuid.getOffset(), SuperBlockEnum.s_uuid.getValueType()).getValue();
        this.s_volume_name = (String) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_volume_name.getOffset(), SuperBlockEnum.s_volume_name.getValueType()).getValue();
        this.s_last_mounted = (String) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_last_mounted.getOffset(), SuperBlockEnum.s_last_mounted.getValueType()).getValue();

        this.s_prealloc_blocks = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_prealloc_blocks.getOffset(), SuperBlockEnum.s_prealloc_blocks.getValueType()).getValue();
        this.s_prealloc_dir_blocks = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_prealloc_dir_blocks.getOffset(), SuperBlockEnum.s_prealloc_dir_blocks.getValueType()).getValue();
        this.s_prealloc_alignment = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_prealloc_alignment.getOffset(), SuperBlockEnum.s_prealloc_alignment.getValueType()).getValue();

        this.s_journal_inum = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_journal_inum.getOffset(), SuperBlockEnum.s_journal_inum.getValueType()).getValue();
        this.s_journal_dev = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_journal_dev.getOffset(), SuperBlockEnum.s_journal_dev.getValueType()).getValue();
        this.s_last_orphan = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_last_orphan.getOffset(), SuperBlockEnum.s_last_orphan.getValueType()).getValue();
        this.s_journal_uuid = (String) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_journal_uuid.getOffset(), SuperBlockEnum.s_journal_uuid.getValueType()).getValue();

        this.s_hash_seed = (long[]) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_hash_seed.getOffset(), SuperBlockEnum.s_hash_seed.getValueType(), 4).getValue();
        this.s_def_hash_version = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_def_hash_version.getOffset(), SuperBlockEnum.s_def_hash_version.getValueType()).getValue();

        this.s_default_mount_options = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_default_mount_options.getOffset(), SuperBlockEnum.s_default_mount_options.getValueType()).getValue();
        this.s_first_meta_bg = (long) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_first_meta_bg.getOffset(), SuperBlockEnum.s_first_meta_bg.getValueType()).getValue();
        this.s_reserved = (long[]) super.read(SUPERBLOCK_OFFSET + SuperBlockEnum.s_reserved.getOffset(), SuperBlockEnum.s_reserved.getValueType()).getValue();
    }

    public long getInodesCount() { return s_inodes_count; }

    public long getBlocksCount() { return s_blocks_count; }

    public long getR_BlocksCount() { return s_r_blocks_count; }

    public long getFreeBlocksCount() { return s_free_blocks_count; }

    public long getFreeInodesCount() { return s_free_inodes_count; }

    public long getFirstDataBlock() { return s_first_data_block; }

    public long getLogBlockSize() { return s_log_block_size; }

    public long getLogFragSize() { return s_log_frag_size; }

    public long getBlocksPerGroup() { return s_blocks_per_group; }

    public long getFragsPerGroup() { return s_frags_per_group; }

    public long getInodesPerGroup() { return s_inodes_per_group; }

    public long getMtime() { return s_mtime; }

    public long getWtime() { return s_wtime; }

    public long getLastcheck() { return s_lastcheck; }

    public long getCheckinterval() { return s_checkinterval; }

    public long getCreatorOs() { return s_creator_os; }

    public long getRevLevel() { return s_rev_level; }

    public long getMntCount() { return s_mnt_count; }

    public long getMaxMntCount() { return s_max_mnt_count; }

    public long getMagic() { return s_magic; }

    public long getState() { return s_state; }

    public long getErrors() { return s_errors; }

    public long getMinorRevLevel() { return s_minor_rev_level; }

    public long getDefResuid() { return s_def_resuid; }

    public long getDefResgid() { return s_def_resgid; }

    public long getFirstIno() { return s_first_ino; }

    public long getInodeSize() { return s_inode_size; }

    public long getBlockGroupNr() { return s_block_group_nr; }

    public long getFeatureCompat() { return s_feature_compat; }

    public long getFeatureIncompat() { return s_feature_incompat; }

    public long getFeatureRoCompat() { return s_feature_ro_compat; }

    public String getUuid() { return s_uuid; }

    public String getVolumeName() { return s_volume_name; }

    public String getLastMounted() { return s_last_mounted; }

    public long getAlgoBitmap() { return s_algo_bitmap; }

    public long getPreallocBlocks() { return s_prealloc_blocks; }

    public long getPreallocDirBlocks() { return s_prealloc_dir_blocks; }

    public long getPreallocAlignment() { return s_prealloc_alignment; }

    public String getJournalUuid() { return s_journal_uuid; }

    public long getJournalInum() { return s_journal_inum; }

    public long getJournalDev() { return s_journal_dev; }

    public long getLastOrphan() { return s_last_orphan; }

    public long[] getHashSeed() { return s_hash_seed; }

    public long getDefHashVersion() { return s_def_hash_version; }

    public long getDefaultMountOptions() { return s_default_mount_options; }

    public long getFirstMetaBg() { return s_first_meta_bg; }

    //public long[] getReserved() { return s_reserved; }

    @Override
    public String toString() {
        return "DeviceIO.SuperBlock{" +
                "s_inodes_count=" + s_inodes_count +
                ", s_blocks_count=" + s_blocks_count +
                ", s_r_blocks_count=" + s_r_blocks_count +
                ", s_free_blocks_count=" + s_free_blocks_count +
                ", s_free_inodes_count=" + s_free_inodes_count +
                ", s_first_data_block=" + s_first_data_block +
                ", s_log_block_size=" + s_log_block_size +
                ", s_log_frag_size=" + s_log_frag_size +
                ", s_blocks_per_group=" + s_blocks_per_group +
                ", s_frags_per_group=" + s_frags_per_group +
                ", s_inodes_per_group=" + s_inodes_per_group +
                ", s_mtime=" + s_mtime +
                ", s_wtime=" + s_wtime +
                ", s_lastcheck=" + s_lastcheck +
                ", s_checkinterval=" + s_checkinterval +
                ", s_creator_os=" + s_creator_os +
                ", s_rev_level=" + s_rev_level +
                ", s_mnt_count=" + s_mnt_count +
                ", s_max_mnt_count=" + s_max_mnt_count +
                ", s_magic=" + s_magic +
                ", s_state=" + s_state +
                ", s_errors=" + s_errors +
                ", s_minor_rev_level=" + s_minor_rev_level +
                ", s_def_resuid=" + s_def_resuid +
                ", s_def_resgid=" + s_def_resgid +
                ", s_first_ino=" + s_first_ino +
                ", s_feature_compat=" + s_feature_compat +
                ", s_feature_incompat=" + s_feature_incompat +
                ", s_feature_ro_compat=" + s_feature_ro_compat +
                ", s_algo_bitmap=" + s_algo_bitmap +
                ", s_inode_size=" + s_inode_size +
                ", s_block_group_nr=" + s_block_group_nr +
                ", s_uuid='" + s_uuid + '\'' +
                ", s_volume_name='" + s_volume_name + '\'' +
                ", s_last_mounted='" + s_last_mounted + '\'' +
                ", s_prealloc_blocks=" + s_prealloc_blocks +
                ", s_prealloc_dir_blocks=" + s_prealloc_dir_blocks +
                ", s_prealloc_alignment=" + s_prealloc_alignment +
                ", s_journal_inum=" + s_journal_inum +
                ", s_journal_dev=" + s_journal_dev +
                ", s_last_orphan=" + s_last_orphan +
                ", s_journal_uuid='" + s_journal_uuid + '\'' +
                ", s_hash_seed=" + Arrays.toString(s_hash_seed) +
                ", s_def_hash_version=" + s_def_hash_version +
                ", s_default_mount_options=" + s_default_mount_options +
                ", s_first_meta_bg=" + s_first_meta_bg +
                ", s_reserved=" + Arrays.toString(s_reserved) +
                '}';
    }
}
