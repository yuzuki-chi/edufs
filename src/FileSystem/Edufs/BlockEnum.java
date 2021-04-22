package FileSystem.Edufs;

import FileSystem.Value.ValueType;

interface BlockEnum {
    public abstract int getByteSize();
    public abstract long getOffset();
    public abstract ValueType getValueType();
}
