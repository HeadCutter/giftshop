package hctk.core.ui;

import java.util.Scanner;

public class InputText extends AbstractInput {

    public InputText(String name, String label) {
        super(name, label);
    }

    @Override
    public Object getValue() {
        System.out.print(label + ": ");
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        return line;
    }

}
