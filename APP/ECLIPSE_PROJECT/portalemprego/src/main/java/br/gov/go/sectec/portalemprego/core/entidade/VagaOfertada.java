package br.gov.go.sectec.portalemprego.core.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * <p>
 * <b>Title:</b> VagaOfertada.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Entidade de negocios para Vaga Ofertada
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
@Entity
@Table(name = "adm_vaga_ofertada")
public class VagaOfertada implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_vaga_ofertada", unique = true, nullable = false)
	private Long idVagaOfertada;

	@Column(name = "ds_vaga_ofertada", nullable = false, length = 100)
	private String dsVagaOfertada;

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_expiracao_vaga")
	private Date dtExpiracaoVaga;

	@Column(name = "nu_vaga_ofertada", nullable = false)
	private Long nuVagaOfertada;

	@ManyToOne
	@JoinColumn(name = "id_cidade")
	private Cidade cidade;

	@ManyToOne
	@JoinColumn(name = "id_pessoa_juridica")
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "id_area_interesse")
	private AreaInteresse areaInteresse;

	public VagaOfertada() {

	}

	public Long getIdVagaOfertada() {

		return idVagaOfertada;
	}

	public void setIdVagaOfertada(Long idVagaOfertada) {

		this.idVagaOfertada = idVagaOfertada;
	}

	public String getDsVagaOfertada() {

		return this.dsVagaOfertada;
	}

	public void setDsVagaOfertada(final String dsVagaOfertada) {

		this.dsVagaOfertada = dsVagaOfertada;
	}

	public Date getDtExpiracaoVaga() {

		return this.dtExpiracaoVaga;
	}

	public void setDtExpiracaoVaga(final Date dtExpiracaoVaga) {

		this.dtExpiracaoVaga = dtExpiracaoVaga;
	}

	public Long getNuVagaOfertada() {

		return this.nuVagaOfertada;
	}

	public void setNuVagaOfertada(final Long nuVagaOfertada) {

		this.nuVagaOfertada = nuVagaOfertada;
	}

	public Cidade getCidade() {

		return this.cidade;
	}

	public void setCidade(final Cidade cidade) {

		this.cidade = cidade;
	}

	public Empresa getEmpresa() {

		return empresa;
	}

	public void setEmpresa(Empresa empresa) {

		this.empresa = empresa;
	}

	public AreaInteresse getAreaInteresse() {

		return areaInteresse;
	}

	public void setAreaInteresse(AreaInteresse areaInteresse) {

		this.areaInteresse = areaInteresse;
	}

}
