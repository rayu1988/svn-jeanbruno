package br.com.barganhas.commons;

import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.entities.StateTO;

public class SearchingRequest {

	private String				text;
	private StateTO				state;
	private CategoryTO			category;
	private Double				filterCurrencyFrom;
	private Double				filterCurrencyUpTo;
	
	// GETTERS AND SETTERS //
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public StateTO getState() {
		return state;
	}
	public void setState(StateTO state) {
		this.state = state;
	}
	public CategoryTO getCategory() {
		return category;
	}
	public void setCategory(CategoryTO category) {
		this.category = category;
	}
	public Double getFilterCurrencyFrom() {
		return filterCurrencyFrom;
	}
	public Double getFilterCurrencyUpTo() {
		return filterCurrencyUpTo;
	}
	public void setFilterCurrencyFrom(Double filterCurrencyFrom) {
		this.filterCurrencyFrom = filterCurrencyFrom;
	}
	public void setFilterCurrencyUpTo(Double filterCurrencyUpTo) {
		this.filterCurrencyUpTo = filterCurrencyUpTo;
	}
}
