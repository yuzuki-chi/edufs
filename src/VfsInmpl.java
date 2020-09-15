import java.util.List;

public class VfsInmpl implements Vfs {
    List<File> fileList;

    public int open(String filename, int mode) {
        return 0;
    }

    public int read(int fd, byte[] b, int size) {
        File f = fileList.get(fd);
        f.getPage(b, 0);
        return 0;
    }
}
