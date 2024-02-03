package pl.projekt.model;

import pl.projekt.Enums.CoffeeSize;
import pl.projekt.Enums.CoffeeType;

public class DeliciousCoffee extends CoffeeMachine {
    private String deliciousCoffeeType;

    public DeliciousCoffee() {
        super();
        this.deliciousCoffeeType = "Delicious Coffee Brand Feature";
    }

    public String getUniqueFeature() {
        return deliciousCoffeeType;
    }

    public void specialDeliciousBrew() {
        System.out.println("Brewing a special delicious coffee with a unique process.");
    }
}
