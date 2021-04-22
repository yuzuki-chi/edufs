package Test;

import FileSystem.Value.*;

public class ValueTest {
    public static void main(String[] args) {
        byte[] b = {(byte)255};
        long b_value = new IntegerValue(b, false, true).getValue();
        System.out.println(b_value);

        byte[] ab = {(byte)255, (byte)255, (byte)10, (byte)255};
        long[] a_value = new ArrayValue(ab, ValueType.BYTE, false, true).getValue();
        for( int i = 0; i < a_value.length; i++ ) {
            System.out.println("["+i+"] :" + a_value[i]);
        }

        byte[] sb = {(byte)0b00000000, (byte)0b00000000, (byte)0b0, (byte)0b00000000};
        String s_value = new StringValue(b, false, true).getValue();
        System.out.println(s_value);
    }
}
