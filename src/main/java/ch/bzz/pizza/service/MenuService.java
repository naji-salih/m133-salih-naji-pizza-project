package ch.bzz.pizza.service;

import ch.bzz.pizza.data.DataHandler;
import ch.bzz.pizza.model.Menu;
import ch.bzz.pizza.model.Pizza;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
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
        List<Menu> menuList = DataHandler.getMenuList();
        Response response = Response
                .status(200)
                .entity(menuList)
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
            menu = DataHandler.findMenuByUUID(menuUUID);
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
}
