package Values;

public class UuidValue {
    private byte[] byteValues;
    private boolean signed;
    private String value = "";

    public UuidValue(byte[] byteValues, boolean signed) {
        this.byteValues = byteValues;
        this.signed = signed;
    }

    public String getValue() {
        //8桁-4桁-4桁-4桁-12桁にハイフンで区切る
        //XXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX
        for (int i = 16; i > 0; i--) {
            if (i == 6 || i == 8 || i == 10 || i == 12) value += "-";
            value += String.format("%02X", (signed == true ? byteValues[i - 1] : Byte.toUnsignedLong(byteValues[i - 1]) & 0xFF));
        }
        return value;
    }
}
