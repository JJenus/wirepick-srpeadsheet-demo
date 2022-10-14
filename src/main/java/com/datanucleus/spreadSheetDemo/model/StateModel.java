package com.datanucleus.spreadSheetDemo.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class StateModel {
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long stateId;
	private String stateName;
	@Column(columnDefinition = "text")
	private String description;
	private String countryId;	
	private String countryName;
	private String regionId;
	
	public StateModel(Long stateId, String stateName, String description,
                      String countryId, String countryName, String regionId) {
		this.stateId = stateId;
		this.stateName = stateName;
		this.description = description;
		this.countryId = countryId;
		this.countryName = countryName;
		this.regionId = regionId;
	}
	
	public String toString(){
		return "{stateId:" + stateId + ", stateName:" + stateName + ", description:" + description
			+ ", countryId:" + countryId + ", countryName:" + countryName + ", regionId:" + regionId + "}";
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
