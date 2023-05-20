package hctk.core.orm;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRepo<T extends AbstractEntity> {

    protected AbstractRepoBackend backend;

    public AbstractRepo(AbstractRepoBackend backend) {
        this.backend = backend;
    }

    public T get(int id) {
        return (T) backend.get(id);
    }

    public T save(T entity) {
        return (T) backend.save(entity);
    }

    public List<T> list() {
        List<T> list = new ArrayList<>();
        backend.list((List<AbstractEntity>) list);
        return list;
    }

    public List<T> saveList(List<T> list) {
        backend.saveList((List<AbstractEntity>) list);
        return list();
    }
}
