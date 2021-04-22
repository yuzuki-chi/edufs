package FileSystem.Edufs;

import FileSystem.Edufs.Entry.Inode;

import java.io.RandomAccessFile;
import java.util.Arrays;

class InodeImpl extends ControlBlock<InodeEnum> implements Inode {
    private long i_mode,        //ファイルの種類とアクセス権
            i_uid,         //所有者のID
            i_gid,         //グループID
            i_links_count; //ハードリンクカウンタ
    private long i_size,        //ファイルの長さ (byte)
            i_atime,       //最終ファイルアクセス時刻
            i_ctime,       //最終inode変更時刻
            i_mtime,       //最終ファイル内容変更時刻
            i_dtime,       //ファイル削除時刻
            i_blocks,      //ファイルのデータブロック数
            i_flags,       //ファイルのフラグ
            i_osd1,        //OS固有の情報
            i_generation,  //ファイルのバージョン (NFS用)
            i_file_acl,    //ファイル用のアクセス制御リスト
            i_dir_acl,     //ディレクトリ用のアクセス制御リスト
            i_faddr;       //フラグメントのアドレス
    private long[] i_block;     //データブロックの番号

    private long offset;

    /**
     * @param raf 対象となるRandomAccessFile
     * @param offset 該当するinodeが存在する最初の絶対アドレス (byte)
     */
    public InodeImpl(RandomAccessFile raf, long offset) {
        super(raf);
        this.offset = offset;
        this.setElements();
    }

    public void setElements() {
        this.i_mode = (long) super.read(offset + InodeEnum.i_mode.getOffset(), InodeEnum.i_mode.getValueType()).getValue();
        this.i_uid = (long) super.read(offset + InodeEnum.i_uid.getOffset(), InodeEnum.i_uid.getValueType()).getValue();
        this.i_size = (long) super.read(offset + InodeEnum.i_size.getOffset(), InodeEnum.i_size.getValueType()).getValue();
        this.i_atime = (long) super.read(offset + InodeEnum.i_atime.getOffset(), InodeEnum.i_atime.getValueType()).getValue();
        this.i_ctime = (long) super.read(offset + InodeEnum.i_ctime.getOffset(), InodeEnum.i_ctime.getValueType()).getValue();
        this.i_mtime = (long) super.read(offset + InodeEnum.i_mtime.getOffset(), InodeEnum.i_mtime.getValueType()).getValue();
        this.i_dtime = (long) super.read(offset + InodeEnum.i_dtime.getOffset(), InodeEnum.i_dtime.getValueType()).getValue();
        this.i_gid = (long) super.read(offset + InodeEnum.i_gid.getOffset(), InodeEnum.i_gid.getValueType()).getValue();
        this.i_links_count = (long) super.read(offset + InodeEnum.i_links_count.getOffset(), InodeEnum.i_links_count.getValueType()).getValue();
        this.i_blocks = (long) super.read(offset + InodeEnum.i_blocks.getOffset(), InodeEnum.i_blocks.getValueType()).getValue();
        this.i_flags = (long) super.read(offset + InodeEnum.i_flags.getOffset(), InodeEnum.i_flags.getValueType()).getValue();
        this.i_osd1 = (long) super.read(offset + InodeEnum.i_osd1.getOffset(), InodeEnum.i_osd1.getValueType()).getValue();
        this.i_block = (long[]) super.read(offset + InodeEnum.i_block.getOffset(), InodeEnum.i_block.getValueType(), InodeEnum.i_block.getByteSize()).getValue();
        this.i_generation = (long) super.read(offset + InodeEnum.i_generation.getOffset(), InodeEnum.i_generation.getValueType()).getValue();
        this.i_file_acl = (long) super.read(offset + InodeEnum.i_file_acl.getOffset(), InodeEnum.i_file_acl.getValueType()).getValue();
        this.i_dir_acl = (long) super.read(offset + InodeEnum.i_dir_acl.getOffset(), InodeEnum.i_dir_acl.getValueType()).getValue();
        this.i_faddr = (long) super.read(offset + InodeEnum.i_faddr.getOffset(), InodeEnum.i_faddr.getValueType()).getValue();
    }

    public long getI_mode() { return i_mode; }
    public long[] getI_block() { return i_block; }

    public int getFileFormat() {
        int ret = (int) (this.i_mode & Integer.toUnsignedLong(0b1111000000000000));
        ret = ret >>> 12;
        return ret;
    }

    @Override
    public String toString() {
        return "DeviceIO.Inode{" +
                "i_mode=" + i_mode +
                ", i_uid=" + i_uid +
                ", i_gid=" + i_gid +
                ", i_links_count=" + i_links_count +
                ", i_size=" + i_size +
                ", i_atime=" + i_atime +
                ", i_ctime=" + i_ctime +
                ", i_mtime=" + i_mtime +
                ", i_dtime=" + i_dtime +
                ", i_blocks=" + i_blocks +
                ", i_flags=" + i_flags +
                ", i_osd1=" + i_osd1 +
                ", i_generation=" + i_generation +
                ", i_file_acl=" + i_file_acl +
                ", i_dir_acl=" + i_dir_acl +
                ", i_faddr=" + i_faddr +
                ", i_block=" + Arrays.toString(i_block) +
                '}';
    }
}

