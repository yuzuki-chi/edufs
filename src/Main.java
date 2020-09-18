import File.VfsInmpl;

public class Main {
    public static void main(String[] args) {
        /* mount */
        int err = VfsInmpl.mount("/dev/ext2dev", "/mnt/ext2dev", "ext2");
        if (err == 0) System.out.println("mount success!");

        /* open */
        int fd = VfsInmpl.open("./.gitignore", 777);
        System.out.println("fd: " + fd );

        /* read */
        int size = 100;
        byte[] b = new byte[size];

        int ret = VfsInmpl.read(fd, b, size);
        System.out.println("ret: " + ret + "\n" +
                           "String: " + new String(b));
    }
}
