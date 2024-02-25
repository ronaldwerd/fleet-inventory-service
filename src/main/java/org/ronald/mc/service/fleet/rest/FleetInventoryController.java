package org.ronald.mc.service.fleet.rest;

import jakarta.validation.Valid;
import org.ronald.mc.service.fleet.generated.VehicleApi;
import org.ronald.mc.service.fleet.generated.dto.Vehicle;
import org.ronald.mc.service.fleet.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FleetInventoryController implements VehicleApi {
    private VehicleService vehicleService;

    @Autowired
    public void setBeans(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Override
    public ResponseEntity<Vehicle> addVehicle(@Valid Vehicle vehicle) {
        return new ResponseEntity<>(vehicleService.saveVehicle(vehicle), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Vehicle> getVehicleByVin(String vinNumber) {
        return new ResponseEntity<>(vehicleService.getVehicleByVin(vinNumber), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Vehicle>> getVehicleList() {
        return new ResponseEntity<>(vehicleService.getAllVehicles(), HttpStatus.OK);
    }
}
