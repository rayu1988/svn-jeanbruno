package br.com.barganhas.business.entities;

import br.com.barganhas.business.entities.annotations.IdField;
import br.com.barganhas.business.entities.annotations.PropertyField;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

@SuppressWarnings("serial")
public class AboutSiteTO extends TransferObject {

	@IdField
	@PropertyField
	private Long			id;
	
	@PropertyField(notNull=true, allowEmpty=false)
	private String			title;
	
	@PropertyField(notNull=true, allowEmpty=false)
	private Text			text;
	
	@PropertyField(notNull=true, allowEmpty=false)
	private Boolean			defaultAboutSite;
	
	public AboutSiteTO() {
		super(null);
	}

	public AboutSiteTO(Key key) {
		super(key);
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public Long getId() {
		return id;
	}
	
	// GETTERS AND SETTERS //
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public Text getText() {
		return text;
	}
	
	public String getStringText() {
		return text != null ? text.getValue() : "";
	}

	public void setText(Text text) {
		this.text = text;
	}
	
	public void setStringText(String value) {
		this.text = new Text(value);
	}

	public Boolean getDefaultAboutSite() {
		return defaultAboutSite;
	}

	public void setDefaultAboutSite(Boolean defaultAboutSite) {
		this.defaultAboutSite = defaultAboutSite;
	}

}
