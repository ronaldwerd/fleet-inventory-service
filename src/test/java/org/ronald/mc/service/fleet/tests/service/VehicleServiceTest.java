package org.ronald.mc.service.fleet.tests.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ronald.mc.service.fleet.db.VehicleDAO;
import org.ronald.mc.service.fleet.db.VehicleEntity;
import org.ronald.mc.service.fleet.db.VehicleUsageDAO;
import org.ronald.mc.service.fleet.generated.dto.Vehicle;
import org.ronald.mc.service.fleet.mapper.VehicleModelMapper;
import org.ronald.mc.service.fleet.service.VehicleService;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class VehicleServiceTest {

    private VehicleService vehicleService;

    @Mock
    private VehicleDAO vehicleDAO;

    @Mock
    private VehicleUsageDAO vehicleUsageDAO;

    @Before
    public void setup() {
        vehicleService = new VehicleService();
        vehicleService.setBeans(vehicleDAO, vehicleUsageDAO, new VehicleModelMapper());
    }

    @Test
    public void getVehicleByVin() {
        VehicleEntity vehicleEntity = createVehicleEntity();
        vehicleEntity.setVehicleId(100L);

        when(vehicleDAO.findVehicleEntityByVin(anyString())).thenReturn(vehicleEntity);
        Vehicle vehicle = vehicleService.getVehicleByVin("MY_VIN_NUMBER");

        assertEquals("MY_VIN_NUMBER", vehicle.getVin());
        assert vehicle.getVehicleYear() == 2024;
        assertEquals("Ronald's Honda", vehicle.getVehicleName());
        assertEquals("Honda", vehicle.getVehicleMake());
        assertEquals("SEDAN", vehicle.getCategory().getValue());
    }

    @Test
    public void getAllVehicles() {
        VehicleEntity vehicleEntity = createVehicleEntity();
        vehicleEntity.setVehicleId(100L);

        List<VehicleEntity> vehicleEntities = new LinkedList<>();
        vehicleEntities.add(vehicleEntity);

        when(vehicleDAO.findAll()).thenReturn(vehicleEntities);
        List<Vehicle> vehicleList = vehicleService.getAllVehicles();

        Vehicle vehicle = vehicleList.get(0);
        assertEquals("MY_VIN_NUMBER", vehicle.getVin());
        assert vehicle.getVehicleYear() == 2024;
        assertEquals("Ronald's Honda", vehicle.getVehicleName());
        assertEquals("Honda", vehicle.getVehicleMake());
        assertEquals("SEDAN", vehicle.getCategory().getValue());
    }

    @Test
    public void saveVehicle() {
        when(vehicleDAO.save(any())).thenReturn(createVehicleEntity());
        Vehicle vehicle = vehicleService.saveVehicle(new Vehicle());
        assertEquals("MY_VIN_NUMBER", vehicle.getVin());

        assert vehicle.getVehicleYear() == 2024;
        assertEquals("Ronald's Honda", vehicle.getVehicleName());
        assertEquals("Honda", vehicle.getVehicleMake());
        assertEquals("SEDAN", vehicle.getCategory().getValue());
    }

    private VehicleEntity createVehicleEntity() {
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setVin("MY_VIN_NUMBER");
        vehicleEntity.setVehicleYear(2024);
        vehicleEntity.setVehicleName("Ronald's Honda");
        vehicleEntity.setVehicleMake("Honda");
        vehicleEntity.setCategory("SEDAN");

        LocalDateTime createdOn = LocalDateTime.now();
        vehicleEntity.setCreatedOn(createdOn);
        vehicleEntity.setUpdatedOn(createdOn);
        return vehicleEntity;
    }
}
