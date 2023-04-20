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
@Table(name = "bus")
public class BusEntity {
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name="system-uuid", strategy = "uuid")
  private String busId;
  private String busNum;
  private int busSeat;
  private String busType;
  @OneToMany(mappedBy = "bus")
  private List<BusTimeEntity> busTimes;
  @OneToMany(mappedBy = "bus")
  private List<RouteEntity> route;
}
