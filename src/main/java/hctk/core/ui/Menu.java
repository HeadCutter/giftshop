package hctk.core.ui;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public class Menu {

    protected String title = "";
    protected String prompt = "";
    LinkedHashMap<String, MenuItem> map;

    public Menu() {
        map = new LinkedHashMap<>();
    }

    public Menu(String title) {
        this();
        this.title = title;
    }

    public Menu(String title, List<MenuItem> items) {
        this(title);
        for (MenuItem item : items) {
            addItem(item);
        }
    }

    public Menu addItem(MenuItem item) {
        map.put(item.getKey(), item);
        return this;
    }

    public Menu setTitle(String title) {
        this.title = title;
        return this;
    }

    public Menu setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    public void run(String key) {
        if (map.containsKey(key)) {
            map.get(key).run();
        } else {
            throw new IndexOutOfBoundsException("Invalid menu item key");
        }
    }

    public void display() {
        if (title.length() > 0) {
            System.out.println();
            System.out.println(title);
        }
        map.forEach((key, val) -> {
            System.out.printf("%s. %s", key, val.getLabel());
            System.out.println();
        });
    }

    public String prompt() {
        if (prompt.length() > 0) {
            System.out.print(prompt);
        }
        Scanner in = new Scanner(System.in);
        return in.next();
    }

    public void run() {
        display();
        String key = prompt();
        run(key);
    }
}
