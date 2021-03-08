package ch.bzz.pizza.model;

/**
 * data object Pizza
 * <p>
 * M133
 *
 * @author Naji Salih
 */
public class Pizza {
    private String pizzaUUID;
    private String name;
    private double preis;
    private int durchmesser;

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
}
