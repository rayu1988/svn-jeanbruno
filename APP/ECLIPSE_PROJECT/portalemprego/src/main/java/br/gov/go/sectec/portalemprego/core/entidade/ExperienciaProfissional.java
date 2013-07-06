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

/**
 * 
 * <p>
 * <b>Title:</b> ExperienciaProfissional.java
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
@Entity(name = "adm_experiencia_profissional")
public class ExperienciaProfissional implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_experiencia_profissional")
	private Long idExperienciaProfissional;

	@Column(name = "ds_cargo")
	private String dsCargo;

	@Column(name = "ds_empresa")
	private String dsEmpresa;

	@Column(name = "ds_funcao")
	private String dsFuncao;

	@Column(name = "dt_data_entrada")
	private Date dtDataEntrada;

	@Column(name = "dt_data_saida")
	private Date dtDataSaida;

	@ManyToOne()
	@JoinColumn(name = "id_curriculo")
	private Curriculo curriculo;

	public Long getIdExperienciaProfissional() {

		return this.idExperienciaProfissional;
	}

	public void setIdExperienciaProfissional(final Long idExperienciaProfissional) {

		this.idExperienciaProfissional = idExperienciaProfissional;
	}

	public String getDsCargo() {

		return this.dsCargo;
	}

	public void setDsCargo(final String dsCargo) {

		this.dsCargo = dsCargo;
	}

	public String getDsEmpresa() {

		return this.dsEmpresa;
	}

	public void setDsEmpresa(final String dsEmpresa) {

		this.dsEmpresa = dsEmpresa;
	}

	public String getDsFuncao() {

		return this.dsFuncao;
	}

	public void setDsFuncao(final String dsFuncao) {

		this.dsFuncao = dsFuncao;
	}

	public Date getDtDataEntrada() {

		return this.dtDataEntrada;
	}

	public void setDtDataEntrada(final Date dtDataEntrada) {

		this.dtDataEntrada = dtDataEntrada;
	}

	public Date getDtDataSaida() {

		return this.dtDataSaida;
	}

	public void setDtDataSaida(final Date dtDataSaida) {

		this.dtDataSaida = dtDataSaida;
	}

	public Curriculo getCurriculo() {

		return this.curriculo;
	}

	public void setCurriculo(final Curriculo curriculo) {

		this.curriculo = curriculo;
	}

}
