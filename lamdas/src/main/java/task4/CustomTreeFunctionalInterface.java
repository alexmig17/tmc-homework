package task4;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import task4.beans.Car;
import task4.interfaces.TreeFunctional;

/**
 * class with implemented task4:
 * Task 4. (5 points) Custom Functional Interface
 * 1.	Write your own functional interface ThreeFunction (it takes three arguments and produce result).
 * 2.	Implement this with two different lambdas
 * 3.	Provide client code with usage of this lambdas
 */
public class CustomTreeFunctionalInterface {

    /**
     * main method for run task4
     *
     * @param args no custom args
     */
    public static void main(String[] args) {

        TreeFunctional<Integer, Integer, Integer, Color> rgbCreator = Color::new;

        TreeFunctional<Car.Brand, Color, Car.Size, Car> carCreator = Car::new;

        Car car = carCreator.apply(Car.Brand.BMW, rgbCreator.apply(29, 1, 100), Car.Size.LARGE);
        Car car2 = carCreator.apply(Car.Brand.LADA, rgbCreator.apply(50, 1, 90), Car.Size.SMALL);
        Car car3 = carCreator.apply(Car.Brand.BMW, rgbCreator.apply(0, 40, 100), Car.Size.LARGE);
        Car car4 = carCreator.apply(Car.Brand.MITSUBISHI, rgbCreator.apply(30, 1, 100), Car.Size.MEDIUM);
        Car car5 = carCreator.apply(Car.Brand.BMW, rgbCreator.apply(26, 17, 100), Car.Size.LARGE);
        List<Car> cars = Arrays.asList(car, car2, car3, car4, car5);
        cars.forEach(System.out::println);
    }
}
