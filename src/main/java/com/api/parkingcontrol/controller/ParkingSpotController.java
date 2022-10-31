package com.api.parkingcontrol.controller;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.model.ParkingSpotModel;
import com.api.parkingcontrol.service.ParkingSpotService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.model.ParkingSpotModel;
import com.api.parkingcontrol.service.ParkingSpotService;



@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

	final ParkingSpotService parkingSpotService;

	public ParkingSpotController(ParkingSpotService parkingSpotService) {
		this.parkingSpotService = parkingSpotService;
	}

	@PostMapping
	public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotModel parkingSpotModelDto) {
		if (parkingSpotService.existsByLicensePlateCar(parkingSpotModelDto.getLicensePlateCar())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
		}
		if (parkingSpotService.existsByParkingSpotNumber(parkingSpotModelDto.getParkingSpotNumber())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use!");
		}
		if (parkingSpotService.existsByApartmentAndBlock(parkingSpotModelDto.getApartment(),
				parkingSpotModelDto.getBlock())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Conflict: Parking Spot already registered for this apartment/block!");
		}

		var parkingSpotModel = new ParkingSpotModel();
		BeanUtils.copyProperties(parkingSpotModelDto, parkingSpotModel);
		parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
	}

	 @GetMapping
	    public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpots(@PageableDefault(page = 0,size = 10,sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
	        return  ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));
	    }
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getOneParkingspot(@PathVariable(value = "id") Long id){
		Optional<ParkingSpotModel> ParkingSpotModelOptional = parkingSpotService.findbyid(id);
		if(!ParkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(ParkingSpotModelOptional.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteParkingspot(@PathVariable(value = "id") Long id){
		Optional<ParkingSpotModel> ParkingSpotModelOptional = parkingSpotService.findbyid(id);
		if(!ParkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
		}
		parkingSpotService.delete(ParkingSpotModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully.");
	}
	
	   
	   
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") Long id, @RequestBody @Valid ParkingSpotDto parkingSpotDto){
		Optional<ParkingSpotModel> ParkingSpotModelOptional = parkingSpotService.findbyid(id);
		if(!ParkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
		}
		
	    
	    var parkingSpotModel = new ParkingSpotModel();
		BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
		parkingSpotModel.setId(ParkingSpotModelOptional.get().getId());
		parkingSpotModel.setRegistrationDate(ParkingSpotModelOptional.get().getRegistrationDate());
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
		
	}
	
}
