public interface Vfs {
    int open(String filename, int mode);
    int read(int fd, byte[] b, int size);
}
