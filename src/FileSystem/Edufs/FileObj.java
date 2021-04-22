package FileSystem.Edufs;

public interface FileObj {
    int read(byte[] b, long offset, int size);
    int write(byte[] b, long offset, int size);
    void close();
}
