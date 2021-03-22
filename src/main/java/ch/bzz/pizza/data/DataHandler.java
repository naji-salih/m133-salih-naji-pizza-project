package ch.bzz.pizza.data;


import ch.bzz.pizza.model.Menu;
import ch.bzz.pizza.model.Pizza;
import ch.bzz.pizza.service.Config;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * data handler
 * <p>
 * M133: Pizza
 *
 * @author Naji Salih
 */
public class DataHandler {
    private static final DataHandler instance = new DataHandler();
    private static Map<String, Pizza> pizzaMap;
    private static Map<String, Menu> menuMap;

    /**
     * default constructor: defeat instantiation
     */
    private DataHandler() {
        pizzaMap = new HashMap<>();
        menuMap = new HashMap<>();
        readJSON();
    }

    /**
     * find the menu by menuUUID
     *
     * @param menuUUID zhe uuid of the menu
     * @return menu-object
     */
    public static Menu readMenu(String menuUUID) {
        Menu menu = null;
        if (getMenuMap().containsKey(menuUUID)) {
            menu = getMenuMap().get(menuUUID);
        }
        return menu;
    }

    /**
     * gets a pizza by its uuid
     *
     * @param pizzaUUID the uuid of the pizza
     * @return pizza-object
     */
    public static Pizza readPizza(String pizzaUUID) {
        Pizza pizza = null;
        if (getPizzaMap().containsKey(pizzaUUID)) {
            pizza = getPizzaMap().get(pizzaUUID);
        }
        return pizza;
    }

    public static void insertPizza(Pizza pizza) {
        pizza.setPizzaUUID(UUID.randomUUID().toString());
        getPizzaMap().put(pizza.getPizzaUUID(), pizza);
        writeJSON();
    }

    public static boolean updatePizza(Pizza pizza, String pizzaUUID) {
        if(getPizzaMap().containsKey(pizzaUUID)) {
            getPizzaMap().put(pizza.getPizzaUUID(), pizza);
            writeJSON();
            return true;
        } else {
            return false;
        }
    }

    public static boolean deletePizza(String pizzaUUID) {
        if (getPizzaMap().remove(pizzaUUID) != null) {
            writeJSON();
            return true;
        } else {
            return false;
        }
    }

    public static void insertMenu(Menu menu) {
        Pizza pizza = new Pizza();
        pizza.setMenu(menu);
        insertPizza(pizza);
    }

    public static boolean updateMenu(Menu menu) {
        boolean found = false;
        for (Map.Entry<String, Pizza> entry : getPizzaMap().entrySet()) {
            Pizza pizza = entry.getValue();
            if (pizza.getMenu()!= null && pizza.getMenu().getMenuUUID().equals(menu.getMenuUUID())) {
                pizza.setMenu(menu);
                found = true;
            }
        }
        writeJSON();
        return found;
    }

    public static boolean deleteMenu(String menuUUID) {
        boolean found = false;
        for (Map.Entry<String, Pizza> entry : getPizzaMap().entrySet()) {
            Pizza pizza = entry.getValue();
            if (pizza.getMenu()!= null && pizza.getMenu().getMenuUUID().equals(menuUUID)) {
                pizza.setMenu(null);
                updatePizza(pizza, pizza.getPizzaUUID());
                found = true;
            }
        }
        return found;
    }

    /**
     * gets the pizzaMap
     *
     * @return the pizzaMap
     */
    public static Map<String, Pizza> getPizzaMap() {
        return pizzaMap;
    }

    /**
     * gets the menuMap
     *
     * @return the menuMap
     */
    public static Map<String, Menu> getMenuMap() {
        return menuMap;
    }

    /**
     * reads the json-file
     */
    private static void readJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty("pizzaJSON")));
            ObjectMapper objectMapper = new ObjectMapper();
            Pizza[] pizzas = objectMapper.readValue(jsonData, Pizza[].class);
            for (Pizza pizza : pizzas) {
                if(pizza.getMenu() != null) {
                    String menuUUID = pizza.getMenu().getMenuUUID();
                    Menu menu;
                    if (getMenuMap().containsKey(menuUUID)) {
                        menu = getMenuMap().get(menuUUID);
                    } else {
                        menu = pizza.getMenu();
                        getMenuMap().put(menuUUID, menu);
                    }
                    pizza.setMenu(menu);
                }
                getPizzaMap().put(pizza.getPizzaUUID(), pizza);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write in JSON
     */
    private static void writeJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String pizzaPath = Config.getProperty("pizzaJSON");
        try {
            fileOutputStream = new FileOutputStream(pizzaPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getPizzaMap().values());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
