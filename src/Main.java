public class Main {
    public static void main(String[] args) {
        int fd = VfsInmpl.open("./.gitignore", 777);
        byte[] b = new byte[4096];
        int size = 100;

        int ret = VfsInmpl.read(fd, b, size);
        System.out.println("fd: " + fd + "\n" +
                           "ret: " + ret + "\n" +
                           "String: " + new String(b)
                           );
    }
}
