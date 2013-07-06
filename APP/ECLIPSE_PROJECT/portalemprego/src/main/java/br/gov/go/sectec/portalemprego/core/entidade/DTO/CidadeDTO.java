package br.gov.go.sectec.portalemprego.core.entidade.DTO;

import java.io.Serializable;

/**
 * <p>
 * <b>Title:</b> CidadeDTO.java
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
public class CidadeDTO implements Serializable {

	
	
	
	
	
	
	private static final long serialVersionUID = 1L;

	private Long idDTO;

	private String nomeDTO;

	public Long getIdDTO() {

		return this.idDTO;
	}

	public void setIdDTO(final Long idDTO) {

		this.idDTO = idDTO;
	}

	public String getNomeDTO() {

		return this.nomeDTO;
	}

	public void setNomeDTO(final String nomeDTO) {

		this.nomeDTO = nomeDTO;
	}

}
