package com.datanucleus.spreadSheetDemo.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class CityModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cityId;
	private String cityName;
	@Column(columnDefinition = "text")
	private String description;
	private String stateId;
	private String stateName;
	private String regionId;
	private String countryId;	
	private String countryName;
	
	public CityModel(Long cityId, String cityName, String description, String stateId, String stateName,
                     String regionId, String countryId, String countryName) {
		this.cityId = cityId;
		this.cityName = cityName;
		this.description = description;
		this.stateId = stateId;
		this.stateName = stateName;
		this.regionId = regionId;
		this.countryId = countryId;
		this.countryName = countryName;
	}

	public String toString(){
		return "{cityId:" + cityId + ", cityName:" + cityName + ", description:" + description
				 + ", stateId:" + stateId + ", stateName:" + stateName + ", regionId:" + regionId
				 + ", countryId:" + countryId + ", countryName:" + countryName + "}";
	}
	
	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateid) {
		this.stateId = stateid;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	
	
}
