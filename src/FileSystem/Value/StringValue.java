package FileSystem.Value;

public class StringValue extends ValueImpl {
    private String value;

    public StringValue(byte[] byteValue, Boolean signed, Boolean endian) {
        super(byteValue, signed, endian);
        this.setValue();
    }

    private void setValue() {
        Boolean signed = super.getSigned();
        Boolean endian = super.getEndian();
        byte[] byteValues = super.getByteValue();

        for ( int i = 0; i < byteValues.length; i++ ) {
            value += (char) (signed == true ? byteValues[i] : Byte.toUnsignedLong(byteValues[i]));
        }
    }

    public String getValue() {
        return value;
    }
}
