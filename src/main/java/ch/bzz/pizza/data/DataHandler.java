package ch.bzz.pizza.data;


import ch.bzz.pizza.model.Menu;
import ch.bzz.pizza.model.Pizza;
import ch.bzz.pizza.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * data handler
 * <p>
 * M133: Pizza
 *
 * @author Naji Salih
 */
public class DataHandler {
    private static final DataHandler instance = new DataHandler();
    private static List<Menu> menuList = null;

    /**
     * default constructor: defeat instantiation
     */
    private DataHandler() {

    }

    /**
     * gets a list of all publishers with their books
     *
     * @return
     */
    public static List<Menu> getMenuList() {
        if (menuList == null) {
            menuList = new ArrayList<>();
            readJSON();
        }
        return menuList;
    }

    public static List<Pizza> getPizzaList() {
        List<Pizza> pizzaList = new ArrayList<>();

        for (Menu menu : getMenuList()) {
            for (Pizza pizza : menu.getPizzas()) {
                pizzaList.add(pizza);
            }
        }
        return pizzaList;
    }

    /**
     * find the menu by menuUUID
     *
     * @param menuUUID zhe uuid of the menu
     * @return menu-object
     */
    public static Menu findMenuByUUID(String menuUUID) {
        for (Menu menu : getMenuList()) {
            if(menu != null && menu.getMenuUUID().equals(menuUUID)){
                return menu;
            }
        }
        return null;
    }

    /**
     * gets a pizza by its uuid
     *
     * @param pizzaUUID the uuid of the pizza
     * @return pizza-object
     */
    public static Pizza findPizzaByUUID(String pizzaUUID) {
        List<Pizza> pizzaList = getPizzaList();
        for (Pizza pizza : pizzaList) {
            if (pizza != null && pizza.getPizzaUUID().equals(pizzaUUID))
                return pizza;
        }

        return null;
    }



    /**
     * reads the json-file into the menuList
     */
    private static void readJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty("menuJSON")));
            ObjectMapper objectMapper = new ObjectMapper();
            Menu[] menus = objectMapper.readValue(jsonData, Menu[].class);
            for (Menu menu : menus) {
                getMenuList().add(menu);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
