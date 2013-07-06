package br.gov.go.sectec.portalemprego.core.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the adm_controle_encaminhamento database table.
 * 
 */
@Entity
@Table(name = "adm_controle_encaminhamento")
public class ControleEncaminhamento implements Serializable {

	private static final long serialVersionUID = 1L;

	private ControleEncaminhamentoPK id;

	private String dsEncaminhamentoEmprego;

	private Boolean isTrabalhando;

	private Aluno aluno;

	//private PessoaJuridica pessoaJuridica;

	public ControleEncaminhamento() {

	}

	@EmbeddedId
	public ControleEncaminhamentoPK getId() {

		return this.id;
	}

	public void setId(final ControleEncaminhamentoPK id) {

		this.id = id;
	}

	@Column(name = "ds_encaminhamento_emprego", length = 4000)
	public String getDsEncaminhamentoEmprego() {

		return this.dsEncaminhamentoEmprego;
	}

	public void setDsEncaminhamentoEmprego(final String dsEncaminhamentoEmprego) {

		this.dsEncaminhamentoEmprego = dsEncaminhamentoEmprego;
	}

	@Column(name = "is_trabalhando")
	public Boolean getIsTrabalhando() {

		return this.isTrabalhando;
	}

	public void setIsTrabalhando(final Boolean isTrabalhando) {

		this.isTrabalhando = isTrabalhando;
	}

	// bi-directional many-to-one association to Aluno
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_aluno", nullable = false, insertable = false, updatable = false)
	public Aluno getAluno() {

		return this.aluno;
	}

	public void setAluno(final Aluno aluno) {

		this.aluno = aluno;
	}

}
