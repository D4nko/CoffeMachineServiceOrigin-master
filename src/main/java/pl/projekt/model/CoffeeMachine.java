package pl.projekt.model;

import lombok.Getter;
import pl.projekt.Enums.CoffeeSize;
import pl.projekt.Enums.CoffeeType;
import pl.projekt.exceptions.*;
import pl.projekt.interfaces.Analyzable;
import pl.projekt.model.Milk;
import pl.projekt.model.Water;
import pl.projekt.service.CoffeeHistoryAnalyzer;

import java.util.List;

public class CoffeeMachine implements Analyzable {
    @Getter
    private CoffeeType coffeeType;
    @Getter
    private CoffeeSize coffeeSize;
    private Water water;
    private Milk milk;
    private List<CoffeeHistory> coffeeHistory;




    public CoffeeMachine() {
        this.water = new Water();
        this.milk = new Milk();
    }



    public void setCoffeeType(CoffeeType type) {
        this.coffeeType = type;
        updateMilkAmount();
    }

    public void nextCoffeeType() {
        coffeeType = CoffeeType.next(coffeeType);
        updateMilkAmount();
    }

    public void previousCoffeeType() {
        coffeeType = CoffeeType.previous(coffeeType);
        updateMilkAmount();
    }

    public void setCoffeeSize(CoffeeSize size) {
        this.coffeeSize = size;
    }

    public void setWaterLevel(double waterLevel) {
        water.setWaterLevel(waterLevel);
    }

    public double getWaterLevel() {
        return water.getWaterLevel();
    }

    public void addWater(double waterAmount) {
        water.addWater(waterAmount);
    }

    public void dispenseWater(double waterAmount) {
        water.dispenseWater(waterAmount);
    }

    public void setMilkLevel(int milkLevel) {
        milk.setMilkLevel(milkLevel);
    }

    public int getMilkLevel() {
        return milk.getMilkLevel();
    }

    public void addMilk(int milkAmount) {
        milk.addMilk(milkAmount);
    }

    public void dispenseMilk(int milkAmount) {
        milk.dispenseMilk(milkAmount);
    }

    public void increaseMilk() {
        if (coffeeType == CoffeeType.LATTE || coffeeType == CoffeeType.CAPPUCCINO) {
            if (milk.getMilkLevel() + 20 <= 400) {
                milk.addMilk(20);
            } else {
                throw new ToMuchMilkException(" Milk amount exceeds the maximum allowed for this coffee type");
            }
        } else {
            throw new WrongUseMilkException(" Milk can only be increased for Latte or Cappuccino ");
        }
    }

    public void decreaseMilk() {
        if (coffeeType == CoffeeType.LATTE || coffeeType == CoffeeType.CAPPUCCINO) {
            if (milk.getMilkLevel() - 20 >= 0) {
                milk.dispenseMilk(20);
            } else {
                throw new NotEnoughMilkExeption(" Milk amount is already at the minimum allowed for this coffee type");
            }
        } else {
            throw new WrongUseMilkException("Milk can only be decreased for Latte or Cappuccino");
        }
    }

    public int getMilkAmount() {
        return milk.getMilkLevel();
    }

    public void makeCoffee(CoffeeType coffeeType, CoffeeSize coffeeSize, double waterAmount, int milkAmount) {
        prepareCoffee(coffeeType, coffeeSize, waterAmount, milkAmount);

        double waterNeeded = coffeeSize.getId() * 0.01; // 1 = small, 2 = medium, 3 = large
        int milkNeeded = getMilkAmount();

        // Sprawdzenie braków wody i mleka, i uzupełnienie ich w razie potrzeby
        if (waterNeeded > getWaterLevel()) {
            throw new LackOfMilkOrWaterException("Not enough water to make coffee. Adding water...");
        }

        if (milkNeeded > getMilkLevel()) {
            throw new LackOfMilkOrWaterException("Not enough water to make coffee. Adding water...");
        }

        // Ponowne pobranie aktualnej ilości wody i mleka po ewentualnym uzupełnieniu
        waterNeeded = coffeeSize.getId() * 0.01;
        milkNeeded = getMilkAmount();

        // Sprawdzenie, czy teraz jest wystarczająco wody i mleka
        if (waterNeeded > getWaterLevel()) {
            throw new NotEnoughWaterException("Still not enough water to make coffee.");
        }

        if (milkNeeded > getMilkLevel()) {
            throw new NotEnoughMilkExeption("Still not enough milk to make coffee");
        }

        dispenseWater(waterAmount);
        dispenseMilk(milkAmount);

        water.dispenseWater(waterAmount);
        milk.dispenseMilk(milkAmount);

        System.out.println("🖨 Twoja kawa \"" + coffeeSize.getName() + " " + coffeeType.getName() +
                "\" jest już zrobiona! Wykorzystano: " + waterAmount + "l wody oraz " + milkAmount + "ml mleka.");
    }

    private void prepareCoffee(CoffeeType coffeeType, CoffeeSize coffeeSize, double waterLevel, int milkLevel) {
        setCoffeeType(coffeeType);
        setCoffeeSize(coffeeSize);
        setWaterLevel(waterLevel);
        setMilkLevel(milkLevel);
    }

    private void updateMilkAmount() {
        switch (coffeeType) {
            case LATTE:
            case CAPPUCCINO:
                milk.setMilkLevel(200);
                break;
            default:
                milk.setMilkLevel(0);
        }
    }
    public void showStatus() {
        System.out.println(" Stan ekspresu");
        System.out.println("————————————");
        System.out.println("Rodzaj kawy: " + coffeeType.getName());
        System.out.println("Wielkość kawy: " + coffeeSize.getName());
        System.out.println("Ilość wody w kawie: " + coffeeSize.getId() * 0.01 + "l");
        System.out.println("Ilość mleka w kawie: " + getMilkAmount() + "ml");
        System.out.println("Ilość wody w zbiorniku: " + getWaterLevel() + "l");
        System.out.println("Ilość mleka w zbiorniku: " + getMilkLevel() + "ml");
    }

    @Override
    public void analyze() {
        CoffeeHistoryAnalyzer.analyze(coffeeHistory);

    }
}
