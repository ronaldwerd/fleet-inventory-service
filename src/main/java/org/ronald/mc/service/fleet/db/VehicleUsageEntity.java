package org.ronald.mc.service.fleet.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="vehicle_usage")
public class VehicleUsageEntity {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name="vehicle_usage_id")
  private Long vehicleUsageId;

  @OneToOne
  @JoinColumn(name = "vehicle_id")
  private VehicleEntity vehicleEntity;

  @Column(name="trip_length_kilometers")
  private Integer tripLengthKilometers;

  @Column(name="trip_length_miles")
  private Integer tripLengthMiles;

  @Column(name="trip_started")
  private LocalDateTime tripStarted;

  @Column(name="trip_ended")
  private LocalDateTime tripEnded;
}
