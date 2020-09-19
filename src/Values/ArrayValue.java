package Values;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class ArrayValue {
    private byte[] byteValues; // Value of read by random access file (byte).
    private int byteSize; // The number of size in the array
    private boolean signed; // signed or unsigned.
    private long[] value;

    public ArrayValue(byte[] byteValues, int byteSize, boolean signed) {
        this.byteValues = byteValues;
        this.byteSize = byteSize;
        this.signed = signed;
    }

    public ArrayValue(byte[] byteValues, boolean signed) {
        this(byteValues, 4, signed);
    }

    public long[] getValue() {
        //https://elixir.bootlin.com/linux/latest/source/fs/ext2/ext2.h#L417
        //s_hash_seed: 32bit(4byte)t値の4つの要素を持つ配列
        //byteValues[] = 32bitを想定
        int diff = byteValues.length % byteSize; //TODO: これが0ではないと割り切れず、余った配列が無視されてしまう.
        int dolen = byteValues.length / byteSize;
        value = new long[dolen];
        for (int i = 0; i < dolen; i++) {
            value[i] = Integer.toUnsignedLong(ByteBuffer.wrap(Arrays.copyOfRange(byteValues, i * byteSize, (i * byteSize) + byteSize)).order(ByteOrder.LITTLE_ENDIAN).getInt());
        }
        return value;
    }
}
