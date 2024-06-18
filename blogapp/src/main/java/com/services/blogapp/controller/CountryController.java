package com.services.blogapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.services.blogapp.dto.CountryDto;
import com.services.blogapp.exception.NotFoundException;
import com.services.blogapp.model.Country;
import com.services.blogapp.service.CountryService;

@RestController
@RequestMapping("/country")
public class CountryController {

	@Autowired
	CountryService countryService;

	@GetMapping("/findById")
	public Country findById(@RequestParam int id) throws NotFoundException {
		return countryService.findById(id);

	}

	@PostMapping("/save")
	public Country save(@RequestBody CountryDto countryDto) throws NotFoundException {
		return countryService.save(countryDto);
	}

	@PutMapping("/update")
	public Country update(@RequestBody Country country) throws NotFoundException {
		return countryService.update(country);

	}

	@DeleteMapping("/delete")
	private void delete(@RequestBody Country country) throws NotFoundException {
		countryService.delete(country);
	}

	@GetMapping("/findAll")
	private List<Country> findAll() {
		return countryService.findAll();
	}

}
