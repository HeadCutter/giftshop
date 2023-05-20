package hctk.core.ui;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Select extends AbstractInput {

    protected List<SelectOption> options;
    public Select(String name, String label) {
        super(name, label);
        options = new ArrayList<>();
    }

    public Select addOption(int key, String value) {
        options.add(new SelectOption(key, value));
        return this;
    }
    @Override
    public Object getValue() {
        System.out.println(label + ": ");
        int num = 0;
        for(SelectOption opt: options) {
            System.out.println(++num + ". " + opt.text);
        }
        Scanner in = new Scanner(System.in);
        for(;;) {
            int val = in.nextInt();
            if(val > 0 && val <= options.size()) {
                return options.get(val-1).key;
            }
            System.out.print(label + ": ");
        }
    }
}
