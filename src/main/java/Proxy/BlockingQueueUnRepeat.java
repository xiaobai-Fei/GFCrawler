package Proxy;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueUnRepeat<E> extends LinkedBlockingQueue<E> {
    @Override
    public boolean add(E o) {
        if (this.contains(o)){
            return false;
        }
        return super.add(o);
    }
}
