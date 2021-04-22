package FileSystem.Edufs;

import FileSystem.Block.Blocks;
import FileSystem.Edufs.Entry.Inode;
import java.io.RandomAccessFile;

public class FileObjImpl implements FileObj {
    private RandomAccessFile raf;   //デバイスファイルに対してのストリーム
    private int blockSize;
    private Inode inode;
    private String mode;

    /**
     * @param raf
     * @param blockSize SuperBlockで決められているブロックサイズ
     * @param inode Inode型のinode
     * @param mode openするモード ("r", "w", "rw" ...)
     */
    public FileObjImpl( RandomAccessFile raf, int blockSize, Inode inode, String mode ) {
        this.raf = raf;
        this.blockSize = blockSize;
        this.inode = inode;
        this.mode = mode;
    }

    @Override
    public int read( byte[] b, long off, int len ) {
        /* TODO:
         * 1. FileObjのパーミッション確認
         * 2. modeごとの分岐
         */
        try {
            long[] block = inode.getI_block(); //データの開始ブロック

            switch ( inode.getFileFormat() ) {
                case (4): //ディレクトリ
                    return 0;

                case (8): //通常ファイル
                    //todo: Blocksクラスを適応しよう
                    int cnt = 0; //読み取る全てのバイトの合計(len)となる
                    byte[] data = new byte[this.blockSize];

                    int readcnt = 0;
                    for (int i = 0; i < block.length; i++) {
                        if (block[i] == 0) continue;
                        raf.seek(block[i] * this.blockSize + off);
                        for (int bcnt = 0; bcnt < this.blockSize; bcnt++) {
                            data[bcnt] = raf.readByte();
                            if (data[bcnt] != 0) readcnt++;

                            b[cnt] = data[bcnt];
                            if (b[cnt] == 0) return readcnt;
                            cnt++;
                            if (cnt >= len) return readcnt;
                        }
                    }
                    return readcnt;

                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println("ファイルの読み出しに失敗しました. (" + this.inode + ")");
            return -1;
        }
        return 0;
    }

    @Override
    public int write( byte[] b, long off, int len ) {
        try {
            long[] block = inode.getI_block();

            switch ( inode.getFileFormat() ) {
                case (4): //directory
                    return 0;

                case (8):
                    //todo: byte[] bを書き込みに適切な形に変換してあげる
                    byte[] writeBytes = b;

                    return new Blocks(raf, off, len).write( writeBytes );
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("write: エラーが発生しました");
            return -1;
        }
        return 0;
    }

    @Override
    public void close() {
        /* ここでrafをcloseしてはいけない */
    }
}
