package com.example.kumoh_school_bus.bus;

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
@Table(name = "route")
public class RouteEntity {
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name="system-uuid", strategy = "uuid")
  private String routeId;
  private int routeNum;
  private String routeTime;
  @ManyToOne
  @JoinColumn(name="busId")
  private BusEntity bus;
  @ManyToOne
  @JoinColumn(name="stationId")
  private StationEntity station;
}
