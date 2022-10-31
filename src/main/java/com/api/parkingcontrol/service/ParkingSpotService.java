package com.api.parkingcontrol.service;


import com.api.parkingcontrol.model.ParkingSpotModel;

import com.api.parkingcontrol.repository.ParkingSpotRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingSpotService {

	final ParkingSpotRepository parkingSpotRepository;

	public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
		this.parkingSpotRepository = parkingSpotRepository;
	}
	public boolean existsByLicensePlateCar(String licensePlateCar) {

		return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
	}

	public boolean existsByParkingSpotNumber(String parkingSpotNumber) {

		return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
	}

	public boolean existsByApartmentAndBlock(String apartment, String block) {

		return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
	}

	@Transactional
	public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {

		return parkingSpotRepository.save(parkingSpotModel);
	}


	public Page<ParkingSpotModel> findAll(Pageable pageable) {
        return parkingSpotRepository.findAll(pageable);
    }


	public Optional<ParkingSpotModel> findbyid(Long id) {

		return parkingSpotRepository.findById(id);
	}

	@Transactional
	public void delete(ParkingSpotModel parkingSpotModel) {
		parkingSpotRepository.delete(parkingSpotModel);

	}

}
