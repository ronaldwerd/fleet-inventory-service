package org.ronald.mc.service.fleet.tests.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ronald.mc.service.fleet.generated.dto.Vehicle;
import org.ronald.mc.service.fleet.rest.FleetInventoryController;
import org.ronald.mc.service.fleet.service.VehicleService;
import org.ronald.mc.service.fleet.service.exceptions.VehicleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FleetInventoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class FleetInventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    @Test
    void getVehicleByVin() throws Exception {
        when(vehicleService.getVehicleByVin(anyString())).thenReturn(createVehicleDto());

        this.mockMvc.perform(get("/vehicle/vin/my-vin-number").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleId").value(1))
                .andExpect(jsonPath("$.vehicleMake").value("VEHICLE_MAKE"))
                .andExpect(jsonPath("$.vehicleYear").value(2024))
                .andExpect(jsonPath("$.vehicleName").value("MY_HONDA_ACCORD"))
                .andExpect(jsonPath("$.category").value("SEDAN"));
    }

    @Test
    void getVehicleList() throws Exception {
        List<Vehicle> vehicleList = new LinkedList<>();
        vehicleList.add(createVehicleDto());

        when(vehicleService.getAllVehicles()).thenReturn(vehicleList);

        this.mockMvc.perform(get("/vehicle/list").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].vehicleId").value(1))
                .andExpect(jsonPath("$[0].vehicleMake").value("VEHICLE_MAKE"))
                .andExpect(jsonPath("$[0].vehicleYear").value(2024))
                .andExpect(jsonPath("$[0].vehicleName").value("MY_HONDA_ACCORD"))
                .andExpect(jsonPath("$[0].category").value("SEDAN"));
    }

    @Test
    void getVehicleByVinNotFound() throws Exception {
        VehicleNotFoundException vehicleNotFoundException = new VehicleNotFoundException("BAD_VIN_NUMBER");

        when(vehicleService.getVehicleByVin(anyString())).thenThrow(vehicleNotFoundException);
        this.mockMvc.perform(get("/vehicle/vin/my-vin-number")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void postVehicleDto() throws Exception {
        when(vehicleService.saveVehicle(any())).thenReturn(createVehicleDto());

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonString = objectWriter.writeValueAsString(createVehicleDto());

        this.mockMvc.perform(post("/vehicle/add").contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vehicleId").value(1))
                .andExpect(jsonPath("$.vehicleMake").value("VEHICLE_MAKE"))
                .andExpect(jsonPath("$.vehicleYear").value(2024))
                .andExpect(jsonPath("$.vehicleName").value("MY_HONDA_ACCORD"))
                .andExpect(jsonPath("$.category").value("SEDAN"));
    }

    private Vehicle createVehicleDto() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleId(1L);
        vehicle.setVehicleMake("VEHICLE_MAKE");
        vehicle.setVehicleYear(2024);
        vehicle.setVehicleName("MY_HONDA_ACCORD");
        vehicle.setCategory(Vehicle.CategoryEnum.SEDAN);
        return vehicle;
    }
}
