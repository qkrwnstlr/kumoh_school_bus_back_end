package com.example.kumoh_school_bus.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationDTO {
  String id;
  String type;
  String station;
  String by;
  String when;
  int seatNum;
  String taken;
  String at;
}
