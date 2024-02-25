package org.ronald.mc.service.fleet.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name="vehicle")
public class VehicleEntity {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="vehicle_id")
  private Long vehicleId;

  @Column(name="vehicle_make")
  private String vehicleMake;

  @Column(name="vehicle_name")
  private String vehicleName;

  @Column(name="category")
  private String category;

  @Column(name="vehicle_year")
  private Integer vehicleYear;

  @Column(name="vin", unique = true)
  private String vin;

  @Column(name="created_on")
  private LocalDateTime createdOn;

  @Column(name="updated_on")
  private LocalDateTime updatedOn;
}
