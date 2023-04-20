package com.example.kumoh_school_bus.bus;

import com.example.kumoh_school_bus.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/bus")
public class BusController {
  private final BusService busService;
  BusController(BusService busService) {
    this.busService = busService;
  }
  @PostMapping("/")
  public ResponseEntity<?> getSearchBus(@RequestBody SearchRequestDTO requestDTO) {
    try {
      SearchResponseDTO searchResponseDTO = busService.getSearchBus(requestDTO);
      return ResponseEntity.ok().body(searchResponseDTO);
    } catch (Exception e) {
      ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
      return ResponseEntity.badRequest().body(responseDTO);
    }
  }

  @GetMapping("/select")
  public ResponseEntity<?> getSelectResponseDTO() {
    try {
      List<SelectResponseDTO> selectResponseDTOList = busService.getSelectResponseDTO();
      ResponseDTO<SelectResponseDTO> responseDTO = ResponseDTO.<SelectResponseDTO>builder().data(selectResponseDTOList).build();
      return ResponseEntity.ok().body(responseDTO);
    } catch (Exception e) {
      ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
      return ResponseEntity.badRequest().body(responseDTO);
    }
  }
}
