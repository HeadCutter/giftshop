package hctk.core.ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Form {

    List<AbstractInput> inputs;

    public Form() {
        inputs = new LinkedList<>();
    }

    public Form(List<AbstractInput> list) {
        this();
        for (AbstractInput input : list) {
            inputs.add(input);
        }
    }

    public Map<String, Object> getValues() {
        Map<String, Object> map = new HashMap<>();
        for (AbstractInput input : inputs) {
            map.put(input.getName(), input.getValue());
        }
        return map;
    }

    public static InputText makeText(String name, String label) {
        return new InputText(name, label);
    }

    public static InputInt makeInt(String name, String label) {
        return new InputInt(name, label);
    }

    public static InputDouble makeDouble(String name, String label) {
        return new InputDouble(name, label);
    }

    public static Select makeSelect(String name, String label, Map<Integer, String> options) {
        Select select = new Select(name, label);
        options.forEach((id, val) -> select.addOption(id, val));
        return select;
    }
}
