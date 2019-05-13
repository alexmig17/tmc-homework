package task4.beans;

import java.awt.*;
import java.util.Objects;

/**
 * Car
 */
public class Car {

    private Brand brand;
    private Color color;
    private Size size;

    public Car(Brand brand, Color color, Size size) {
        this.brand = brand;
        this.color = color;
        this.size = size;
    }

    private Brand getBrand() {
        return brand;
    }

    private void setBrand(Brand brand) {
        this.brand = brand;
    }

    private Color getColor() {
        return color;
    }

    private void setColor(Color color) {
        this.color = color;
    }

    private Size getSize() {
        return size;
    }

    private void setSize(Size size) {
        this.size = size;
    }

    @Override
    public int hashCode() {

        return Objects.hash(brand, color, size);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return brand == car.brand &&
                Objects.equals(color, car.color) &&
                size == car.size;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand=" + brand +
                ", color=" + color +
                ", size=" + size +
                '}';
    }

    public enum Brand {
        BMW, MITSUBISHI, LADA
    }

    public enum Size {
        SMALL, MEDIUM, LARGE
    }
}
