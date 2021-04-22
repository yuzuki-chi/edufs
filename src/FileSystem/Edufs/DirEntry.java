package FileSystem.Edufs;

import java.io.RandomAccessFile;

class DirEntry extends ControlBlock<DirEntryEnum_2> {
    private int inode; //int
    private short rec_len; //short
    private byte name_len, file_type; //byte
    private String name;

    private long addr;  //エントリアドレス

    public DirEntry(RandomAccessFile raf, long addr) {
        super(raf);
        this.addr = addr;
        this.setElements();
    }

    private void setElements() {
        this.inode = Integer.parseInt( String.valueOf( super.read( addr + DirEntryEnum_2.inode.getOffset(), DirEntryEnum_2.inode.getValueType() ).getValue() ) );
        this.rec_len = Short.parseShort( String.valueOf( super.read( addr + DirEntryEnum_2.rec_len.getOffset(), DirEntryEnum_2.rec_len.getValueType() ).getValue() ) );
        this.name_len = Byte.parseByte( String.valueOf( super.read( addr + DirEntryEnum_2.name_len.getOffset(), DirEntryEnum_2.name_len.getValueType() ).getValue() ) );
        this.file_type = Byte.parseByte( String.valueOf( super.read( addr + DirEntryEnum_2.file_type.getOffset(), DirEntryEnum_2.file_type.getValueType() ).getValue() ) );
        this.name = super.read( addr + DirEntryEnum_2.name.getOffset(), DirEntryEnum_2.name.getValueType(), this.name_len ).toString();
    }

    public int getInode() { return (int) inode; }

    public int getRec_len() { return (int) rec_len; }

    public int getName_len() { return (int) name_len; }

    public String getName() { return name; }

    public byte getFile_type() { return (byte) file_type; }

    @Override
    public String toString() {
        return "DeviceIO.DirEntry{" +
                "inode=" + inode +
                ", rec_len=" + rec_len +
                ", name_len=" + name_len +
                ", file_type=" + file_type +
                ", name='" + name + '\'' +
                '}';
    }
}
