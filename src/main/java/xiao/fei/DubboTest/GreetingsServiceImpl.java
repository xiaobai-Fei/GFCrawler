package xiao.fei.DubboTest;

/**
 * @Author fei
 */
public class GreetingsServiceImpl implements GreetingService {

    @Override
    public String sayHello(String name) {
        return "hi, " + name;
    }
}