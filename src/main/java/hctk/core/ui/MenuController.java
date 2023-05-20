package hctk.core.ui;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import hctk.core.ioc.Container;
import hctk.core.ui.annotations.MenuAction;

public class MenuController {

    protected String title = "";
    protected String exitLabel = "Back";
    protected boolean shouldExit = false;

    public void run() {
        for (;;) {
            getMenu().run();
            if (shouldExit) {
                shouldExit = false;
                break;
            }
        }
    }
    
    public void goTo(String controllerName) {
        MenuController menu = (MenuController)Container.make("menu." + controllerName);
        menu.run();
    }

    public Menu getMenu() {
        Method[] methods = this.getClass().getMethods();
        List<MenuItem> items = new LinkedList<>();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation an : annotations) {
                if (an instanceof MenuAction) {
                    MenuItem item = new MenuItem(((MenuAction) an).key(), ((MenuAction) an).label(), () -> {
                        try {
                            method.invoke(this);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    items.add(item);
                }
            }
        }
        Collections.sort(items, (o1, o2) -> o1.key.compareTo(o2.key));
        items.add(new MenuItem("0", exitLabel, () -> {
            shouldExit = true;
        }));
        Menu menu = new Menu(title, items);
        return menu;
    }
}
