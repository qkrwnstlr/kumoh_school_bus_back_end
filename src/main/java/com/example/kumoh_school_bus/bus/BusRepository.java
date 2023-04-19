package com.example.kumoh_school_bus.bus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<BusEntity, String> {
  BusEntity findByBusNum(String busNum);
}
