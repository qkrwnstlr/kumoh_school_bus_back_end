package com.example.kumoh_school_bus.reservation;

import com.example.kumoh_school_bus.bus.BusTimeSeatEntity;
import com.example.kumoh_school_bus.member.MemberEntity;
import com.example.kumoh_school_bus.station.StationEntity;
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
  @JoinColumn(name = "busTimeSeatId")
  private BusTimeSeatEntity busTimeSeat;
  @ManyToOne
  @JoinColumn(name = "memberId")
  private MemberEntity member;

  @OneToOne
  @JoinColumn(name = "stationId")
  private StationEntity station;
}
