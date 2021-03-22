package ch.bzz.pizza.service;

import ch.bzz.pizza.data.DataHandler;
import ch.bzz.pizza.model.Menu;
import ch.bzz.pizza.model.Pizza;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Path("menu")
public class MenuService {
    /**
     * produces a list of all books
     *
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public Response listMenu(
    ) {
        Map<String, Menu> menuMap = DataHandler.getMenuMap();
        Response response = Response
                .status(200)
                .entity(menuMap)
                .build();
        return response;
    }

    /**
     * reads a single menu identified by the menuUUID
     *
     * @param menuUUID the menuUUID in the URL
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

    public Response readMenu(
            @QueryParam("uuid") String menuUUID
    ) {
        Menu menu = null;
        int httpStatus;

        try {
            UUID menuKey = UUID.fromString(menuUUID);
            menu = DataHandler.readMenu(menuUUID);
            if (menu != null) {
                httpStatus = 200;
            } else {
                httpStatus = 404;
            }
        } catch (IllegalArgumentException argEx) {
            httpStatus = 400;
        }

        Response response = Response
                .status(httpStatus)
                .entity(menu)
                .build();
        return response;
    }

    /**
     * creates a new menu without pizza
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createMenu(
            @Valid @BeanParam Menu menu
    ) {
        int httpStatus = 200;
        menu.setMenuUUID(UUID.randomUUID().toString());
        DataHandler.insertMenu(menu);

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * updates the menu in all it's pizzas
     * @param menuUUID  the uuid of the menu
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateMenu(
            @Valid @BeanParam Menu menu,
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("menuUUID") String menuUUID
    ) {
        int httpStatus = 200;
        try {
            UUID.fromString(menuUUID);
            menu.setMenuUUID(menuUUID);
            if (DataHandler.updateMenu(menu)) {
                httpStatus = 200;
            } else {
                httpStatus = 404;
            }
        } catch (IllegalArgumentException argEx) {
            httpStatus = 400;
        }
        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteMenu(
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("menuUUID") String menuUUID
    ) {
        int httpStatus;
        try {
            UUID.fromString(menuUUID);
            boolean success = DataHandler.deleteMenu(menuUUID);
            if (success) httpStatus = 200;
            else httpStatus = 404;
        } catch (IllegalArgumentException argEx) {
            httpStatus = 400;
        }
        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }
}
