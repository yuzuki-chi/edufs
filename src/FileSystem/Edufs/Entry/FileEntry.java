package FileSystem.Edufs.Entry;

public class FileEntry implements Entry {
    private long inumber;
    private Inode inode;
    private String fileName;

    public FileEntry(Inode inode) {
        this.inode = inode;
    }

    public FileEntry(long inumber, Inode inode, String fileName) {
        this.inumber = inumber;
        this.fileName = fileName;
        this.inode = inode;
    }

    public long getInumber() { return inumber; }

    public Inode getInode() { return inode; }

    public String getFileName() { return fileName; }
}
