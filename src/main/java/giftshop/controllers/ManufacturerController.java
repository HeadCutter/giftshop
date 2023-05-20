package giftshop.controllers;

import giftshop.entities.Country;
import giftshop.entities.Manufacturer;
import giftshop.repos.CountryRepository;
import giftshop.repos.ManufacturerRepository;
import hctk.core.ui.Form;
import hctk.core.ui.MenuController;
import hctk.core.ui.annotations.MenuAction;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ManufacturerController extends MenuController {

    private ManufacturerRepository repo;
    private CountryRepository countries;
    public ManufacturerController(ManufacturerRepository repo, CountryRepository countries) {
        this.repo = repo;
        this.countries = countries;
        title = "Manufacturers";
    }

    @MenuAction(key = "1", label = "List Manufacturers")
    public void list() {
        List<Manufacturer> list = repo.list();
        list.forEach(item -> System.out.println(item.getId() + ". " + item.getName()+ " (" + item.getCountry().getName() + ")"));
    }

    @MenuAction(key = "2", label = "Add Manufacturer")
    public void add() {
        Form form = new Form(List.of(
                Form.makeText("name", "Enter Manufacturer Name"),
                Form.makeSelect("country", "Select Country", countries.list().stream().collect(Collectors.toMap(Country::getId, Country::getName)))
        ));
        Map<String, Object> vals = form.getValues();
        Manufacturer mnf = new Manufacturer((String) vals.get("name"), countries.get((int) vals.get("country")));
        repo.save(mnf);
    }
}
