package com.example.kumoh_school_bus.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BusDTO {
  String busNum;
  List<BusTimeSeatDTO> busTimeSeatList;
}
