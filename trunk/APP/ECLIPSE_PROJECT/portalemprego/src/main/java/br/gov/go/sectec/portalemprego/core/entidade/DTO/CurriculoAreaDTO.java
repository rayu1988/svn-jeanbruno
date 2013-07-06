package br.gov.go.sectec.portalemprego.core.entidade.DTO;

import java.io.Serializable;


/**
 * <p>
 * <b>Title:</b> CurriculoAreaDTO.java
 * </p>
 * 
 * <p>
 * <b>Description:</b>
 * </p>	
 * 	
 * <p>	
 * <b>Company: </b> Premium Consultoria e Sistemas
 * </p>	
 * 	
 * @author Silvânio
 * 
 * @version 1.0.0
 */
public class CurriculoAreaDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long idArea;
	
	private String area;
	
	private Long total;

	
	public String getArea() {
	
		return area;
	}

	
	public void setArea(String area) {
	
		this.area = area;
	}

	public Long getTotal() {
	
		return total;
	}
	
	public void setTotal(Long total) {
	
		this.total = total;
	}


	public Long getIdArea() {
	
		return idArea;
	}


	
	public void setIdArea(Long idArea) {
	
		this.idArea = idArea;
	}
	
}
