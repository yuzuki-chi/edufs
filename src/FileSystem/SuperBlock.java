package FileSystem;

import java.io.File;

public class SuperBlock extends ControlBlock<SuperBlockEnum> {
    Dentry droot;

    public SuperBlock(String source) throws Exception {
        super(new java.io.File(source));
        this.init();
    }

    public void init() {
    }

    public Dentry getSroot() {
        return droot;
    }
}
