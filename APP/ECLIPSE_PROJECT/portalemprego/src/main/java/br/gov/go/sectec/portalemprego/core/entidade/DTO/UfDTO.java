package br.gov.go.sectec.portalemprego.core.entidade.DTO;

import java.io.Serializable;


/**
 * <p>
 * <b>Title:</b> PaisDTO.java
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
public class UfDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long idDTO;
	
	private String siglaDTO;

	
	public Long getIdDTO() {
	
		return idDTO;
	}

	
	public void setIdDTO(Long idDTO) {
	
		this.idDTO = idDTO;
	}


	
	public String getSiglaDTO() {
	
		return siglaDTO;
	}


	
	public void setSiglaDTO(String siglaDTO) {
	
		this.siglaDTO = siglaDTO;
	}
	
}
