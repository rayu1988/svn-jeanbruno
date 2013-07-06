package br.gov.go.sectec.portalemprego.core.entidade;

import java.io.Serializable;

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
 * <b>Title:</b> Telefone.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Entidade de negocios para telefone
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
@Entity(name = "adm_telefone")
public class Telefone implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_telefone", unique = true, nullable = false)
	private Long idTelefone;

	@Column(name = "nu_ddd")
	private Integer nuDdd;

	@Column(name = "nu_ddi")
	private Integer nuDdi;

	@Column(name = "nu_telefone")
	private Integer nuTelefone;

	@ManyToOne
	@JoinColumn(name = "id_aluno")
	private Aluno aluno;

	@ManyToOne
	@JoinColumn(name = "id_pessoa_juridica")
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "id_tipo_telefone", nullable = false)
	private TipoTelefone tipoTelefone;

	public Telefone() {

	}

	public Long getIdTelefone() {

		return this.idTelefone;
	}

	public void setIdTelefone(final Long idTelefone) {

		this.idTelefone = idTelefone;
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

	public Integer getNuTelefone() {

		return this.nuTelefone;
	}

	public void setNuTelefone(final Integer nuTelefone) {

		this.nuTelefone = nuTelefone;
	}

	public Aluno getAluno() {

		return this.aluno;
	}

	public void setAluno(final Aluno aluno) {

		this.aluno = aluno;
	}

	public TipoTelefone getTipoTelefone() {

		return this.tipoTelefone;
	}

	public void setTipoTelefone(final TipoTelefone tipoTelefone) {

		this.tipoTelefone = tipoTelefone;
	}

	public Empresa getEmpresa() {

		return empresa;
	}

	public void setEmpresa(Empresa empresa) {

		this.empresa = empresa;
	}

}
