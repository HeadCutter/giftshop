package giftshop.entities;

import hctk.core.orm.AbstractEntity;
import hctk.core.orm.annotations.EntityProp;

public class Country extends AbstractEntity {
    @EntityProp
    private String name;

    public Country() {

    }

    public String getName() {
        return name;
    }
}
