package br.gov.go.sectec.portalemprego.core.entidade.DTO;

import java.io.Serializable;

/**
 * <p>
 * <b>Title:</b> TelefoneDTO.java
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
public class TelefoneDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idDTO;

	private Integer numeroDTO;

	private Integer nuDdd;

	private Integer nuDdi;

	private String dsTipo;

	private Long idTipo;

	private Integer index;

	public Long getIdDTO() {

		return this.idDTO;
	}

	public void setIdDTO(final Long idDTO) {

		this.idDTO = idDTO;
	}

	public Integer getNumeroDTO() {

		return this.numeroDTO;
	}

	public void setNumeroDTO(final Integer numeroDTO) {

		this.numeroDTO = numeroDTO;
	}

	public Integer getNuDdd() {

		return this.nuDdd;
	}

	public void setNuDdd(final Integer nuDdd) {

		this.nuDdd = nuDdd;
	}

	public Integer getNuDdi() {

		return this.nuDdi;
	}

	public void setNuDdi(final Integer nuDdi) {

		this.nuDdi = nuDdi;
	}

	public String getDsTipo() {

		return this.dsTipo;
	}

	public void setDsTipo(final String dsTipo) {

		this.dsTipo = dsTipo;
	}

	public Long getIdTipo() {

		return this.idTipo;
	}

	public void setIdTipo(final Long idTipo) {

		this.idTipo = idTipo;
	}

	public Integer getIndex() {

		return this.index;
	}

	public void setIndex(final Integer index) {

		this.index = index;
	}

}
