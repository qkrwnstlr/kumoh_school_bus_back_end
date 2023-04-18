package com.example.kumoh_school_bus.bus;

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
  private String routeID;

  private int routeNum;
  private int routeTime;
  @ManyToOne
  @JoinColumn(name="busID")
  private BusEntity bus;
  @ManyToOne
  @JoinColumn(name="stationID")
  private StationEntity station;
}