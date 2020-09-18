package File;

public interface Directory extends File {
    File getFile(String filename); //特定のFileをgetする
}
