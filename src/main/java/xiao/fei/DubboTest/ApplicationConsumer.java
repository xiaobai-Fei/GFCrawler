package xiao.fei.DubboTest;


import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
/**
 * @Author fei
 */
public class ApplicationConsumer {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "192.168.134.128");

    public static void main(String[] args) {
        ReferenceConfig<GreetingService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        reference.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        reference.setInterface(GreetingService.class);
        GreetingService service = reference.get();
        String message = service.sayHello("dubbo");
        for(;;)
            System.out.println(message);
    }
}