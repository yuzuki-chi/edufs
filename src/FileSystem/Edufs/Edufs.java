package FileSystem.Edufs;

import FileSystem.Edufs.Entry.*;
import FileSystem.FileSystem;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Edufs implements FileSystem {
    protected final RandomAccessFile FILE;
    protected final SuperBlock SUPER_BLOCK;
    protected final GroupDesc GROUP_DESC;
    protected final InodeTable INODE_TABLE;
    protected final Bitmap BLOCK_BITMAP;
    protected final Bitmap INODE_BITMAP;

    protected final int BLOCK_SIZE;
    protected final long DEVICE_SIZE, GROUP_SIZE;

    protected final DirectoryEntry ROOT_DIR;

    public Edufs(String devFileName) throws Exception {
        FILE = new RandomAccessFile(new File( devFileName ), "rw");

        //1. SuperBlockの値をstaticで保持
        System.out.println("Reading SUPER BLOCK ... ");
        SUPER_BLOCK = new SuperBlock(FILE);

        BLOCK_SIZE = 1024 << SUPER_BLOCK.getLogBlockSize();         //ブロックサイズ (byte)
        GROUP_SIZE = SUPER_BLOCK.getBlocksPerGroup() * BLOCK_SIZE;  //ブロックグループのサイズ (byte)
        DEVICE_SIZE = SUPER_BLOCK.getBlocksCount() * BLOCK_SIZE;    //デバイスファイルのサイズ (byte)

        //2. GroupDescの値をstaticで保持
        System.out.println("Reading GROUP DESC ... ");
        GROUP_DESC = new GroupDesc(FILE, BLOCK_SIZE);

        //3. DataBlockBitmapとinodeBitmapを取得する
        /* TODO: コンストラクタで"全グループ"のビットマップを結合して保持する */
        System.out.println("Reading BITMAP ... ");
        BLOCK_BITMAP = new Bitmap(FILE, SUPER_BLOCK, GROUP_DESC.getBlockBitmap(), 0);
        INODE_BITMAP = new Bitmap(FILE, SUPER_BLOCK, GROUP_DESC.getInodeBitmap(), 0);

        //4. inodeテーブルは全てのinodeを1つのリストに保持する
        System.out.println("Reading INODE TABLE ... ");
        INODE_TABLE = new InodeTable(FILE, SUPER_BLOCK, GROUP_DESC.getInodeTable());

        //5. FileAnalyseするクラスを作成
        System.out.println("Analysing file ...");
        ROOT_DIR = new DirDig(FILE, SUPER_BLOCK, INODE_TABLE, 2).getRoot();
    }

    public DirectoryEntry getRoot() { return ROOT_DIR; }

    /**
     * fileName -> i-num
     * fileNameからi-numberを特定する関数
     * @param fileName
     * @return long型のi-number
     */
    private long getInumber(String fileName) {
        List<Entry> entryList = ROOT_DIR.getList();
        for (Entry entry : entryList) {
            if (fileName.equals(entry.getFileName()))
                return entry.getInumber();
        }
        return 0;
    }

    /**
     * i-num -> inode
     * i-numberからinodeを特定する関数
     * @param inum
     * @return Inode型のinode情報
     */
    private Inode getInode( long inum ) {
        return INODE_TABLE.get((int) inum);
    }

    public FileObj open( String filePath, String mode ) {
        //1. 名前からi-numberを特定する関数
        long inum = getInumber( filePath );
        //2. i-numberからinodeを特定する関数
        Inode inode = getInode( inum );
        return new FileObjImpl( FILE , BLOCK_SIZE, inode, mode );

        //todo: NotFoundExceptionを追記する
    }

    public void umount() {
        try {
            FILE.close();
        } catch (IOException e) {
            System.out.println("アンマウントできませんでした.");
        }
    }

    protected List<Integer> treeLevel;

    public void tree(DirectoryEntry dir) {
        treeLevel = new ArrayList<>(); //階層ごとにtabをしたりする用
        //INODE_TABLEに該当する先頭のディレクトリのinodeを取得してtreeを開始する
        int inode = INODE_TABLE.get().indexOf(dir.getInode()) + 1;
        tree(0, inode);
    }

    private void tree(int beforeInode, int inode) {
        treeLevel.add(1);
        List<Entry> entryList = new DirDig(FILE, SUPER_BLOCK, INODE_TABLE, inode).getRoot().getList();
        for(int i=0; i<entryList.size(); i++) {
            for(int tl=1; tl<treeLevel.size(); tl++) {
                if(treeLevel.get(tl-1) != 0) System.out.print("|");
                else System.out.print(" ");
                System.out.print("   ");
            }

            if(i != entryList.size()-1) System.out.print("├");
            else System.out.print("└");

            if(i == entryList.size()-1) treeLevel.set(treeLevel.size()-1, 0);

            System.out.println("── " + entryList.get(i).getFileName());
            if((entryList.get(i).getInode().getI_mode() & 0xF000) == 0x4000) {
                int nextInode = INODE_TABLE.get().indexOf(entryList.get(i).getInode()) + 1; //List.indexOfはエラーに-1を返す
                //呼び出し前のinode(..)と呼び出し中のinode(.)は探索を行わない
                if(nextInode > 0 && nextInode != beforeInode && nextInode != inode) {
                    tree(inode, nextInode);
                    treeLevel.remove(treeLevel.size()-1);
                }
            }
        }
    }

    public void ls(DirectoryEntry dir) {
        int inode = INODE_TABLE.get().indexOf(dir.getInode()) + 1;

        List<Entry> entryList = null;
        entryList = new DirDig(FILE, SUPER_BLOCK, INODE_TABLE, inode).getRoot().getList();

        for (Entry entry : entryList) {
            System.out.println( entry.getInumber() + "\t" + entry.getInode().getI_mode() + "\t" + entry.getFileName());
        }
    }

}
