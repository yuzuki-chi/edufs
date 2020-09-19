package ext2;

import FileSystem.BlockEnum;
import Values.ValueType;

public enum SuperBlockElement implements BlockEnum {
    ;

    public int getByteSize() { return 0; }

    public long getOffset() { return 0; }

    public ValueType getValueType() { return null; }
}