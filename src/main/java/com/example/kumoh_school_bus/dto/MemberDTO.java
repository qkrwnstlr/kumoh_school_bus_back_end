package com.example.kumoh_school_bus.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDTO {
  private String memberId;
  private String loginId;
  private String password;
  private String type;
  private String name;
  private String major;
  private String token;
}
