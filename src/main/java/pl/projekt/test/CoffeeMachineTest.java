package pl.projekt.test;

import org.junit.Before;
import org.junit.Test;
import pl.projekt.Enums.CoffeeSize;
import pl.projekt.Enums.CoffeeType;
import pl.projekt.exceptions.NotEnoughMilkExeption;
import pl.projekt.exceptions.ToMuchMilkException;
import pl.projekt.exceptions.WrongUseMilkException;
import pl.projekt.model.CoffeeHistory;
import pl.projekt.model.CoffeeMachine;
import pl.projekt.service.CoffeeHistoryAnalyzer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CoffeeMachineTest {
    private CoffeeMachine coffeeMachine;
    private CoffeeHistoryAnalyzer coffeeHistoryAnalyzer;

    @Before
    public void setUp() {
        coffeeMachine = new CoffeeMachine();
        coffeeHistoryAnalyzer = new CoffeeHistoryAnalyzer();
    }

    @Test
    public void shouldReturnNextCoffeeType() {
        coffeeMachine.setCoffeeType(CoffeeType.ESPRESSO);
        coffeeMachine.nextCoffeeType();
        assertEquals(CoffeeType.CAPPUCCINO, coffeeMachine.getCoffeeType());
    }

    @Test
    public void shouldReturnPreviousCoffeeType() {
        coffeeMachine.setCoffeeType(CoffeeType.CAPPUCCINO);
        coffeeMachine.previousCoffeeType();
        assertEquals(CoffeeType.ESPRESSO, coffeeMachine.getCoffeeType());
    }
    

    @Test
    public void shouldIncreaseMilkForLatte() {
        coffeeMachine.setCoffeeType(CoffeeType.LATTE);
        coffeeMachine.setMilkLevel(100);
        coffeeMachine.increaseMilk();
        assertEquals(120, coffeeMachine.getMilkLevel());
    }

    @Test
    public void shouldIncreaseMilkForCappuccino() {
        coffeeMachine.setCoffeeType(CoffeeType.CAPPUCCINO);
        coffeeMachine.setMilkLevel(100);
        coffeeMachine.increaseMilk();
        assertEquals(120, coffeeMachine.getMilkLevel());
    }

    @Test
    public void shouldNotIncreaseMilkForOtherCoffeeTypes() {
        coffeeMachine.setCoffeeType(CoffeeType.ESPRESSO);
        coffeeMachine.setMilkLevel(100);
        assertThrows(WrongUseMilkException.class, () -> coffeeMachine.increaseMilk());
    }

    @Test
    public void shouldIncreaseMilkWithinLimit() {
        coffeeMachine.setCoffeeType(CoffeeType.LATTE);
        coffeeMachine.setMilkLevel(200); // Set initial milk level to 200ml
        assertDoesNotThrow(() -> coffeeMachine.increaseMilk()); // Increasing milk by 20ml should not throw any exception
        assertEquals(220, coffeeMachine.getMilkLevel()); // After increasing, milk level should be 220ml
    }

    @Test
    public void shouldDecreaseMilkForLatte() {
        coffeeMachine.setCoffeeType(CoffeeType.LATTE);
        coffeeMachine.setMilkLevel(200);
        coffeeMachine.decreaseMilk();
        assertEquals(180, coffeeMachine.getMilkLevel());
    }

    @Test
    public void shouldDecreaseMilkForCappuccino() {
        coffeeMachine.setCoffeeType(CoffeeType.CAPPUCCINO);
        coffeeMachine.setMilkLevel(200);
        coffeeMachine.decreaseMilk();
        assertEquals(180, coffeeMachine.getMilkLevel());
    }

    @Test
    public void shouldNotDecreaseMilkForOtherCoffeeTypes() {
        coffeeMachine.setCoffeeType(CoffeeType.ESPRESSO);
        coffeeMachine.setMilkLevel(100);
        assertThrows(WrongUseMilkException.class, () -> coffeeMachine.decreaseMilk());
    }

    @Test
    public void shouldThrowNotEnoughMilkException() {
        coffeeMachine.setCoffeeType(CoffeeType.LATTE);
        coffeeMachine.setMilkLevel(10);
        assertThrows(NotEnoughMilkExeption.class, () -> coffeeMachine.decreaseMilk());
    }
}

