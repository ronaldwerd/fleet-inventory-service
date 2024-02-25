package org.ronald.mc.service.fleet.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface VehicleUsageDAO extends JpaRepository<VehicleUsageEntity, Long> {

}
