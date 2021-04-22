package FileSystem.Value;

public class UUIDValue extends ValueImpl {
    private String value;

    public UUIDValue(byte[] byteValue, Boolean signed, Boolean endian) {
        super(byteValue, signed, endian);
        this.setValue();
    }

    private void setValue() {
        Boolean signed = super.getSigned();
        Boolean endian = super.getEndian();
        byte[] byteValues = super.getByteValue();

        //8桁-4桁-4桁-4桁-12桁にハイフンで区切る
        //XXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX
        for (int i = 16; i > 0; i--) {
            if (i == 6 || i == 8 || i == 10 || i == 12) value += "-";
            value += String.format("%02X", (signed == true ? byteValues[i - 1] : Byte.toUnsignedLong(byteValues[i - 1]) & 0xFF));
        }
    }

    public String getValue() {
        return value;
    }
}
