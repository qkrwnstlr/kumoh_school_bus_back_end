package com.example.kumoh_school_bus.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MemberService {
  private final MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public MemberEntity create(MemberEntity member) {
    if (member == null || member.getLoginId() == null) throw new RuntimeException("Invalid Arguments");
    if (memberRepository.existsByLoginId(member.getLoginId())) {
      log.warn("id already exits{}", member.getLoginId());
      throw new RuntimeException("id already exists");
    }
    return memberRepository.save(member);
  }

  public MemberEntity getByCredentials(String email, String password, final PasswordEncoder encoder) {
    final MemberEntity originalUser = memberRepository.findByLoginId(email);
    if (originalUser != null && encoder.matches(password, originalUser.getPassword()))
      return originalUser;
    return null;
  }

  public void edit(MemberEntity member) {
    if (member == null || member.getLoginId() == null) throw new RuntimeException("Invalid Arguments");
    if (!memberRepository.existsByLoginId(member.getLoginId())) {
      log.warn("id is not exits{}", member.getLoginId());
      throw new RuntimeException("id is not exists");
    }
    MemberEntity before = memberRepository.findByLoginId(member.getLoginId());
    before.setPassword(member.getPassword());
    memberRepository.save(before);
  }

  @Transactional
  public void delete(MemberEntity member) {
    if (member == null || member.getLoginId() == null) throw new RuntimeException("Invalid Arguments");
    if (!memberRepository.existsByLoginId(member.getLoginId())) {
      log.warn("id is not exits{}", member.getLoginId());
      throw new RuntimeException("id is not exists");
    }
    memberRepository.deleteByLoginId(member.getLoginId());
  }
}
