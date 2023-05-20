package giftshop.entities;

import hctk.core.orm.AbstractEntity;
import hctk.core.orm.annotations.EntityProp;
import hctk.core.orm.annotations.EntityPropExt;

public class Manufacturer extends AbstractEntity {
    @EntityProp
    private String name;
    @EntityPropExt(repo = "country")
    private Country country;

    public Manufacturer() {

    }

    public Manufacturer(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    public String toString() {
        return name + " (" + country.getName() + ")";
    }
}
