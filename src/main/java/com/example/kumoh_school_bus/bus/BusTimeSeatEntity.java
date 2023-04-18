package com.example.kumoh_school_bus.bus;

import com.example.kumoh_school_bus.reservation.ReservationEntity;
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
@Table(name = "bus_time_seat")
public class BusTimeSeatEntity {
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name="system-uuid", strategy = "uuid")
  private String busTimeSeatId;
  private String busTimeSeatDate;
  private int busTimeSeatNum;
  private boolean busTimeSeatIsReserved;
  @ManyToOne
  @JoinColumn(name = "busTimeId")
  private BusTimeEntity busTime;
}
