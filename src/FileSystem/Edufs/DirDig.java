package FileSystem.Edufs;

import FileSystem.Edufs.Entry.*;

import java.io.RandomAccessFile;

class DirDig {
    private final RandomAccessFile raf;
    private final InodeImpl inode;
    private DirectoryEntry rootdir;
    private final InodeTable it;

    /* super block's info */
    private final int blockSize;

    /**
     * Entryを使って, ファイルの探索を行うクラス
     * @param raf 対象のRandomAccessFile
     * @param sb ターゲットとなるデバイスファイルのSuperBlock
     * @param it ターゲットとなるデバイスファイルのInodeTable
     * @param inode 探索を開始するディレクトリのinode番号
     */
    public DirDig(RandomAccessFile raf, SuperBlock sb, InodeTable it, int inode ) {
        this.raf = raf;
        this.it = it;
        this.inode = it.get( inode );
        this.blockSize = 1024 << sb.getLogBlockSize();

        this.dig();
    }

    private void dig() {
        /* Ext2 file type */
        final byte EXT2_FT_UNKNOWN  = 0;    //未知のファイルシステム
        final byte EXT2_FT_REG_FILE = 1;    //通常ファイル
        final byte ETX2_FT_DIR      = 2;    //ディレクトリファイル
        final byte EXT2_FT_CHRDEV   = 3;    //キャラクターデバイス
        final byte EXT2_FT_BLKDEV   = 4;    //ブロックデバイス
        final byte EXT2_FT_FIFO     = 5;    //バッファーファイル
        final byte EXT2_FT_SOCK     = 6;    //ソケットファイル
        final byte EXT2_FT_SYMLINK  = 7;    //シンボリックリンク
        /* --------------- */

        rootdir = new DirectoryEntry( this.inode );
        long[] iBlock = this.inode.getI_block();
        long block, offset = 0;
        DirEntry dirEntry;

        for( int i = 0; i < iBlock.length; i++ ) {
            if( iBlock[i] == 0 ) {
                continue;
            }
            block = iBlock[i];
            int countSize = 0; //ブロックサイズ以上の読み出しを行わないためのカウント変数

            while( true ) {
                if( countSize >= this.blockSize ) {
                    break;
                }

                dirEntry = new DirEntry( raf, block * this.blockSize + offset );
                //DirEntryのfileTypeが0(EXT2_FT_UNKNOWN)になるまでレコードを取得し続ける

                if ( dirEntry.getFile_type() == EXT2_FT_UNKNOWN ) {
                    break;
                }

                switch ( dirEntry.getFile_type() ) {
                    case EXT2_FT_REG_FILE:
                        //通常ファイルとして new File()
                        rootdir.add(new FileEntry(dirEntry.getInode(), it.get(dirEntry.getInode()), dirEntry.getName()));
                        break;
                    case ETX2_FT_DIR:
                        //ディレクトリとして new Directory()
                        rootdir.add(new DirectoryEntry(dirEntry.getInode(), it.get(dirEntry.getInode()), dirEntry.getName()));
                        break;
                    case EXT2_FT_CHRDEV:
                    case EXT2_FT_BLKDEV:
                    case EXT2_FT_FIFO:
                    case EXT2_FT_SOCK:
                    case EXT2_FT_SYMLINK:
                        //いずれ対応
                        break;
                    default:
                        throw new FileException();
                }

                offset += dirEntry.getRec_len();
                countSize += dirEntry.getRec_len();
            }
        }
    }

    public DirectoryEntry getRoot() {
        return rootdir;
    }
}
