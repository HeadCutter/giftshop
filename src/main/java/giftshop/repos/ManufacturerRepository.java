
package giftshop.repos;

import giftshop.entities.Manufacturer;
import hctk.core.orm.AbstractRepo;
import hctk.core.orm.AbstractRepoBackend;

public class ManufacturerRepository extends AbstractRepo<Manufacturer> {

    public ManufacturerRepository(AbstractRepoBackend backend) {
        super(backend);
    }

}