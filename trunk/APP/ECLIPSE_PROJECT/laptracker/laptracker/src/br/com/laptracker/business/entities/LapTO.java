package br.com.laptracker.business.entities;

import br.com.laptracker.commons.enums.DistanceUnit;

@SuppressWarnings("serial")
public class LapTO extends TransferObject {

	private Long 				id;
	private String 				identifier;
	private String 				format;
	private String				time;
	
	private String				breakTime;

	private DistanceUnit		distanceUnit;
	private String				distanceValue;
	
	// GETTERS AND SETTERS //
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public DistanceUnit getDistanceUnit() {
		return distanceUnit;
	}
	public void setDistanceUnit(DistanceUnit distanceUnit) {
		this.distanceUnit = distanceUnit;
	}
	public String getDistanceValue() {
		return distanceValue;
	}
	public void setDistanceValue(String distanceValue) {
		this.distanceValue = distanceValue;
	}
	public String getBreakTime() {
		return breakTime;
	}
	public void setBreakTime(String breakTime) {
		this.breakTime = breakTime;
	}
}
