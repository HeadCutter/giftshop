package hctk.core.orm;

import java.util.List;

public abstract class AbstractRepoBackend {

    protected String name;
    protected AbstractEntity prototype;

    public AbstractRepoBackend(String name, AbstractEntity prototype) {
        this.name = name;
        this.prototype = prototype;
    }

    abstract public void init();

    abstract public AbstractEntity get(int id);

    abstract public AbstractEntity save(AbstractEntity entity);
    abstract public void saveList(List<AbstractEntity> list);

    abstract public void list(List<AbstractEntity> list);
}
