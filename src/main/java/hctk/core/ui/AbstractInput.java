package hctk.core.ui;

abstract public class AbstractInput {

    protected String name;
    protected String label;

    public AbstractInput(String name, String label) {
        this.name = name;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    abstract public Object getValue();
}
