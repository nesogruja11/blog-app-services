package com.services.blogapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.blogapp.dto.CountryDto;
import com.services.blogapp.exception.NotFoundException;
import com.services.blogapp.model.Country;
import com.services.blogapp.repository.CountryRepository;

@Service
public class CountryService {
	@Autowired
	CountryRepository countryRepository;

	public Country findById(int id) throws NotFoundException {
		return countryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nije pronađena država sa id-em:" + id));
	}

	public List<Country> findAll() {
		return countryRepository.findAll();
	}

	public Country save(CountryDto countryDto) throws NotFoundException {
		Country country = new Country();

		country.setCountryCode(countryDto.getCountryCode());
		country.setCountryName(countryDto.getCountryName());
		return countryRepository.save(country);
	}

	public Country update(Country country) throws NotFoundException {
		if (countryRepository.existsById(country.getCountryId())) {
			return countryRepository.save(country);

		}
		throw new NotFoundException("Nije pronađena država sa id-em:" + country.getCountryId());

	}

	public void delete(Country country) throws NotFoundException {
		if (countryRepository.existsById(country.getCountryId())) {
			countryRepository.delete(country);
		} else {
			throw new NotFoundException("Nije pronađena država sa id-em:" + country.getCountryId());
		}
	}

}
