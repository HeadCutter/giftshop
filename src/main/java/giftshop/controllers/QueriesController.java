package giftshop.controllers;

import giftshop.entities.Country;
import giftshop.entities.Gift;
import giftshop.entities.Manufacturer;
import giftshop.repos.CountryRepository;
import giftshop.repos.GiftRepository;
import giftshop.repos.ManufacturerRepository;
import hctk.core.ui.Form;
import hctk.core.ui.MenuController;
import hctk.core.ui.annotations.MenuAction;

import java.util.List;
import java.util.stream.Collectors;

public class QueriesController extends MenuController {

    private GiftRepository gifts;
    private ManufacturerRepository manufacturers;
    private CountryRepository countries;

    public QueriesController(GiftRepository gifts, ManufacturerRepository manufacturers, CountryRepository countries) {
        this.gifts = gifts;
        this.manufacturers = manufacturers;
        this.countries = countries;
        title = "Gift and Manufacturer queries";
    }

    @MenuAction(key = "1", label = "List Gifts By Manufacturer")
    public void listByManufacturer() {
        int mfId = (int)(Form.makeSelect("id", "Select Manufacturer", manufacturers.list().stream().collect(Collectors.toMap(Manufacturer::getId, Manufacturer::getName))).getValue());
        gifts.list().stream().filter(gift -> gift.getManufacturer().getId() == mfId).forEach(System.out::println);
    }
    @MenuAction(key = "2", label = "List Gifts By Country")
    public void listByCountry() {
        int cId = (int)(Form.makeSelect("id", "Select Country", countries.list().stream().collect(Collectors.toMap(Country::getId, Country::getName))).getValue());
        gifts.list().stream().filter(gift -> gift.getManufacturer().getCountry().getId() == cId).forEach(System.out::println);
    }
    @MenuAction(key = "3", label = "List Low Price Manufacturers")
    public void listLowPriceManufacturers() {
        double price =  (double)(Form.makeDouble("price", "Enter Highest Price").getValue());
        gifts.list().stream().filter(gift -> gift.getPrice() <= price).map(Gift::getManufacturer).distinct().forEach(System.out::println);
    }

    @MenuAction(key = "4", label = "List Manufacturers and Gifts")
    public void listManufacturersAndGifts() {
        manufacturers.list().stream().forEach(manufacturer -> {
            System.out.println(manufacturer);
            gifts.list().stream().filter(gift -> gift.getManufacturer().getId() == manufacturer.getId()).forEach(gift -> System.out.println("\t"+gift));
        });
    }

    @MenuAction(key = "5", label = "List Manufacturers by Gift Production Year")
    public void listManufacturersByGiftYear() {
        int year =  (int)(Form.makeInt("year", "Enter Year of Gift Production").getValue());
        gifts.list().stream().filter(gift -> gift.getYear() == year).map(Gift::getManufacturer).distinct().forEach(System.out::println);
    }

    @MenuAction(key = "6", label = "List Gift by Year")
    public void listGiftsByYear() {
        gifts.list().stream().mapToInt(Gift::getYear).distinct().sorted().forEach(year ->  {
            System.out.println("Year " + year);
            gifts.list().stream().filter(gift -> gift.getYear() == year).forEach(gift -> System.out.println("\t"+gift));
        });
    }

    @MenuAction(key = "7", label = "Remove Manufacturer and Gifts")
    public void removeManufacturer() {
        int mfId = (int)(Form.makeSelect("id", "Select Manufacturer", manufacturers.list().stream().collect(Collectors.toMap(Manufacturer::getId, Manufacturer::getName))).getValue());
        List<Gift> fgifts = gifts.list().stream().filter(gift -> gift.getManufacturer().getId() != mfId).toList();
        List<Manufacturer> fman = manufacturers.list().stream().filter(manufacturer -> manufacturer.getId() != mfId).toList();
        gifts.saveList(fgifts);
        manufacturers.saveList(fman);
    }
}
