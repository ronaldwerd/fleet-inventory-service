package org.ronald.mc.service.fleet.service.exceptions;

import org.ronald.mc.service.fleet.db.VehicleEntity;

public class DuplicateVehicleEntityException extends DuplicateEntityException {

    public DuplicateVehicleEntityException(VehicleEntity vehicleEntity) {
        super("Vin number already exists:" + vehicleEntity.getVin() + " with vehicle id: " + vehicleEntity.getVehicleId());
    }
}
