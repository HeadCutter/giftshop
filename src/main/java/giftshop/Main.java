package giftshop;

import giftshop.controllers.GiftController;
import giftshop.controllers.MainController;
import giftshop.controllers.ManufacturerController;
import giftshop.controllers.QueriesController;
import giftshop.entities.Country;
import giftshop.entities.Gift;
import giftshop.entities.Manufacturer;
import giftshop.repos.CountryRepository;
import giftshop.repos.GiftRepository;
import giftshop.repos.ManufacturerRepository;
import hctk.core.ioc.Container;
import hctk.core.orm.AbstractRepoBackendFactory;
import hctk.core.orm.RepoBackendFileFactory;
import hctk.core.ui.MenuController;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    private void run() {
        bind();
        MenuController menu = (MenuController) getApp().resolve("menu.main");
        menu.run();
    }

    private void bind() {
        Container app = getApp();

        app.bind("factory.repo", (a) -> {
            RepoBackendFileFactory factory = new RepoBackendFileFactory();
            return factory;
        });
        //Repos
        app.bind("repo.country", (a) -> {
            CountryRepository repo = new CountryRepository(((AbstractRepoBackendFactory) a.resolve("factory.repo")).make("countries", new Country()));
            return repo;
        }).bind("repo.manufacturer", (a) -> {
            ManufacturerRepository repo = new ManufacturerRepository(((AbstractRepoBackendFactory) a.resolve("factory.repo")).make("manufacturers", new Manufacturer()));
            return repo;
        }).bind("repo.gift", (a) -> {
            GiftRepository repo = new GiftRepository(((AbstractRepoBackendFactory) a.resolve("factory.repo")).make("gifts", new Gift()));
            return repo;
        });
        //Menus
        app.bind("menu.main", (a) -> {
            return new MainController();
        }).bind("menu.manufacturer", (a) -> {
            return new ManufacturerController(
                    (ManufacturerRepository) a.resolve("repo.manufacturer"),
                    (CountryRepository) a.resolve("repo.country")
            );
        }).bind("menu.gift", (a) -> {
            return new GiftController(
                    (GiftRepository) a.resolve("repo.gift"),
                    (ManufacturerRepository) a.resolve("repo.manufacturer")
            );
        }).bind("menu.queries", (a) -> {
            return new QueriesController(
                    (GiftRepository) a.resolve("repo.gift"),
                    (ManufacturerRepository) a.resolve("repo.manufacturer"),
                    (CountryRepository) a.resolve("repo.country")
            );
        });

    }

    protected Container getApp() {
        return Container.getInstance();
    }

}