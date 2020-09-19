package FileSystem;

import Values.ValueType;

public interface BlockEnum {
    int getByteSize();
    long getOffset();
    ValueType getValueType();
}
