public interface Directory extends File {
    abstract File getFile(String filename); //特定のFileをgetする
}
