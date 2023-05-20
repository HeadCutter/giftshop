package giftshop.controllers;


import hctk.core.ui.MenuController;
import hctk.core.ui.annotations.MenuAction;

public class MainController extends MenuController {

    public MainController() {
        title = "Main";
        exitLabel = "Exit";
    }

    @MenuAction(key = "1", label = "Manufacturers")
    public void manufacturers() {
        goTo("manufacturer");
    }

    @MenuAction(key = "2", label = "Gifts")
    public void gifts() {
        goTo("gift");
    }

    @MenuAction(key = "3", label = "Queries")
    public void queries() {
        goTo("queries");
    }

}
