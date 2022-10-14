package com.datanucleus.spreadSheetDemo.model;

import javax.persistence.*;

@Entity
public class CountryModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String countryName;
	@Column(columnDefinition = "text")
	private String description;
	private String currency;
	private String currencySymbol;
	private String usExchangeRate;
	private String euExchangeRate;
	private String ukExchangeRate;

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public String getUsExchangeRate() {
		return usExchangeRate;
	}

	public void setUsExchangeRate(String usExchangeRate) {
		this.usExchangeRate = usExchangeRate;
	}

	public String getEuExchangeRate() {
		return euExchangeRate;
	}

	public void setEuExchangeRate(String euExchangeRate) {
		this.euExchangeRate = euExchangeRate;
	}

	public String getUkExchangeRate() {
		return ukExchangeRate;
	}

	public void setUkExchangeRate(String ukExchangeRate) {
		this.ukExchangeRate = ukExchangeRate;
	}

	@Override
	public String toString() {
		return String.format(
				"Country name:%s\nDescription: %s\nCurrency: %s\nCurrency symbol: %s\nusExhangeRate: %s\neuExchangeRate: %s\nukExchangeRate: %s",
				 countryName, description, currency, currencySymbol, usExchangeRate, euExchangeRate, ukExchangeRate
		);
	}
}
