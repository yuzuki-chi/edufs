package FileSystem.Edufs.Entry;

public interface Inode {
    void setElements();

    long getI_mode();
    long[] getI_block();
    int getFileFormat();
}
