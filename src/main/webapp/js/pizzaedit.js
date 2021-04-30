/**
 * view-controller for pizzaedit.html
 * @author  Naji Salih
 */

/**
 * register listeners and load the pizza data
 */
$(document).ready(function () {
    loadMenus();
    loadPizza();

    /**
     * listener for submitting the form
     */
    $("#pizzaeditForm").submit(savePizza);

    /**
     * listener for button [abbrechen], redirects to menu
     */
    $("#cancel").click(function () {
        window.location.href = "./menu.html";
    });


});

/**
 *  loads the data of this pizza
 *
 */
function loadPizza() {
    let pizzaUUID = $.urlParam("uuid");
    if (pizzaUUID) {
        $
            .ajax({
                url: "./resource/pizza/read?uuid=" + pizzaUUID,
                dataType: "json",
                type: "GET"
            })
            .done(showPizza)
            .fail(function (xhr, status, errorThrown) {
                if (xhr.status == 403) {
                    window.location.href = "./login.html";
                } else if (xhr.status == 404) {
                    $("#message").text("Keine Pizza gefunden");
                } else {
                    window.location.href = "./menu.html";
                }
            })
    }

}

/**
 * shows the data of this pizza
 * @param  pizza  the pizza data to be shown
 */
function showPizza(pizza) {
    $("#pizzaUUID").val(pizza.pizzaUUID);
    $("#name").val(pizza.name);
    $("#preis").val(pizza.preis);
    $("#menu").val(pizza.menu.menuUUID);
    $("#durchmesser").val(pizza.durchmesser);

}

/**
 * sends the pizza data to the webservice
 * @param form the form being submitted
 */
function savePizza(form) {
    form.preventDefault();

    let url = "./resource/pizza/";
    let type;

    let pizzaUUID = $("#pizzaUUID").val();
    if (pizzaUUID) {
        type= "PUT";
        url += "update"
    } else {
        type = "POST";
        url += "create";
    }

    $
        .ajax({
            url: url,
            dataType: "text",
            type: type,
            data: $("#pizzaeditForm").serialize()
        })
        .done(function (jsonData) {
            window.location.href = "./menu.html"
        })
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
                $("#message").text("Diese Pizza existiert nicht");
            } else {
                $("#message").text("Fehler beim Speichern der Pizza");
            }
        })
}

function loadMenus() {
    $
        .ajax({
            url: "./resource/menu/list",
            dataType: "json",
            type: "GET"
        })
        .done(showMenus)
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
                $("#message").text("Kein Menu gefunden");
            } else {
                window.location.href = "./menu.html";
            }
        })
}

function showMenus(menus) {

    $.each(menus, function (uuid, menu) {
        $('#menu').append($('<option>', {
            value: menu.menuUUID,
            text: menu.name
        }));
    });
}