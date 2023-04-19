package com.example.kumoh_school_bus.bus;

import com.example.kumoh_school_bus.station.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<RouteEntity, String> {
  List<RouteEntity> findAllByBus_BusTypeAndStation(String bus_busType, StationEntity station);
}
