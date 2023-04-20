package com.example.kumoh_school_bus.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SelectBusTimeDTO {
  String id;
  int busTimeNum;
  String startTime;
}
