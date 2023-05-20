package hctk.core.ui;

public class MenuItem {

    protected String label;
    protected String key;
    protected Runnable action;

    public MenuItem(String key, String label, Runnable action) {
        this.key = key;
        this.label = label;
        this.action = action;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Runnable getAction() {
        return action;
    }

    public void setAction(Runnable action) {
        this.action = action;
    }
    
    public void run() {
        action.run();
    }

}
