package com.example.kumoh_school_bus.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BusTimeSeatDTO {
  BusTimeDTO busTimeDTO;
  List<TimeSeatDTO> timeSeatList;
}
