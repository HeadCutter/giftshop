package giftshop.controllers;

import giftshop.entities.Gift;
import giftshop.entities.Manufacturer;
import giftshop.repos.GiftRepository;
import giftshop.repos.ManufacturerRepository;
import hctk.core.ui.Form;
import hctk.core.ui.MenuController;
import hctk.core.ui.annotations.MenuAction;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GiftController  extends MenuController {

    private GiftRepository gifts;
    private ManufacturerRepository manufacturers;

    public GiftController(GiftRepository gifts, ManufacturerRepository manufacturers) {
        this.gifts = gifts;
        this.manufacturers = manufacturers;
        title = "Gifts";
    }

    @MenuAction(key = "1", label = "List Gifts")
    public void list() {
        List<Gift> list = gifts.list();
        list.forEach(item -> System.out.println(item.getId() + ". " + item));
    }

    @MenuAction(key = "2", label = "Add Gift")
    public void add() {
        Form form = new Form(List.of(
                Form.makeText("name", "Enter Gift Name"),
                Form.makeSelect("manufacturer", "Select Manufacturer", manufacturers.list().stream().collect(Collectors.toMap(Manufacturer::getId, Manufacturer::getName))),
                Form.makeInt("year", "Enter Gift production year"),
                Form.makeDouble("price", "Enter Gift price")
        ));
        Map<String, Object> vals = form.getValues();
        Gift gift = new Gift((String) vals.get("name"), manufacturers.get((int) vals.get("manufacturer")), (int)vals.get("year"), (double)vals.get("price"));
        gifts.save(gift);
    }
}
