package com.example.kumoh_school_bus.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
  MemberEntity findByLoginId(String loginId);
  void deleteByLoginId(String loginId);
  boolean existsByLoginId(String loginId);
}
