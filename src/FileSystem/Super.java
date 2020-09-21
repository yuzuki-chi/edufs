package FileSystem;

public class Super {
    public static Dentry mountBdev(FileSystemTypeObject fsType, int flags, String devName) throws Exception {
        BlockDevice bDev;
        SuperBlock s;

        bDev = blkdevGetByPath(devName, fsType); //名前からブロックデバイスを開く
        //mutex_lock //おそらくファイルのロックをするもの
        s = fsType.getSb(fsType, flags, devName);
        //mutex_unlock //おそらくファイルのロックを解除するもの
        return dGet(s.getSroot());
    }

    private static BlockDevice blkdevGetByPath(String devName, FileSystemTypeObject fsType) {
        //TODO devNameからブロックデバイス型にして返す
        return new BlockDevice();
    }

    private static Dentry dGet(Dentry dentry) {
        //if (dentry != null) lockref_get(dentry.dLockref());
        return dentry;
    }
}
