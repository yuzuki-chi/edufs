package FileSystem.Edufs;

import FileSystem.Block.Blocks;
import FileSystem.Value.*;

import java.io.RandomAccessFile;

abstract class ControlBlock<T extends BlockEnum> {
    private byte[] byteValue; // FileSystem.Value of read by random access file (byte).
    private final RandomAccessFile raf;

    public ControlBlock(RandomAccessFile raf) {
        this.raf = raf;
    }

    public Value read(long offset, ValueType valueType) {
        switch (valueType) {
            case BYTE:
            case SHORT:
            case INTEGER:
                return readInteger(offset, valueType);
            case STRING:
                return  readString(offset, valueType);
            case UUID:
                return readUUID(offset, valueType);
            case INTARRAY:
                return readArray(offset, valueType, 4);
            case BYTEARRAY:
                return readArray(offset, valueType, 1);
        }
        return null;
    }

    /* TODO */
    public Value write() {
        return null;
    }

    public Value read(long offset, ValueType valueType, int byteSize) {
        if (valueType == ValueType.INTARRAY) return readArray(offset, valueType, byteSize);
        else if (valueType == ValueType.BYTEARRAY) return readArray(offset, valueType, byteSize);
        else return null;
    }

    private IntegerValue readInteger(long offset, ValueType valueType) {
        return new IntegerValue(this.readElement(offset, valueType), false, false);
    }

    private StringValue readString(long offset, ValueType valueType) {
        return new StringValue(this.readElement(offset, valueType), false, false);
    }

    private UUIDValue readUUID(long offset, ValueType valueType) {
        return new UUIDValue(this.readElement(offset, valueType), false, false);
    }

    private ArrayValue readArray(long offset, ValueType valueType, int byteSize) {
        return new ArrayValue(this.readElement(offset, valueType, byteSize), valueType, false, false);
    }

    public byte[] readElement(long offset, ValueType valueType) {
        int size = valueType.getByteSize();
        return this.seekRead(offset, size);
    }

    public byte[] readElement(long offset, ValueType valueType, int byteSize) {
        int size = byteSize;
        return this.seekRead(offset, size);
    }

    private byte[] seekRead(long off, int len) {
        return new Blocks(this.raf, off, len).read();
    }
}
