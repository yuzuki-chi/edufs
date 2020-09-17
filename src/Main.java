public class Main {
    public static void main(String[] args) {
        int fd = VfsInmpl.open("input.txt", 777);
        System.out.println(fd);
        int fd2 = VfsInmpl.open("input2.txt", 744);
        System.out.println(fd2);
        int fd3 = VfsInmpl.open("input3.txt", 722);
        System.out.println(fd3);

        byte[] b = new byte[4096];
        int ret = VfsInmpl.read(fd3, b, 100);
        System.out.println(ret + "\n" + new String(b));
    }
}
