package com.example.kumoh_school_bus.reservation;

import com.example.kumoh_school_bus.bus.BusTimeSeatEntity;
import com.example.kumoh_school_bus.member.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservation")
public class ReservationEntity {
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name="system-uuid", strategy = "uuid")
  private String reservationId;
  private String state;
  @OneToOne
  @JoinColumn(name = "busTimeSeatID")
  private BusTimeSeatEntity busTimeSeat;
  @ManyToOne
  @JoinColumn(name = "memberID")
  private MemberEntity member;
}
