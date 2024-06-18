package com.services.blogapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "country")
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "gen_country_seq")
	@SequenceGenerator(name = "gen_country_seq", allocationSize = 1, sequenceName = "gen_country_seq")
	private int countryId;

	@Column(name = "country_code", nullable = false, unique = true)
	private String countryCode;

	@Column(name = "country_name", nullable = false)
	private String countryName;
}
