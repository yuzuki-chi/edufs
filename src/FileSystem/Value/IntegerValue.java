package FileSystem.Value;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class IntegerValue extends ValueImpl {
    private long value;

    public IntegerValue(byte[] byteValue, Boolean signed, Boolean endian) {
        super(byteValue, signed, endian);
        this.setValue();
    }

    private void setValue() {
        Boolean signed   = super.getSigned();
        Boolean endian   = super.getEndian();
        byte[] byteValues = super.getByteValue();

        switch ( byteValues.length ) {
            case 1:
                value = signed == true ? byteValues[0] : Byte.toUnsignedLong( byteValues[0] );
                break;
            case 2:
                if (signed == true) value = ByteBuffer.wrap( byteValues ).getShort();
                else value = Short.toUnsignedLong( ByteBuffer.wrap( byteValues ).order( ByteOrder.LITTLE_ENDIAN ).getShort() );
                break;
            case 3:
            case 4:
                if (signed == true) value = ByteBuffer.wrap( byteValues ).getInt();
                else value = Integer.toUnsignedLong( ByteBuffer.wrap( byteValues ).order( ByteOrder.LITTLE_ENDIAN ).getInt() );
                break;
        }
    }

    public Long getValue() {
        return (long) value;
    }
}
