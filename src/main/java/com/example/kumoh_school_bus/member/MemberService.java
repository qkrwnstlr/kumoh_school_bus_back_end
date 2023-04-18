package com.example.kumoh_school_bus.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberService {
  private final MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public MemberEntity create(MemberEntity user) {
    if (user == null || user.getLoginId() == null) throw new RuntimeException("Invalid Arguments");
    if (memberRepository.existsByLoginId(user.getLoginId())) {
      log.warn("Email already exits{}", user.getLoginId());
      throw new RuntimeException("Email already exists");
    }
    return memberRepository.save(user);
  }

  public MemberEntity getByCredentials(String email, String password, final PasswordEncoder encoder) {
    final MemberEntity originalUser = memberRepository.findByLoginId(email);
    if (originalUser != null && encoder.matches(password, originalUser.getPassword()))
      return originalUser;
    return null;
  }
}
