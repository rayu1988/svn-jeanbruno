package br.com.barganhas.commons;

import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.entities.StateTO;

public class SearchingRequest {

	public enum SearchOrdering {
		MOST_RELEVANT,
		LOWER_PRICE,
		HIGHER_PRICE
	}
	
	private String				text;
	private StateTO				state;
	private CategoryTO			category;
	private Double				filterCurrencyFrom;
	private Double				filterCurrencyUpTo;
	private SearchOrdering		searchOrdering;
	
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
	public SearchOrdering getSearchOrdering() {
		return searchOrdering;
	}
	public void setSearchOrdering(SearchOrdering searchOrdering) {
		this.searchOrdering = searchOrdering;
	}
}
