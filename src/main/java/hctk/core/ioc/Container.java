package hctk.core.ioc;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Container {

    Map<String, Function<Container, Object>> list;
    Map<String, Object> resolved;

    private static Container instance;

    Container() {
        list = new HashMap<>();
        resolved = new HashMap<>();
    }

    public Container bind(String label, Function<Container, Object> cb) {
        list.put(label, cb);
        return this;
    }

    public Object resolve(String label) {
        if (!resolved.containsKey(label)) {
            if (list.containsKey(label)) {
                Object res = list.get(label).apply(this);
                resolved.put(label, res);
            }
        }
        return resolved.get(label);
    }

    public static Container getInstance() {
        if (instance == null) {
            instance = new Container();
        }
        return instance;
    }

    public static Object make(String label) {
        return getInstance().resolve(label);
    }
}
