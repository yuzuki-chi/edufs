package FileSystem;

public interface FileSystemType {
    SuperBlock getSb(FileSystemType type, int flags, String source) throws Exception;
}
