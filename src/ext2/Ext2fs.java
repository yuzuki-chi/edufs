package ext2;

import File.File;
import FileSystem.*;

public class Ext2fs implements FileSystem {

    public Ext2fs() {
    }

    /**
     * @param fsType
     * @param flags
     * @param devName
     * @return ルートディレクトリのdentryを返す
     */
    public Dentry mount(FileSystemTypeObject fsType, int flags, String devName) {
        return null;
    }

    public File getFile(int ino) {
        return null;
    }
}
