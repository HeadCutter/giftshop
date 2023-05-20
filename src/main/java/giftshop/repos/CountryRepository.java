package giftshop.repos;

import giftshop.entities.Country;
import hctk.core.orm.AbstractRepo;
import hctk.core.orm.AbstractRepoBackend;

public class CountryRepository extends AbstractRepo<Country> {

    public CountryRepository(AbstractRepoBackend backend) {
        super(backend);
    }

}