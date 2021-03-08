package ch.bzz.pizza.model;

import java.util.Date;
import java.util.List;

/**
 * data object Menu
 * <p>
 * M133
 *
 * @author Naji Salih
 */
public class Menu {
    private String menuUUID;
    private Date veroeffentlichung;
    private String name;
    private List<Pizza> pizzas;

    public String getMenuUUID() {
        return menuUUID;
    }

    public void setMenuUUID(String menuUUID) {
        this.menuUUID = menuUUID;
    }

    public Date getVeroeffentlichung() {
        return veroeffentlichung;
    }

    public void setVeroeffentlichung(Date veroeffentlichung) {
        this.veroeffentlichung = veroeffentlichung;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }
}
