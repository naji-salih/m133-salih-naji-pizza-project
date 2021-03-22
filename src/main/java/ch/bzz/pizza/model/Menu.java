package ch.bzz.pizza.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.time.LocalDate;
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
    @FormParam("veroeffentlichung")
    private LocalDate veroeffentlichung;
    @FormParam("name")
    private String name;

    public String getMenuUUID() {
        return menuUUID;
    }

    public void setMenuUUID(String menuUUID) {
        this.menuUUID = menuUUID;
    }

    public LocalDate getVeroeffentlichung() {
        return veroeffentlichung;
    }

    public void setVeroeffentlichung(LocalDate veroeffentlichung) {
        this.veroeffentlichung = veroeffentlichung;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
