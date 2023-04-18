package com.example.kumoh_school_bus.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDTO {
  private String memberID;
  private String loginID;
  private String password;
  private String token;
}
