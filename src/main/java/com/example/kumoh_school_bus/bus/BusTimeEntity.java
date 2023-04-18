package com.example.kumoh_school_bus.bus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bus_time")
public class BusTimeEntity {
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name="system-uuid", strategy = "uuid")
  private String busTimeId;
  private String busTimeDeparture;
  private int busTimeNum;
  @ManyToOne
  @JoinColumn(name="busId")
  private BusEntity bus;
  @OneToMany(mappedBy = "busTime")
  private List<BusTimeSeatEntity> busTimeSeats;
}
