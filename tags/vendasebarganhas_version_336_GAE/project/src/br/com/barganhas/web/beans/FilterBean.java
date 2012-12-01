package br.com.barganhas.web.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.richfaces.event.ItemChangeEvent;

@ManagedBean
@SessionScoped
@SuppressWarnings("serial")
public class FilterBean extends AppManagedBean {

	private static final String			BLANK = "blank";
	private static final String			FILLABLE = "fillable";
	
	private String						activedItem =  BLANK;
	
	public void toggleUp(ItemChangeEvent event) {
		try {
			this.activedItem = this.activedItem.equals(BLANK) ? FILLABLE : BLANK;
		} catch (Exception e) {
			this.manageException(e);
		}
	}

	// GETTERS AND SETTERS //
	public static String getBlank() {
		return BLANK;
	}
	
	public static String getFillable() {
		return FILLABLE;
	}
	
	public String getActivedItem() {
		return activedItem;
	}

	public void setActivedItem(String activedItem) {
		this.activedItem = activedItem;
	}

}
