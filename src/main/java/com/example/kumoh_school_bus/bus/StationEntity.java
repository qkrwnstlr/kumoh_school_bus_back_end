package com.example.kumoh_school_bus.bus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "station")
public class StationEntity {
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name="system-uuid", strategy = "uuid")
  private String stationID;
  private String stationName;
  private double stationLat;
  private double stationLng;
}
