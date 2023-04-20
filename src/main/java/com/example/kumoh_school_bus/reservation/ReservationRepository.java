package com.example.kumoh_school_bus.reservation;

import com.example.kumoh_school_bus.bus.BusTimeSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, String> {
  ReservationEntity findByReservationId(String reservationId);
  List<ReservationEntity> findAllByMember_LoginId(String loginId);
  ReservationEntity findByBusTimeSeat(BusTimeSeatEntity busTimeSeat);
}
