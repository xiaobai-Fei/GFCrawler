public class TestThread extends Thread {


    public TestThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println(getName()+" is running");
    }
}
