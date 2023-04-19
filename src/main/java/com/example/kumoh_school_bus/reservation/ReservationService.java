package com.example.kumoh_school_bus.reservation;

import com.example.kumoh_school_bus.bus.*;
import com.example.kumoh_school_bus.dto.ReservationAddRequestDTO;
import com.example.kumoh_school_bus.dto.ReservationDTO;
import com.example.kumoh_school_bus.member.MemberEntity;
import com.example.kumoh_school_bus.member.MemberRepository;
import com.example.kumoh_school_bus.station.StationEntity;
import com.example.kumoh_school_bus.station.StationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReservationService {
  private final ReservationRepository reservationRepository;
  private final StationRepository stationRepository;
  private final MemberRepository memberRepository;
  private final BusTimeSeatRepository busTimeSeatRepository;
  private final BusRepository busRepository;
  private final BusTimeRepository busTimeRepository;

  public ReservationService(ReservationRepository reservationRepository, StationRepository stationRepository, MemberRepository memberRepository, BusTimeSeatRepository busTimeSeatRepository, BusRepository busRepository, BusTimeRepository busTimeRepository) {
    this.reservationRepository = reservationRepository;
    this.stationRepository = stationRepository;
    this.memberRepository = memberRepository;
    this.busTimeSeatRepository = busTimeSeatRepository;
    this.busRepository = busRepository;
    this.busTimeRepository = busTimeRepository;
  }

  @Transactional
  List<ReservationDTO> addReservation(ReservationAddRequestDTO requestDTO, String loginId) {
    StationEntity station = stationRepository.findByStationName(requestDTO.getStation());
    log.info("station", station);
    BusEntity bus = busRepository.findByBusNum(requestDTO.getBy());
    log.info("bus", bus);
    BusTimeEntity busTime = busTimeRepository.findByBusAndBusTimeDeparture(bus, requestDTO.getDeparture());
    BusTimeSeatEntity busTimeSeat = busTimeSeatRepository.findByBusTimeAndBusTimeSeatDateAndBusTimeSeatNum(busTime, requestDTO.getWhen(), requestDTO.getSeatNum());
    if(busTimeSeat.isBusTimeSeatIsReserved()) throw new RuntimeException("is already reserved");
    log.info("busTimeSeat", busTimeSeat);
    MemberEntity member = memberRepository.findByLoginId(loginId);
    log.info("member", member);

    ReservationEntity reservationEntity = ReservationEntity.builder()
        .station(station).busTimeSeat(busTimeSeat).member(member).state("예약됨").build();
    reservationRepository.save(reservationEntity);
    busTimeSeat.setBusTimeSeatIsReserved(true);
    busTimeSeatRepository.save(busTimeSeat);

    return getAllReservation(loginId);
  }

  List<ReservationDTO> removeReservation(String reservationId, String loginId) {
    ReservationEntity reservationEntity = reservationRepository.findByReservationId(reservationId);
    reservationEntity.getBusTimeSeat().setBusTimeSeatIsReserved(false);
    reservationRepository.deleteById(reservationId);
    busTimeSeatRepository.save(reservationEntity.getBusTimeSeat());
    return getAllReservation(loginId);
  }

  List<ReservationDTO> getAllReservation(String loginId) {
    List<ReservationEntity> reservationEntities = reservationRepository.findAllByMember_LoginId(loginId);
    return reservationEntities.stream().map(e -> {
      if(!Objects.equals(e.getState(), "예약됨")) return null;
      return ReservationDTO.builder().id(e.getReservationId()).station(e.getStation().getStationName()).type(e.getBusTimeSeat().getBusTime().getBus().getBusType()).when(e.getBusTimeSeat().getBusTimeSeatDate()).by(e.getBusTimeSeat().getBusTime().getBus().getBusNum()).seatNum(e.getBusTimeSeat().getBusTimeSeatNum()).build();
    }).collect(Collectors.toList());
  }
}
