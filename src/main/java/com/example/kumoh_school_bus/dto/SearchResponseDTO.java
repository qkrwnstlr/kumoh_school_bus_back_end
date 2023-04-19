package com.example.kumoh_school_bus.dto;

import com.example.kumoh_school_bus.station.StationEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchResponseDTO {
  final List<BusDTO> busList;
  final String direction;
  final StationEntity station;
  final String reservationDate;
}
