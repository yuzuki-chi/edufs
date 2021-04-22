package FileSystem.Value;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class ArrayValue extends ValueImpl {
    private long[] value;
    private ValueType valueType;

    public ArrayValue(byte[] byteValue, ValueType valueType, Boolean signed, Boolean endian) {
        super(byteValue, signed, endian);
        this.valueType = valueType;
        this.setValue();
    }

    private void setValue() {
        Boolean signed = super.getSigned();
        Boolean endian = super.getEndian();
        byte[] byteValues = super.getByteValue();

        int byteSize = byteValues.length;
        int splitSize = valueType.getByteSize();

        //1. byteSize が splitSize で割り切れるか確認. 割り切れない場合は割り切れる個数の配列に作り直す.
        if(byteSize % splitSize != 0) byteSize += (splitSize - (byteSize - splitSize));

        //2. 実際のbyteの長さを計算し, その個数の配列を作る.
        int dolen = byteSize / splitSize;
        value = new long[dolen];

        //3. （仮想的に)分割したbyteValuesに対し, 次の処理を行う
        for (int i = 0; i < dolen; i++) {
            //3-1. byteValueの指定した範囲を新しい配列にコピーする
            byte[] splitArray = Arrays.copyOfRange(byteValues, i * splitSize, (i * splitSize) + splitSize);
            //3-2. 分割した配列をリトルエンディアンにする
            if(splitArray.length!=1) {
                int intBB = ByteBuffer.wrap(splitArray).order(ByteOrder.LITTLE_ENDIAN).getInt();
                //3-3. (この時点で正しい値になっているはずなので)value[i]に代入する
                value[i] = Integer.toUnsignedLong(intBB);
            } else {
                value[i] = Byte.toUnsignedLong(splitArray[0]);
            }
        }
    }

    public long[] getValue() {
        return value;
    }

    @Override
    public String toString() {
        byte[] array = new byte[this.value.length];
        for(int i=0; i<array.length; i++) array[i] = (byte)this.value[i];
        return new String(array);
    }

}
