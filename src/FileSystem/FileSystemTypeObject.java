package FileSystem;

public interface FileSystemTypeObject {
    SuperBlock getSb(FileSystemTypeObject type, int flags, String source);
}
