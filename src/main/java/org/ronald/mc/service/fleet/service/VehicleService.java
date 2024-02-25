package org.ronald.mc.service.fleet.service;

import lombok.extern.slf4j.Slf4j;
import org.ronald.mc.service.fleet.db.VehicleDAO;
import org.ronald.mc.service.fleet.db.VehicleEntity;
import org.ronald.mc.service.fleet.db.VehicleUsageDAO;
import org.ronald.mc.service.fleet.generated.dto.Vehicle;
import org.ronald.mc.service.fleet.mapper.VehicleModelMapper;
import org.ronald.mc.service.fleet.service.exceptions.DuplicateVehicleEntityException;
import org.ronald.mc.service.fleet.service.exceptions.VehicleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class VehicleService {
    private VehicleDAO vehicleDAO;
    private VehicleUsageDAO vehicleUsageDAO;
    private VehicleModelMapper vehicleModelMapper;

    @Autowired
    public void setBeans(VehicleDAO vehicleDAO, VehicleUsageDAO vehicleUsageDAO, VehicleModelMapper vehicleModelMapper) {
        this.vehicleDAO = vehicleDAO;
        this.vehicleUsageDAO = vehicleUsageDAO;
        this.vehicleModelMapper = vehicleModelMapper;
    }

    public Vehicle saveVehicle(Vehicle vehicleDto) {
        throwDuplicateExceptionOnExistingVinNumber(vehicleDto);

        VehicleEntity vehicleEntity = vehicleModelMapper.mapDtoToEntity(vehicleDto);
        LocalDateTime now = LocalDateTime.now();
        vehicleEntity.setCreatedOn(now);
        vehicleEntity.setUpdatedOn(now);

        VehicleEntity vehicleEntitySaved = vehicleDAO.save(vehicleEntity);

        Vehicle storedVehicle = vehicleModelMapper.mapEntityToDto(vehicleEntitySaved);

        log.info("Vehicle saved: " + storedVehicle.toString());

        return storedVehicle;
    }

    private void throwDuplicateExceptionOnExistingVinNumber(Vehicle vehicle) {
        VehicleEntity vehicleEntity = vehicleDAO.findVehicleEntityByVin(vehicle.getVin());

        if(vehicleEntity != null) {
            throw new DuplicateVehicleEntityException(vehicleEntity);
        }
    }

    public Vehicle getVehicleByVin(String vinNumber) {
        VehicleEntity vehicleEntity = vehicleDAO.findVehicleEntityByVin(vinNumber);

        if (vehicleEntity == null) {
            throw new VehicleNotFoundException(vinNumber);
        }

        return vehicleModelMapper.mapEntityToDto(vehicleEntity);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleModelMapper.mapEntitiesToDtos(vehicleDAO.findAll());
    }
}
