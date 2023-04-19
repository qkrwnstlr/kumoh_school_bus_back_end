package com.example.kumoh_school_bus.station;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StationService {
  private final StationRepository stationRepository;
  public StationService(StationRepository stationRepository) {
    this.stationRepository = stationRepository;
  }

  public List<StationEntity> getAll() {
    return stationRepository.findAll();
  }
}
