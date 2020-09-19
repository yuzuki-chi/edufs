package FileSystem;

import Values.ArrayValue;
import Values.IntegerValue;
import Values.StringValue;
import Values.UuidValue;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ControlBlock<T extends BlockEnum> {
    private byte[] byteValue; // Value of read by random access file (byte).
    private RandomAccessFile raf;

    public ControlBlock(File file) throws Exception {
        this.raf = new RandomAccessFile(file, "rw");
    }

    public long readInt(T t) {
        return new IntegerValue(this.readElement(t), false).getValue();
    }

    public String readString(T t) {
        return new StringValue(this.readElement(t), false).getValue();
    }

    public String readUuid(T t) {
        return new UuidValue(this.readElement(t), false).getValue();
    }

    public long[] readArray(T t, int byteSize) {
        return new ArrayValue(this.readElement(t), byteSize, false).getValue();
    }

    public long[] readArray(T t) {
        return new ArrayValue(this.readElement(t), false).getValue();
    }

    public byte[] readElement(T t) {
        long offset = t.getOffset();
        int size = t.getByteSize();

        byteValue = new byte[size];
        try {
            raf.seek(offset);
        } catch (IOException e) {
            e.printStackTrace(); //posが0より小さい場合、あるいは入出力エラーが発生した場合
        }
        try {
            for (int i=0; i<size; i++) {
                byteValue[i] = raf.readByte();
            }
        } catch (Exception e) { //EOFException, IOException
            e.printStackTrace();
        }
        return byteValue;
    }
}
