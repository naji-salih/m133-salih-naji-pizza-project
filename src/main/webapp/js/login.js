/**
 * view-controller für login.html
 */

/**
 * register listeners
 */
$(document).ready(function () {

    /**
     * listener für login button
     */
    $("#loginForm").submit(sendLogin);

    /**
     * listener für abmelden button
     */
    $("#logout").click(sendLogout);

});

/**
 * login-request
 * @param form with username oder password
 */
function sendLogin(form) {
    form.preventDefault();
    $
        .ajax({
            url: "./resource/user/login",
            dataType: "text",
            type: "POST",
            data: $("#loginForm").serialize()
        })
        .done(function () {
            window.location.href = "./menu.html";
        })
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
                $("#message").text("Benutzernamen oder Passwort ist unbekannt");
            } else {
                $("#message").text("Achtung: Es ist ein Fehler aufgetreten!");
            }
        })

}

/**
 * logout request
 */
function sendLogout() {
    $
        .ajax({
            url: "./resource/user/logout",
            dataType: "text",
            type: "DELETE"
        })
        .done(function () {
            window.location.href = "./login.html";
        })
        .fail(function (xhr, status, errorThrown) {

            $("#message").text("Achtung: Es ist ein Fehler aufgetreten!");

        })
}