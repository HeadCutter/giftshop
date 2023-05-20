package hctk.core.orm;

/**
 *
 * @author Headcutter
 */
public class RepoBackendFileFactory extends AbstractRepoBackendFactory {

    @Override
    public AbstractRepoBackend make(String name, AbstractEntity prototype) {
        RepoBackendFile back = new RepoBackendFile(name, prototype);
        back.init();
        return back;
    }

}
