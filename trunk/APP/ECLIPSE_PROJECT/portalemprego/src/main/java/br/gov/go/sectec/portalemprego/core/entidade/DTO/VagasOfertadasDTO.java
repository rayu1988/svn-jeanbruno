package br.gov.go.sectec.portalemprego.core.entidade.DTO;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <b>Title:</b> VagasOfertadasDTO.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Descrição vagas ofertas
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
public class VagasOfertadasDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idDTO;
	
	private Long idArea;
	
	private Long idCidade;

	private String dsCidade;

	private String dsArea;

	private Long qtdVagas;

	private Date dataExpiracao;
	
	private String dataExpiracaoStr;
	
	private Integer index;
	
	private String dsVagaOfertada;

	public Long getIdDTO() {

		return this.idDTO;
	}

	public void setIdDTO(final Long idDTO) {

		this.idDTO = idDTO;
	}

	public String getDsCidade() {

		return this.dsCidade;
	}

	public void setDsCidade(final String dsCidade) {

		this.dsCidade = dsCidade;
	}

	public String getDsArea() {

		return this.dsArea;
	}

	public void setDsArea(final String dsArea) {

		this.dsArea = dsArea;
	}

	public Long getQtdVagas() {
	
		return qtdVagas;
	}

	
	public void setQtdVagas(Long qtdVagas) {
	
		this.qtdVagas = qtdVagas;
	}

	public Date getDataExpiracao() {

		return this.dataExpiracao;
	}

	public void setDataExpiracao(final Date dataExpiracao) {

		this.dataExpiracao = dataExpiracao;
	}

	
	public Long getIdArea() {
	
		return idArea;
	}

	
	public void setIdArea(Long idArea) {
	
		this.idArea = idArea;
	}

	
	public Long getIdCidade() {
	
		return idCidade;
	}

	
	public void setIdCidade(Long idCidade) {
	
		this.idCidade = idCidade;
	}

	
	public String getDsVagaOfertada() {
	
		return dsVagaOfertada;
	}

	
	public void setDsVagaOfertada(String dsVagaOfertada) {
	
		this.dsVagaOfertada = dsVagaOfertada;
	}

	
	public Integer getIndex() {
	
		return index;
	}

	
	public void setIndex(Integer index) {
	
		this.index = index;
	}

	
	public String getDataExpiracaoStr() {
	
		return dataExpiracaoStr;
	}

	
	public void setDataExpiracaoStr(String dataExpiracaoStr) {
	
		this.dataExpiracaoStr = dataExpiracaoStr;
	}

}
