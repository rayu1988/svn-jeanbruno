package br.gov.go.sectec.portalemprego.core.entidade.DTO;

import java.io.Serializable;

/**
 * <p>
 * <b>Title:</b> EscolaridadeDTO.java
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
 * @author Joffre
 * 
 * @version 1.0.0
 */
public class EscolaridadeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idDTO;

	private String nomeDTO;

	public Long getIdDTO() {

		return idDTO;
	}

	public void setIdDTO(Long idDTO) {

		this.idDTO = idDTO;
	}

	public String getNomeDTO() {

		return nomeDTO;
	}

	public void setNomeDTO(String nomeDTO) {

		this.nomeDTO = nomeDTO;
	}

}
