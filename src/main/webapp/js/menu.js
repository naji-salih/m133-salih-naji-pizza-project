
/**
 * view-controller for menu.html
 * @author  Naji Salih
 */

/**
 * register listeners and load all pizzas
 */
$(document).ready(function () {
    loadPizzas();

    /**
     * listener for buttons within menu
     */
    $("#menuForm").on("click", "button", function () {
        if (confirm("Wollen Sie diese Pizza wirklich löschen?")) {
            deletePizza(this.value);
        }
    });



});

function loadPizzas() {
    $
        .ajax({
            url: "./resource/pizza/list",
            dataType: "json",
            type: "GET"
        })
        .done(showPizzas)
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 403) {
                window.location.href = "./login.html";
            } else if (xhr.status == 404) {
                $("#message").text("keine Pizzas vorhanden");
            }else {
                $("#message").text("Fehler beim Lesen der Pizzas");
            }
        })

}

/**
 * shows all pizzas as a table
 *
 * @param pizzaData all pizzas as an array
 */
function showPizzas(pizzaData) {

    let table = document.getElementById("pizzas");
    clearTable(table);

    $.each(pizzaData, function (uuid, pizza) {
        if (pizza.name) {
            let row = table.insertRow(-1);
            let cell = row.insertCell(-1);
            cell.innerHTML = pizza.name;

            cell = row.insertCell(-1);
            cell.innerHTML = pizza.preis;

            cell = row.insertCell(-1);
            cell.innerHTML = pizza.menu.name;

            cell = row.insertCell(-1);
            cell.innerHTML = pizza.durchmesser;

            cell = row.insertCell(-1);
            cell.innerHTML = "<a href='./pizzaedit.html?uuid=" + uuid + "'>Bearbeiten</a>";

            cell = row.insertCell(-1);
            cell.innerHTML = "<button type='button' id='delete_" + uuid + "' value='" + uuid + "'>Löschen</button>";


        }
    });
}

function clearTable(table) {
    while (table.hasChildNodes()) {
        table.removeChild(table.firstChild);
    }
}

/**
 * send delete request for a pizza
 * @param pizzaUUID
 */
function deletePizza(pizzaUUID) {
    $
        .ajax({
            url: "./resource/pizza/delete?pizzaUUID=" + pizzaUUID,
            dataType: "text",
            type: "DELETE",
        })
        .done(function (data) {
            loadPizzas();
            $("#message").text("Pizza gelöscht");

        })
        .fail(function (xhr, status, errorThrown) {
            $("#message").text("Fehler beim Löschen der Pizza");
        })
}