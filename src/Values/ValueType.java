package Values;

public enum ValueType {
    BYTE(1),
    SHORT(2),
    INTEGER(4),
    LONG(8),
    STRING(0),
    UUID(16),
    BYTEARRAY(0),
    INTARRAY(0),
    ;

    private int byteSize;
    private ValueType(int byteSize) {
        this.byteSize = byteSize;
    }

    public int getByteSize() { return byteSize; }
}
