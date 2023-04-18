package com.example.kumoh_school_bus.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
  MemberEntity findByLoginID(String loginID);

  boolean existsByLoginID(String loginID);
}
