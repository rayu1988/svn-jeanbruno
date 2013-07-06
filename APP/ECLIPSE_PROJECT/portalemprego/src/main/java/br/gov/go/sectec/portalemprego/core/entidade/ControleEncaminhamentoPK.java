package br.gov.go.sectec.portalemprego.core.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the adm_controle_encaminhamento database table.
 * 
 */
@Embeddable
public class ControleEncaminhamentoPK implements Serializable {

	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer idControleEncaminhamento;

	private Long idAluno;

	public ControleEncaminhamentoPK() {

	}

	@Column(name = "id_controle_encaminhamento", unique = true, nullable = false)
	public Integer getIdControleEncaminhamento() {

		return this.idControleEncaminhamento;
	}

	public void setIdControleEncaminhamento(final Integer idControleEncaminhamento) {

		this.idControleEncaminhamento = idControleEncaminhamento;
	}

	@Column(name = "id_aluno", unique = true, nullable = false)
	public Long getIdAluno() {

		return this.idAluno;
	}

	public void setIdAluno(final Long idAluno) {

		this.idAluno = idAluno;
	}

	public boolean equals(final Object other) {

		if (this == other) {
			return true;
		}
		if (!( other instanceof ControleEncaminhamentoPK )) {
			return false;
		}
		final ControleEncaminhamentoPK castOther = (ControleEncaminhamentoPK) other;
		return this.idControleEncaminhamento.equals(castOther.idControleEncaminhamento) && this.idAluno.equals(castOther.idAluno);
	}

	public int hashCode() {

		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idControleEncaminhamento.hashCode();
		hash = hash * prime + this.idAluno.hashCode();

		return hash;
	}
}
