package File;

import FileSystem.SuperBlock;

public class VfsMount {
    private SuperBlock sb;
    private String root;
    private String sroot;
    private VfsMount mnt;

    public VfsMount doKernMount(String fstype, String name, String data) {
        return null;
    }

    public void setSb(SuperBlock sb) {
        this.sb = sb;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public void setMountpoint(String sroot) {
        this.sroot = sroot;
    }

    public void setParent(VfsMount mnt){
        this.mnt = mnt;
    }
}
