package com.example.kumoh_school_bus.reservation;

import com.example.kumoh_school_bus.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/reservation")
public class ReservationController {
  private final ReservationService reservationService;

  private ResponseEntity<ResponseDTO<String>> onError(Exception e){
    String error = e.getMessage();
    ResponseDTO<String> response = ResponseDTO.<String>builder().error(error).build();
    return ResponseEntity.badRequest().body(response);
  }
  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping("/{loginId}")
  public ResponseEntity<?> getAllReservationByMember(@PathVariable String loginId) {
    try {
      List<ReservationDTO> entities = reservationService.getAllReservationByMember(loginId);
      ResponseDTO<ReservationDTO> response = ResponseDTO.<ReservationDTO>builder().data(entities).build();
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      return onError(e);
    }
  }

  @PostMapping("/{loginId}")
  public ResponseEntity<?> addReservation(@RequestBody ReservationAddRequestDTO requestDTO, @PathVariable String loginId) {
    try {
      List<ReservationDTO> entities = reservationService.addReservation(requestDTO, loginId);
      ResponseDTO<ReservationDTO> response = ResponseDTO.<ReservationDTO>builder().data(entities).build();
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      return onError(e);
    }
  }

  @DeleteMapping("/{loginId}/{reservationId}")
  public ResponseEntity<?> removeReservation(@PathVariable String loginId, @PathVariable String reservationId) {
    try {
      List<ReservationDTO> entities = reservationService.removeReservation(reservationId, loginId);
      ResponseDTO<ReservationDTO> response = ResponseDTO.<ReservationDTO>builder().data(entities).build();
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      return onError(e);
    }
  }

  @GetMapping("/bus/{busTimeId}")
  public BusTimeReservationDTO getAllReservationByBus(@PathVariable String busTimeId) {
    return reservationService.getAllReservationByBus(busTimeId);
  }

  @GetMapping("/chart")
  public ResponseEntity<?> getAllFinishedReservation() {
    try {
      List<ReservationDTO> entities = reservationService.getAllFinishedReservation();
      ResponseDTO<ReservationDTO> response = ResponseDTO.<ReservationDTO>builder().data(entities).build();
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      return onError(e);
    }
  }

  @PostMapping("/finish/{busTimeId}")
  public ResponseEntity<?> finishDriving(@PathVariable String busTimeId) {
    try {
      reservationService.finish(busTimeId);
      ResponseDTO<SelectResponseDTO> responseDTO = ResponseDTO.<SelectResponseDTO>builder().build();
      return ResponseEntity.ok().body(responseDTO);
    } catch (Exception e) {
      ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
      return ResponseEntity.badRequest().body(responseDTO);
    }
  }
}
