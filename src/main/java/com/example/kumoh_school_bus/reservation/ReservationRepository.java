package com.example.kumoh_school_bus.reservation;

import com.example.kumoh_school_bus.bus.BusTimeEntity;
import com.example.kumoh_school_bus.bus.BusTimeSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, String> {
  ReservationEntity findByReservationId(String reservationId);
  List<ReservationEntity> findAllByMember_LoginId(String loginId);
  List<ReservationEntity> findAllByMember_LoginIdAndState(String member_loginId, String state);
  ReservationEntity findByBusTimeSeat(BusTimeSeatEntity busTimeSeat);
  List<ReservationEntity> findAllByState(String state);
  List<ReservationEntity> findAllByBusTimeSeat_BusTimeAndState(BusTimeEntity busTimeSeat_busTime, String state);
}
