package com.example.kumoh_school_bus.bus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BusTimeRepository extends JpaRepository<BusTimeEntity, String> {
  BusTimeEntity findByBusAndBusTimeDeparture(BusEntity bus, String busTimeDeparture);
}
