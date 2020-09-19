package Values;

public class StringValue {
    private byte[] byteValues;
    private boolean signed;
    private String value = "";

    public StringValue(byte[] byteValues, boolean signed) {
        this.byteValues = byteValues;
        this.signed = signed;
    }

    public String getValue(){
        for (int i = 0; i < byteValues.length; i++) {
            value += (char) (signed == true ? byteValues[i] : Byte.toUnsignedLong(byteValues[i]));
        }
        return value;
    }
}
