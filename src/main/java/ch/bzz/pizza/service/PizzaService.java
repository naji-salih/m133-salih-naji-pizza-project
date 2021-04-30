package ch.bzz.pizza.service;

import ch.bzz.pizza.data.DataHandler;
import ch.bzz.pizza.model.Menu;
import ch.bzz.pizza.model.Pizza;


import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

@Path("pizza")
public class PizzaService {
    /**
     * produces a list of all books
     *
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)


    public Response listPizza(
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        Map<String, Pizza> pizzapizzaMap = null;
        if(!userRole.equals("admin") && !userRole.equals("restaurantManager") && !userRole.equals("chief")){
            httpStatus = 403;
        } else {
            httpStatus = 200;
            pizzapizzaMap = DataHandler.getPizzaMap();
        }
        Response response = Response
                .status(httpStatus)
                .entity(pizzapizzaMap)
                .build();
        return response;
    }


    /**
     * reads a single pizza identified by the pizzaUUID
     *
     * @param pizzaUUID the pizzaUUID in the URL
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

    public Response readPizza(
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String pizzaUUID,
            @CookieParam("userRole") String userRole

    ) {
        Pizza pizza = null;
        int httpStatus;
        if(!userRole.equals("admin") && !userRole.equals("restaurantManager") && !userRole.equals("chief")){
            httpStatus = 403;
        } else {
            try {
                UUID pizzyKey = UUID.fromString(pizzaUUID);
                pizza = DataHandler.readPizza(pizzaUUID);
                if (pizza != null) {
                    httpStatus = 200;
                } else {
                    httpStatus = 404;
                }
            } catch (IllegalArgumentException argEx) {
                httpStatus = 400;
            }
        }
        Response response = Response
                .status(httpStatus)
                .entity(pizza)
                .build();
        return response;
    }

    /**
     * new Pizza a valid Pizza-Object
     * @param menuUUID
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createPizza(
           @Valid @BeanParam Pizza pizza,
           @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
           @FormParam("menuUUID") String menuUUID,
           @CookieParam("userRole") String userRole
    ) {
       int httpStatus;
        if(!userRole.equals("admin") && !userRole.equals("restaurantManager") && !userRole.equals("chief")){
            httpStatus = 403;
        } else {
            httpStatus = 200;
            Menu menu = DataHandler.getMenuMap().get(menuUUID);
            pizza.setMenu(menu);
            DataHandler.insertPizza(pizza);
        }
        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * updates an existing pizza
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updatePizza(
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("pizzaUUID") String pizzaUUID,
            @Valid @BeanParam Pizza pizza,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        if(!userRole.equals("admin") && !userRole.equals("restaurantManager") && !userRole.equals("chief")){
            httpStatus = 403;
        } else {
            try {
                boolean success = DataHandler.updatePizza(pizza, pizzaUUID);
                if (success) {
                    httpStatus = 200;
                } else {
                    httpStatus = 404;
                }
            } catch (IllegalArgumentException argEx) {
                httpStatus = 400;
            }
        }
        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * deletes an existing pizza identified by its uuid
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deletePizza(
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("pizzaUUID") String pizzaUUID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        if(!userRole.equals("admin") && !userRole.equals("restaurantManager") && !userRole.equals("chief")){
            httpStatus = 403;
        } else {
            try {
                UUID.fromString(pizzaUUID);
                if (DataHandler.deletePizza(pizzaUUID)) {
                    httpStatus = 200;
                } else {
                    httpStatus = 404;
                }
            } catch (IllegalArgumentException argEx) {
                httpStatus = 400;
            }
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }
}
