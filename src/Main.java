public class Main {
    public static void main(String[] args) throws Exception {
        /* mount */
        int err = SystemCall.sys_mount("/dev/disk3s1", "/Volume/ext2dev", "ext2", 777);
        if (err == 0) System.out.println("mount success!");
        else if (err == -1) System.out.println("mount faild...");

        /* open */
        int fd = SystemCall.sys_open("./.gitignore", 777);
        System.out.println("fd: " + fd );

        /* read */
        int size = 100;
        byte[] b = new byte[size];

        int ret = SystemCall.sys_read(fd, b, size);
        System.out.println("ret: " + ret + "\n" +
                           "String: " + new String(b));
    }
}
