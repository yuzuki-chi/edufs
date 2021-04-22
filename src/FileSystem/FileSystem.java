package FileSystem;

import File.File;

public interface FileSystem {
    File getFile(int ino);
    Dentry mount(String fsType, int flags, String devName) throws Exception;
}
