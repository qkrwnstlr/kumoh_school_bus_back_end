package com.example.kumoh_school_bus.reservation;

import com.example.kumoh_school_bus.bus.*;
import com.example.kumoh_school_bus.dto.*;
import com.example.kumoh_school_bus.member.MemberEntity;
import com.example.kumoh_school_bus.member.MemberRepository;
import com.example.kumoh_school_bus.station.StationEntity;
import com.example.kumoh_school_bus.station.StationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
  private final RouteRepository routeRepository;

  public ReservationService(ReservationRepository reservationRepository, StationRepository stationRepository, MemberRepository memberRepository, BusTimeSeatRepository busTimeSeatRepository, BusRepository busRepository, BusTimeRepository busTimeRepository, RouteRepository routeRepository) {
    this.reservationRepository = reservationRepository;
    this.stationRepository = stationRepository;
    this.memberRepository = memberRepository;
    this.busTimeSeatRepository = busTimeSeatRepository;
    this.busRepository = busRepository;
    this.busTimeRepository = busTimeRepository;
    this.routeRepository = routeRepository;
  }

  @Transactional
  List<ReservationDTO> addReservation(ReservationAddRequestDTO requestDTO, String loginId) {
    StationEntity station = stationRepository.findByStationName(requestDTO.getStation());
    BusEntity bus = busRepository.findByBusNum(requestDTO.getBy());
    BusTimeEntity busTime = busTimeRepository.findByBusAndBusTimeDeparture(bus, requestDTO.getDeparture());
    BusTimeSeatEntity busTimeSeat = busTimeSeatRepository.findByBusTimeAndBusTimeSeatDateAndBusTimeSeatNum(busTime, requestDTO.getWhen(), requestDTO.getSeatNum());
    if (busTimeSeat.isBusTimeSeatIsReserved()) throw new RuntimeException("is already reserved");
    MemberEntity member = memberRepository.findByLoginId(loginId);

    ReservationEntity reservationEntity = ReservationEntity.builder()
        .station(station).busTimeSeat(busTimeSeat).member(member).state("예약됨").build();
    reservationRepository.save(reservationEntity);
    busTimeSeat.setBusTimeSeatIsReserved(true);
    busTimeSeatRepository.save(busTimeSeat);

    return getAllReservationByMember(loginId);
  }

  @Transactional
  List<ReservationDTO> removeReservation(String reservationId, String loginId) {
    ReservationEntity reservationEntity = reservationRepository.findByReservationId(reservationId);
    reservationEntity.getBusTimeSeat().setBusTimeSeatIsReserved(false);
    reservationEntity.setState("취소됨");
    reservationRepository.save(reservationEntity);
    busTimeSeatRepository.save(reservationEntity.getBusTimeSeat());
    return getAllReservationByMember(loginId);
  }

  List<ReservationDTO> getAllReservationByMember(String loginId) {
    List<ReservationEntity> reservationEntities = reservationRepository.findAllByMember_LoginIdAndState(loginId, "예약됨");
    return reservationEntities.stream().map(e -> {
      RouteEntity routeEntity = routeRepository.findByStationAndBus(e.getStation(), e.getBusTimeSeat().getBusTime().getBus());
      return ReservationDTO.builder()
          .id(e.getReservationId())
          .station(e.getStation().getStationName())
          .type(e.getBusTimeSeat().getBusTime().getBus().getBusType())
          .when(e.getBusTimeSeat().getBusTimeSeatDate())
          .by(e.getBusTimeSeat().getBusTime().getBus().getBusNum())
          .seatNum(e.getBusTimeSeat().getBusTimeSeatNum())
          .taken(routeEntity.getRouteTime())
          .at(e.getBusTimeSeat().getBusTime().getBusTimeDeparture())
          .build();
    }).collect(Collectors.toList());
  }

  BusTimeReservationDTO getAllReservationByBus(String busTimeId) {
    BusTimeEntity busTimeEntity = busTimeRepository.findByBusTimeId(busTimeId);

    BusTimeDTO busTimeDTO = BusTimeDTO.builder().startTime(busTimeEntity.getBusTimeDeparture()).endTime("").build();

    List<TimeSeatReservationDTO> timeSeatReservationList = new ArrayList<>();
    for (BusTimeSeatEntity busTimeSeatEntity : busTimeEntity.getBusTimeSeats()) {
      TimeSeatReservationDTO timeSeatReservationDTO;
      TimeSeatDTO timeSeatDTO = TimeSeatDTO.builder().isReserved(busTimeSeatEntity.isBusTimeSeatIsReserved()).seatNum(busTimeSeatEntity.getBusTimeSeatNum()).build();
      if (timeSeatDTO.isReserved()) {
        MemberEntity memberEntity = reservationRepository.findByBusTimeSeat(busTimeSeatEntity).getMember();
        MemberDTO memberDTO = MemberDTO.builder().loginId(memberEntity.getLoginId()).name(memberEntity.getName()).major(memberEntity.getMajor()).build();
        timeSeatReservationDTO = TimeSeatReservationDTO.builder().timeSeatDTO(timeSeatDTO).memberDTO(memberDTO).build();
      } else {
        timeSeatReservationDTO = TimeSeatReservationDTO.builder().timeSeatDTO(timeSeatDTO).build();
      }
      timeSeatReservationList.add(timeSeatReservationDTO);
    }

    return BusTimeReservationDTO.builder().busTimeDTO(busTimeDTO).timeSeatReservationList(timeSeatReservationList).build();
  }

  List<ReservationDTO> getAllFinishedReservation() {
    List<ReservationEntity> reservationEntities = reservationRepository.findAllByStateNot("취소됨");
    return reservationEntities.stream().map(e -> ReservationDTO.builder()
        .when(e.getBusTimeSeat().getBusTimeSeatDate())
        .build()).collect(Collectors.toList());
  }


  void finish(String busTimeId) {
    List<ReservationEntity> reservationEntities = reservationRepository.findAllByBusTimeSeat_BusTimeAndState(busTimeRepository.findByBusTimeId(busTimeId), "예약됨");
    for (ReservationEntity reservationEntity : reservationEntities) {
      reservationEntity.setState("완료됨");
      reservationRepository.save(reservationEntity);
    }
  }
}
