package FileSystem.Block;

import java.io.*;

/**
 * Block class
 * 最小単位のブロックをバイト単位で読み出す
 */
public class Block {
   private final RandomAccessFile raf;
   private long pos;                // ファイルの先頭を始点とした、バイト単位のオフセット位置。この位置にファイル・ポインタが設定される。

   public Block(RandomAccessFile raf, long address) {
      this.raf = raf;
      this.pos = address;
   }

   public long getPos() {
      return pos;
   }

   public byte read() {
      /* RandomAccess.open -> seek -> read -> close */
      byte b;

      try {
         raf.seek(pos);
         b = raf.readByte();
      } catch (IOException e) {     //posが0より小さい場合、あるいは入出力エラーが発生した場合。
         e.printStackTrace();
         return -1;
      }

      return b;
   }

   public int write( byte b ) {
      try {
         raf.seek(pos);
         raf.write(b);
      } catch (IOException e) {
         e.printStackTrace();
         return -1;
      }
      return 1;
   }
}
