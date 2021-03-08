package ch.bzz.pizza.service;

import ch.bzz.pizza.data.DataHandler;
import ch.bzz.pizza.model.Pizza;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
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
    ) {
        List<Pizza> pizzaList = DataHandler.getPizzaList();
        Response response = Response
                .status(200)
                .entity(pizzaList)
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
            @QueryParam("uuid") String pizzaUUID
    ) {
        Pizza pizza = null;
        int httpStatus;

        try {
            UUID pizzyKey = UUID.fromString(pizzaUUID);
            pizza = DataHandler.findPizzaByUUID(pizzaUUID);
            if (pizza != null) {
                httpStatus = 200;
            } else {
                httpStatus = 404;
            }
        } catch (IllegalArgumentException argEx) {
            httpStatus = 400;
        }

        Response response = Response
                .status(httpStatus)
                .entity(pizza)
                .build();
        return response;
    }
}
