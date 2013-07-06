package br.gov.go.sectec.portalemprego.core.entidade.DTO;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <b>Title:</b> ExperienciaProfissionalDTO.java
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
public class ExperienciaProfissionalDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idDTO;

	private String nomeDTO;
	
	private String dsCargo;

	private String dsEmpresaDTO;

	private String dsFuncaoDTO;

	private Date dtDataEntradaDTO;

	private Date dtDataSaidaDTO;
	
	private String dataEntradaStr;
	
	private String dataSaidaStr;
	
	private Integer index;

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

	public String getDsEmpresaDTO() {

		return this.dsEmpresaDTO;
	}

	public void setDsEmpresaDTO(final String dsEmpresaDTO) {

		this.dsEmpresaDTO = dsEmpresaDTO;
	}

	public String getDsFuncaoDTO() {

		return this.dsFuncaoDTO;
	}

	public void setDsFuncaoDTO(final String dsFuncaoDTO) {

		this.dsFuncaoDTO = dsFuncaoDTO;
	}

	public Date getDtDataEntradaDTO() {

		return this.dtDataEntradaDTO;
	}

	public void setDtDataEntradaDTO(final Date dtDataEntradaDTO) {

		this.dtDataEntradaDTO = dtDataEntradaDTO;
	}

	public Date getDtDataSaidaDTO() {

		return this.dtDataSaidaDTO;
	}

	public void setDtDataSaidaDTO(final Date dtDataSaidaDTO) {

		this.dtDataSaidaDTO = dtDataSaidaDTO;
	}

	
	public String getDsCargo() {
	
		return dsCargo;
	}

	
	public void setDsCargo(String dsCargo) {
	
		this.dsCargo = dsCargo;
	}

	
	public String getDataEntradaStr() {
	
		return dataEntradaStr;
	}

	
	public void setDataEntradaStr(String dataEntradaStr) {
	
		this.dataEntradaStr = dataEntradaStr;
	}

	
	public String getDataSaidaStr() {
	
		return dataSaidaStr;
	}

	
	public void setDataSaidaStr(String dataSaidaStr) {
	
		this.dataSaidaStr = dataSaidaStr;
	}

	
	public Integer getIndex() {
	
		return index;
	}

	
	public void setIndex(Integer index) {
	
		this.index = index;
	}
	
}
