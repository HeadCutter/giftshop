package hctk.core.ui;

import java.util.Scanner;

public class InputInt extends AbstractInput {

    public InputInt(String name, String label) {
        super(name, label);
    }

    @Override
    public Object getValue() {
        System.out.print(label + ": ");
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }

}
