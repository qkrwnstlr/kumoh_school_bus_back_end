package com.example.kumoh_school_bus.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeSeatReservationDTO {
  TimeSeatDTO timeSeatDTO;
  MemberDTO memberDTO;
}
