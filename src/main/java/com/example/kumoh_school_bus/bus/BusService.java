package com.example.kumoh_school_bus.bus;

import com.example.kumoh_school_bus.dto.*;
import com.example.kumoh_school_bus.station.StationEntity;
import com.example.kumoh_school_bus.station.StationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class BusService {
  private final BusRepository busRepository;
  private final RouteRepository routeRepository;
  private final StationRepository stationRepository;

  BusService(BusRepository busRepository, RouteRepository routeRepository, StationRepository stationRepository) {
    this.busRepository = busRepository;
    this.routeRepository = routeRepository;
    this.stationRepository = stationRepository;
  }

  SearchResponseDTO getSearchBus(SearchRequestDTO requestDTO) {
    StationEntity stationEntity = stationRepository.findByStationName(requestDTO.getStation());
    List<RouteEntity> routeEntities = routeRepository.findAllByBus_BusTypeAndStation(requestDTO.getType(), stationEntity);
    List<BusEntity> busEntities = routeEntities.stream().map(RouteEntity::getBus).collect(Collectors.toList());
    List<BusDTO> busDTOList = new ArrayList<>();
    for (BusEntity busEntity : busEntities) {
      List<BusTimeSeatDTO> busTimeSeatList = new ArrayList<>();
      for (BusTimeEntity busTimeEntity : busEntity.getBusTimes()) {
        BusTimeDTO busTimeDTO = BusTimeDTO.builder().startTime(busTimeEntity.getBusTimeDeparture()).endTime("").build();
        List<TimeSeatDTO> timeSeatList = new ArrayList<>();
        for (BusTimeSeatEntity busTimeSeatEntity : busTimeEntity.getBusTimeSeats()) {
          if (busTimeSeatEntity.isBusTimeSeatIsReserved()) continue;
          TimeSeatDTO timeSeatDTO = TimeSeatDTO.builder().isReserved(busTimeSeatEntity.isBusTimeSeatIsReserved()).seatNum(busTimeSeatEntity.getBusTimeSeatNum()).build();
          timeSeatList.add(timeSeatDTO);
        }
        BusTimeSeatDTO busTimeSeatDTO = BusTimeSeatDTO.builder().busTimeDTO(busTimeDTO).timeSeatList(timeSeatList).build();
        busTimeSeatList.add(busTimeSeatDTO);
      }
      BusDTO busDTO = BusDTO.builder().busNum(busEntity.getBusNum()).busTimeSeatList(busTimeSeatList).build();
      busDTOList.add(busDTO);
    }
    return SearchResponseDTO.builder().station(stationEntity).busList(busDTOList).direction(requestDTO.getType()).reservationDate(requestDTO.getDate()).build();
  }

  List<SelectResponseDTO> getSelectResponseDTO() {
    List<BusEntity> busEntities = busRepository.findAll();
    List<SelectResponseDTO> selectResponseDTOList = new ArrayList<>();
    for (BusEntity busEntity : busEntities) {
      List<SelectBusTimeDTO> selectBusTimeDTOList = new ArrayList<>();
      for (BusTimeEntity busTimeEntity : busEntity.getBusTimes()) {
        SelectBusTimeDTO selectBusTimeDTO = SelectBusTimeDTO.builder().busTimeNum(busTimeEntity.getBusTimeNum()).startTime(busTimeEntity.getBusTimeDeparture()).id(busTimeEntity.getBusTimeId()).build();
        selectBusTimeDTOList.add(selectBusTimeDTO);
      }
      SelectResponseDTO selectResponseDTO = SelectResponseDTO.builder().busNum(busEntity.getBusNum()).selectBusTimeList(selectBusTimeDTOList).build();
      selectResponseDTOList.add(selectResponseDTO);
    }
    return selectResponseDTOList;
  }
}
