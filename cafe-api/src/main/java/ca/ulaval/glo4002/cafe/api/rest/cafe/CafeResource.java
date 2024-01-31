package ca.ulaval.glo4002.cafe.api.rest.cafe;

import ca.ulaval.glo4002.cafe.api.rest.inventory.AddInventoryRequest;
import ca.ulaval.glo4002.cafe.api.rest.inventory.InventoryAssembler;
import ca.ulaval.glo4002.cafe.domain.tax.Location;
import ca.ulaval.glo4002.cafe.usecase.AddToInventory;
import ca.ulaval.glo4002.cafe.usecase.ChangeReservationConfig;
import ca.ulaval.glo4002.cafe.usecase.CloseCafe;
import ca.ulaval.glo4002.cafe.usecase.FindCafe;
import ca.ulaval.glo4002.cafe.usecase.FindInventory;
import ca.ulaval.glo4002.cafe.usecase.dto.CafeDTO;
import ca.ulaval.glo4002.cafe.usecase.dto.IngredientDTO;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class CafeResource {

    private final CafeAssembler cafeAssembler;
    private final InventoryAssembler inventoryAssembler;
    private final FindCafe findCafe;
    private final CloseCafe closeCafe;
    private final ChangeReservationConfig changeReservationConfig;
    private final FindInventory findInventory;
    private final AddToInventory addToInventory;

    public CafeResource(InventoryAssembler inventoryAssembler, FindCafe findCafe, CloseCafe closeCafe,
        ChangeReservationConfig changeReservationConfig, CafeAssembler cafeAssembler,
        FindInventory findInventory, AddToInventory addToInventory) {
        this.cafeAssembler = cafeAssembler;
        this.inventoryAssembler = inventoryAssembler;
        this.findCafe = findCafe;
        this.closeCafe = closeCafe;
        this.changeReservationConfig = changeReservationConfig;
        this.findInventory = findInventory;
        this.addToInventory = addToInventory;
    }

    @GET
    @Path("/layout")
    public Response getLayout() {
        CafeDTO cafe = findCafe.find();

        return Response.status(200).entity(cafeAssembler.toCafeResponse(cafe)).build();
    }

    @POST
    @Path("/config")
    public Response changeConfig(CafeConfigRequest cafeConfigRequest) {
        CafeParams cafeParams = new CafeParams(cafeConfigRequest.organizationName(), cafeConfigRequest.cubeSize(),
            cafeConfigRequest.groupReservationMethod(), cafeConfigRequest.tip());
        Location location = new Location(cafeConfigRequest.country(), cafeConfigRequest.province(),
            cafeConfigRequest.state());

        changeReservationConfig.change(cafeParams.getReservationType(), cafeParams.getCafeName(),
            cafeParams.getCubeSize(), location, cafeParams.getTip());

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/close")
    public Response close() {
        closeCafe.close();

        return Response.status(200).build();
    }

    @GET
    @Path("/inventory")
    public Response getInventory() {
        List<IngredientDTO> inventory = findInventory.find();

        return Response.status(200).entity(inventoryAssembler.fromIngredientsDTOtoInventoryResponse(inventory)).build();
    }

    @PUT
    @Path("/inventory")
    public Response addToInventory(AddInventoryRequest addInventoryRequest) {
        addToInventory.add(inventoryAssembler.fromInventoryRequestToIngredients(addInventoryRequest));

        return Response.status(200).build();
    }
}
