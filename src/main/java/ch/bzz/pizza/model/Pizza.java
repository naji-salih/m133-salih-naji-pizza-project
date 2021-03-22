package ch.bzz.pizza.model;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;

/**
 * data object Pizza
 * <p>
 * M133
 *
 * @author Naji Salih
 */
public class Pizza {
    private String pizzaUUID;
    @FormParam("name")
    @NotEmpty
    @Size(min=2, max=40)
    private String name;
    @FormParam("preis")
    @DecimalMax(value="99.95")
    @DecimalMin(value="0.05")
    private double preis;
    @FormParam("durchmesser")
    @Max(value=99)
    @Min(value=5)
    private int durchmesser;
    private Menu menu;

    public Pizza(){

    }

    public String getPizzaUUID() {
        return pizzaUUID;
    }

    public void setPizzaUUID(String pizzaUUID) {
        this.pizzaUUID = pizzaUUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public int getDurchmesser() {
        return durchmesser;
    }

    public void setDurchmesser(int durchmesser) {
        this.durchmesser = durchmesser;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
