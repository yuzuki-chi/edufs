package Values;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class IntegerValue {
    private byte[] byteValues;
    private boolean signed;
    private long value;

    public IntegerValue(byte[] byteValues, boolean signed) {
        this.byteValues = byteValues;
        this.signed = signed;
    }

    public long getValue() {
        switch(byteValues.length) {
            case 1: //1byte
                value = signed == true ? byteValues[0] : Byte.toUnsignedLong(byteValues[0]);
                break;
            case 2:
                if (signed == true) value = ByteBuffer.wrap(byteValues).getShort();
                else value = Short.toUnsignedLong(ByteBuffer.wrap(byteValues).order(ByteOrder.LITTLE_ENDIAN).getShort());
                break;
            case 3:
            case 4:
                if (signed == true) value = ByteBuffer.wrap(byteValues).getInt();
                else value = Integer.toUnsignedLong(ByteBuffer.wrap(byteValues).order(ByteOrder.LITTLE_ENDIAN).getInt());
                break;
        }
        return value;
    }
}
