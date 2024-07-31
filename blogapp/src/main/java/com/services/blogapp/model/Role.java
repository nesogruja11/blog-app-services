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
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "gen_role_seq")
	@SequenceGenerator(name = "gen_role_seq", allocationSize = 1, sequenceName = "gen_role_seq")
	private int roleId;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "preview_name", nullable = true)
	private String previewName;
}