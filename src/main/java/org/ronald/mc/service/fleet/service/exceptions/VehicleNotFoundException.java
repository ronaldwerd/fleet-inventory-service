package org.ronald.mc.service.fleet.service.exceptions;

public class VehicleNotFoundException extends NotFoundException {

    public VehicleNotFoundException(String vin) {
        super("Could not find vehicle with vin number: " + vin);
    }
}
