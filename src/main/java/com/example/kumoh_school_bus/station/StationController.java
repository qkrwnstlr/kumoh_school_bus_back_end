package com.example.kumoh_school_bus.station;

import com.example.kumoh_school_bus.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/station")
public class StationController {
  private final StationService stationService;

  public StationController(StationService stationService) {
    this.stationService = stationService;
  }

  private ResponseEntity<ResponseDTO<String>> onError(Exception e){
    String error = e.getMessage();
    ResponseDTO<String> response = ResponseDTO.<String>builder().error(error).build();
    return ResponseEntity.badRequest().body(response);
  }

  @GetMapping("/")
  public ResponseEntity<?> getAllStation() {
    try {
      List<StationEntity> entities = stationService.getAll();
      ResponseDTO<StationEntity> response = ResponseDTO.<StationEntity>builder().data(entities).build();
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      return onError(e);
    }
  }
}
