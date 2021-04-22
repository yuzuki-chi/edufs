package FileSystem.Edufs.Entry;

import java.util.ArrayList;
import java.util.List;

public class DirectoryEntry implements Entry {
    private long inumber;
    private Inode inode;
    private String fileName;

    private List<Entry> entryList = new ArrayList<>();

    public DirectoryEntry(Inode inode) throws FileException {
        this.inode = inode;
        if( !isDir( inode ) ) throw new FileException();    //-> directoryじゃない時のエラー
    }

    public DirectoryEntry(long inumber, Inode inode, String fileName) {
        this.inumber = inumber;
        this.inode = inode;
        this.fileName = fileName;
        if( !isDir(inode) ) throw new FileException();    //-> directoryじゃない時のエラー
    }

    private boolean isDir(Inode inode) {
        long mode = inode.getI_mode() & 0xF000;
        if( mode != 0x4000 ) return false;
        return true;
    }

    public void add(Entry entry) {
        entryList.add(entry);
    }

    public long getInumber() { return inumber; }

    public List<Entry> getList() {
        return entryList;
    }

    public Inode getInode() { return inode; }

    public String getFileName() { return fileName; }
}
