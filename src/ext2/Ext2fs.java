package ext2;

import File.File;
import FileSystem.*;

public class Ext2fs implements FileSystem {
    FileSystemTypeObject ext2FsType;

    public Ext2fs() {
        ext2FsType= new FileSystemTypeObjectImpl("ext2", 0);
    }

    /**
     * @param fsType
     * @param flags
     * @param devName
     * @return ルートディレクトリのdentryを返す
     */
    public Dentry mount(String fsType, int flags, String devName) throws Exception {
        return Super.mountBdev(ext2FsType, flags, devName);
    }

    public File getFile(int ino) {
        return null;
    }
}
