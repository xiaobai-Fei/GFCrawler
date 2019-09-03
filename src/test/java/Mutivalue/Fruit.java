package Mutivalue;

public class Fruit extends AFruit {

    @Override
    public String getName() {
        return "I am Fruit class";
    }

    @Override
    public String getFruitType() {
        return "banana";
    }

    public static void main(String[] args){
        Fruit fruit = new Fruit();
        System.out.println(fruit.getFruitName());
    }
}
