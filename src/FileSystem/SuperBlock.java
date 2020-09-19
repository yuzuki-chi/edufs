package FileSystem;

import java.io.File;

public class SuperBlock extends ControlBlock<SuperBlockEnum> {

    public SuperBlock(String source) throws Exception {
        super(new java.io.File(source));
        this.init();
    }

    public void init() {
    }

    public String getSroot() {
        return "";
    }
}
