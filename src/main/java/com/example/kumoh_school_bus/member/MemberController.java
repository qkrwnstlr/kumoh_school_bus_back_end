package com.example.kumoh_school_bus.member;

import com.example.kumoh_school_bus.dto.MemberDTO;
import com.example.kumoh_school_bus.dto.ResponseDTO;
import com.example.kumoh_school_bus.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/auth")
public class MemberController {
  private final MemberService memberService;
  private final TokenProvider tokenProvider;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public MemberController(MemberService memberService, TokenProvider tokenProvider) {
    this.memberService = memberService;
    this.tokenProvider = tokenProvider;
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody MemberDTO memberDTO) {
    try {
      MemberEntity member = MemberEntity.builder()
          .loginId(memberDTO.getLoginId())
          .password(passwordEncoder.encode(memberDTO.getPassword()))
          .type(memberDTO.getType())
          .build();
      MemberEntity registeredUser = memberService.create(member);
      MemberDTO responseUserDTO = MemberDTO.builder()
          .loginId(registeredUser.getLoginId())
          .password(passwordEncoder.encode(registeredUser.getPassword()))
          .type(memberDTO.getType())
          .build();
      return ResponseEntity.ok().body(responseUserDTO);
    } catch (Exception e) {
      ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
      return ResponseEntity.badRequest().body(responseDTO);
    }
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticate(@RequestBody MemberDTO memberDTO) {
    MemberEntity member = memberService.getByCredentials(memberDTO.getLoginId(), memberDTO.getPassword(), passwordEncoder);
    if (member == null) {
      ResponseDTO<Object> responseDTO = ResponseDTO.builder().error("Login Failed").build();
      return ResponseEntity.badRequest().body(responseDTO);
    }
    final String token = tokenProvider.create(member);
    final MemberDTO responseUserDTO = MemberDTO.builder()
        .loginId(member.getLoginId())
        .password(passwordEncoder.encode(member.getPassword()))
        .type(member.getType())
        .token(token)
        .build();
    return ResponseEntity.ok(responseUserDTO);
  }

  @PutMapping("/edit")
  public void editUserInfo(@RequestBody MemberDTO memberDTO) {
    MemberEntity member = MemberEntity.builder()
        .loginId(memberDTO.getLoginId())
        .password(passwordEncoder.encode(memberDTO.getPassword()))
        .build();
    memberService.edit(member);
  }

  @DeleteMapping("/remove/{id}")
  public void removeUserInfo(@PathVariable String id) {
    MemberEntity member = MemberEntity.builder()
        .loginId(id)
        .build();
    memberService.delete(member);
  }
}
