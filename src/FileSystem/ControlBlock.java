package FileSystem;

public interface ControlBlock<T extends BlockEnum> {
    long readInt(T t);

    String readString(T t);

    String readUuid(T t);

    long[] readArray(T t, int byteSize);

    long[] readArray(T t);

    byte[] readElement(T t);
}
