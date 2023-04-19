package com.example.kumoh_school_bus.bus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BusTimeSeatRepository extends JpaRepository<BusTimeSeatEntity, String> {
  BusTimeSeatEntity findByBusTimeAndBusTimeSeatDateAndBusTimeSeatNum(BusTimeEntity busTime, String busTimeSeatDate, int busTimeSeatNum);
}
