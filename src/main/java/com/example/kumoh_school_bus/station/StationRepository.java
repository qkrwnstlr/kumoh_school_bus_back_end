package com.example.kumoh_school_bus.station;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<StationEntity, String> {
  StationEntity findByStationName(String stationName);
}
