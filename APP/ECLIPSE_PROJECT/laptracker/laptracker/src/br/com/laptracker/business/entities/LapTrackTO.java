package br.com.laptracker.business.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.laptracker.commons.enums.DistanceUnit;
import br.com.laptracker.commons.enums.LapType;

@SuppressWarnings("serial")
public class LapTrackTO extends TransferObject {
	
	private Long 				id;
	private LapType				lapType;
	private Date				date;
	
	private DistanceUnit		distanceUnit;
	private String				distanceValue;
	
	private String 				totalTimeFormat;
	private String 				totalTimeValue;
	
	private DistanceUnit		distanceDivisorUnit;
	private String				distanceDivisorValue;
	
	private String				breakTime;
	
	private List<LapTO>			listLaps;
	
	// GETTERS AND SETTERS //
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LapType getLapType() {
		return lapType;
	}
	public void setLapType(LapType lapType) {
		this.lapType = lapType;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<LapTO> getListLaps() {
		if (listLaps == null) {
			listLaps = new ArrayList<LapTO>();
		}
		return listLaps;
	}
	public void setListLaps(List<LapTO> listLaps) {
		this.listLaps = listLaps;
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
	public String getTotalTimeValue() {
		return totalTimeValue;
	}
	public void setTotalTimeValue(String totalTimeValue) {
		this.totalTimeValue = totalTimeValue;
	}
	public String getTotalTimeFormat() {
		return totalTimeFormat;
	}
	public void setTotalTimeFormat(String totalTimeFormat) {
		this.totalTimeFormat = totalTimeFormat;
	}
	public DistanceUnit getDistanceDivisorUnit() {
		return distanceDivisorUnit;
	}
	public void setDistanceDivisorUnit(DistanceUnit distanceDivisorUnit) {
		this.distanceDivisorUnit = distanceDivisorUnit;
	}
	public String getDistanceDivisorValue() {
		return distanceDivisorValue;
	}
	public void setDistanceDivisorValue(String distanceDivisorValue) {
		this.distanceDivisorValue = distanceDivisorValue;
	}
	public String getBreakTime() {
		return breakTime;
	}
	public void setBreakTime(String breakTime) {
		this.breakTime = breakTime;
	}
	
}
