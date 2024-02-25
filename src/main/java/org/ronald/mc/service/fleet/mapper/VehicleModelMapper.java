package org.ronald.mc.service.fleet.mapper;

import org.modelmapper.ModelMapper;
import org.ronald.mc.service.fleet.db.VehicleEntity;
import org.ronald.mc.service.fleet.generated.dto.Vehicle;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VehicleModelMapper {
  private final ModelMapper modelMapper = new ModelMapper();

  public VehicleModelMapper() {

  }

  public VehicleEntity mapDtoToEntity(Vehicle vehicle) {
    return this.modelMapper.map(vehicle, VehicleEntity.class);
  }

  public Vehicle mapEntityToDto(VehicleEntity vehicleEntity) {
    return this.modelMapper.map(vehicleEntity, Vehicle.class);
  }

  public List<Vehicle> mapEntitiesToDtos(List<VehicleEntity> vehicleEntityList) {
    return ModelMapperUtils.mapList(modelMapper, vehicleEntityList, Vehicle.class);
  }
}
