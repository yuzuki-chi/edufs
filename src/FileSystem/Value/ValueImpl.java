package FileSystem.Value;

public class ValueImpl implements Value {
    private byte[] byteValue;
    private Boolean signed;
    private Boolean endian;

    protected ValueImpl(byte[] byteValue, Boolean signed, Boolean endian) {
        this.byteValue = byteValue;
        this.signed = signed;
        this.endian = endian;
    }

    public Boolean getEndian() { return endian; }

    public Boolean getSigned() { return signed; }

    public byte[] getByteValue() { return byteValue; }

    @Override
    public Object getValue() {
        return null;
    }
}
