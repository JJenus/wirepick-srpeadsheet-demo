package com.datanucleus.spreadSheetDemo.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Region {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long regionId;
	private String regionName;
	private String countryId;
	@Column(columnDefinition = "text")
	private String description;
	private String countryName;


	
	
	public Region(Long regionId, String regionName, String countryId, String description, String countryName) {
		
		this.regionId = regionId;
		this.regionName= regionName;
		this.countryId = countryId;
		this.description = description;
		this.countryName = countryName;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	
}
