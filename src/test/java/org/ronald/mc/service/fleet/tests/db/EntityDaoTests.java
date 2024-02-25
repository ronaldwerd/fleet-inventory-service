package org.ronald.mc.service.fleet.tests.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ronald.mc.service.fleet.db.VehicleDAO;
import org.ronald.mc.service.fleet.db.VehicleEntity;
import org.ronald.mc.service.fleet.db.VehicleUsageDAO;
import org.ronald.mc.service.fleet.db.VehicleUsageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EntityDaoTests {

    @Autowired
    private VehicleDAO vehicleDAO;

    @Autowired
    private VehicleUsageDAO vehicleUsageDAO;

    @Test
    public void vehicle_writeEntity_compareEntity() {
        VehicleEntity vehicleEntity = createVehicleEntity();
        VehicleEntity savedVehicleEntity = vehicleDAO.save(vehicleEntity);

        Optional<VehicleEntity> vehicleEntityOptional = vehicleDAO.findById(savedVehicleEntity.getVehicleId());
        assert vehicleEntityOptional.isPresent();

        VehicleEntity vehicleEntityFromDb = vehicleEntityOptional.get();
        assertEquals("MY_VIN_NUMBER", vehicleEntityFromDb.getVin());
        assert vehicleEntityFromDb.getVehicleYear() == 2024;
        assertEquals("Ronald's Honda", vehicleEntityFromDb.getVehicleName());
        assertEquals("Honda", vehicleEntityFromDb.getVehicleMake());
        assertEquals("SEDAN", vehicleEntityFromDb.getCategory());
        assertEquals(vehicleEntity.getCreatedOn(), vehicleEntityFromDb.getCreatedOn());
        assertEquals(vehicleEntity.getUpdatedOn(), vehicleEntityFromDb.getUpdatedOn());

        vehicleDAO.save(vehicleEntity);
    }

    @Test
    public void vehicle_writeEntityTwice_expectException() {
        VehicleEntity vehicleEntity = createVehicleEntity();
        VehicleEntity vehicleEntityDuplicate = createVehicleEntity();

        vehicleDAO.save(vehicleEntity);

        assertThrows(DataIntegrityViolationException.class, () -> {
            vehicleDAO.save(vehicleEntityDuplicate);
        });
    }

    @Test
    public void vehicle_writeUsage_compareUsage() {
        VehicleEntity vehicleEntity = this.vehicleDAO.save(createVehicleEntity());

        VehicleUsageEntity vehicleUsageEntity = createVehicleUsageEntity();
        vehicleUsageEntity.setVehicleEntity(vehicleEntity);
        VehicleUsageEntity savedVehicleUsageEntity = vehicleUsageDAO.save(vehicleUsageEntity);

        Optional<VehicleUsageEntity> vehicleUsageEntityOptional = vehicleUsageDAO.findById(savedVehicleUsageEntity.getVehicleUsageId());
        assert vehicleUsageEntityOptional.isPresent();
        VehicleUsageEntity vehicleUsageEntityFromDb = vehicleUsageEntityOptional.get();

        assertEquals(vehicleUsageEntity.getTripLengthKilometers(), vehicleUsageEntityFromDb.getTripLengthKilometers());
        assertEquals(vehicleUsageEntity.getTripLengthMiles(), vehicleUsageEntityFromDb.getTripLengthMiles());
        assertEquals(vehicleUsageEntity.getTripStarted(), vehicleUsageEntityFromDb.getTripStarted());
        assertEquals(vehicleUsageEntity.getTripEnded(), vehicleUsageEntityFromDb.getTripEnded());

        assert (vehicleUsageEntity.getVehicleEntity() != null);
    }

    @Test
    public void vehicle_writeUsage_constraintNotNull() {
        VehicleUsageEntity vehicleUsageEntity = createVehicleUsageEntity();

        assertThrows(DataIntegrityViolationException.class, () -> {
            vehicleUsageDAO.save(vehicleUsageEntity);
        });
    }

    @Test
    public void vehicle_writeUsage_constraintVehicleIdDoesNotExist() {
        VehicleEntity vehicleEntity = createVehicleEntity();

        VehicleUsageEntity vehicleUsageEntity = createVehicleUsageEntity();
        vehicleUsageEntity.setVehicleEntity(vehicleEntity);

        assertThrows(DataIntegrityViolationException.class, () -> {
            vehicleUsageDAO.save(vehicleUsageEntity);
        });
    }

    private VehicleUsageEntity createVehicleUsageEntity() {
        LocalDateTime now = LocalDateTime.now();

        VehicleUsageEntity vehicleUsageEntity = new VehicleUsageEntity();
        vehicleUsageEntity.setTripStarted(now);
        vehicleUsageEntity.setTripEnded(now);
        vehicleUsageEntity.setTripLengthMiles(60);
        vehicleUsageEntity.setTripLengthKilometers(100);

        return vehicleUsageEntity;
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
