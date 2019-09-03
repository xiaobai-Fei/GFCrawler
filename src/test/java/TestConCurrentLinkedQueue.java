import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.list.SynchronizedList;
import org.apache.commons.collections.set.SynchronizedSet;
import scala.reflect.runtime.SynchronizedOps;

import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.*;

public class TestConCurrentLinkedQueue {


    private static ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
    private static ExecutorService service = Executors.newSingleThreadExecutor();

    public static void main(String[] args){
//        Collections.synchronizedSet();
        TestConCurrentLinkedQueue testConCurrentLinkedQueue = new TestConCurrentLinkedQueue();
//        testConCurrentLinkedQueue.producer(1);
//        testConCurrentLinkedQueue.consumer(10);
        TestThread testThread_1 = new TestThread("t1");
        TestThread testThread_2 = new TestThread("t2");
        service.submit(testThread_2);
        service.submit(testThread_1);
        service.shutdown();

//        queue.add(1);
//        queue.add(2);
//        System.out.println(queue);
//
//        System.out.println(queue.poll());
//        System.out.println(queue.peek());
//        System.out.println(queue.poll());
//        System.out.println(queue.size());

    }

    public void producer(Integer workerNumber){
        for(int i=0;i<workerNumber;i++){
            new Thread(){
                int startNumber = 0;

                @Override
                public void run() {
                    while(true){
                        queue.add(startNumber++);
                        try {
                            Thread.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        if (startNumber%2 ==1)
//                            queue.add(startNumber++);
//                        else
//                            queue.add(-(startNumber++));
                    }
                }
            }.start();
        }
    }

    public void consumer(Integer consumerNumber){
        for(int i=0;i<consumerNumber;i++){
            new Thread(){
                @Override
                public void run() {
                    while(true){
                        System.out.println(queue.poll());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }


}
