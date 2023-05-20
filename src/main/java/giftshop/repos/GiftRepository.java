
package giftshop.repos;

import giftshop.entities.Gift;
import hctk.core.orm.AbstractRepo;
import hctk.core.orm.AbstractRepoBackend;

public class GiftRepository extends AbstractRepo<Gift> {

    public GiftRepository(AbstractRepoBackend backend) {
        super(backend);
    }

}