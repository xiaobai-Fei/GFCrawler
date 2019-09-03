package Mutivalue;

public  class AFruit implements  IFruit {

    public String getFruitName(){
        return  getName()+":"+getFruitType();
    }

    public String getFruitType(){
        return  "apple";
    }
    @Override
    public String getName() {
        return "I am AFruit class";
    }
}
