package giftshop.entities;

import hctk.core.orm.AbstractEntity;
import hctk.core.orm.annotations.EntityProp;
import hctk.core.orm.annotations.EntityPropExt;

public class Gift extends AbstractEntity {
    @EntityProp
    private String name;
    @EntityProp
    private int year;
    @EntityProp
    private double price;
    @EntityPropExt(repo = "manufacturer")
    private Manufacturer manufacturer;

    public Gift() {

    }

    public Gift(String name, Manufacturer manufacturer, int year, double price) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.year = year;
        this.price = price;
    }

    public Gift setYear(int year) {
        this.year = year;
        return this;
    }

    public Gift setName(String name) {
        this.name = name;
        return this;
    }

    public Gift setPrice(double price) {
        this.price = price;
        return this;
    }

    public Gift setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public int getYear() {
        return year;
    }

    public String getName() {
        return name;
    }

    public String getCountryName() {
        return manufacturer.getCountry().getName();
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public String toString() {
        return name + " (" + manufacturer.getName() + ", " + getCountryName() + "), " + year + ", $" + price;
    }

}
