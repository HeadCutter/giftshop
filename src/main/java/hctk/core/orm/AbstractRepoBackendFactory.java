package hctk.core.orm;

abstract public class AbstractRepoBackendFactory {

    abstract public AbstractRepoBackend make(String name, AbstractEntity prototype);
}
