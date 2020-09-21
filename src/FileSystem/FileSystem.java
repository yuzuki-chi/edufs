package FileSystem;

import FileObject.File;

public interface FileSystem {
    File getFile(int ino);
}
