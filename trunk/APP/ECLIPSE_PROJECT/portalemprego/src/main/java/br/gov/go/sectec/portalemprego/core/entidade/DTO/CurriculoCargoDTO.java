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
public class CurriculoCargoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idCargo;

	private String cargo;

	private Long total;

	public Long getIdCargo() {

		return this.idCargo;
	}

	public void setIdCargo(final Long idCargo) {

		this.idCargo = idCargo;
	}

	public String getCargo() {

		return this.cargo;
	}

	public void setCargo(final String cargo) {

		this.cargo = cargo;
	}

	public Long getTotal() {

		return this.total;
	}

	public void setTotal(final Long total) {

		this.total = total;
	}
}
