package FileSystem;

public interface FileSystemTypeObject {
    Dentry mount(String fsType, int flags, String devName) throws Exception;
    SuperBlock getSb(FileSystemTypeObject type, int flags, String source) throws Exception;
    int killSb();
}
