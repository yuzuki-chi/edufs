package FileSystem;

import File.File;

public interface FileSystem {
    File getFile(int ino);
}
