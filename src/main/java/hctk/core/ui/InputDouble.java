package hctk.core.ui;

import java.util.Scanner;

public class InputDouble extends AbstractInput {

    public InputDouble(String name, String label) {
        super(name, label);
    }

    @Override
    public Object getValue() {
        System.out.print(label + ": ");
        Scanner in = new Scanner(System.in);
        return in.nextDouble();
    }

}
