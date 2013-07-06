package br.gov.go.sectec.portalemprego.core.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the adm_controle_emprego_efetivo database table.
 * 
 */
@Embeddable
public class ControleEmpregoEfetivoPK implements Serializable {

	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer idControleEmpregoEfetivo;

	private Long idPessoaJuridica;

	public ControleEmpregoEfetivoPK() {

	}

	@Column(name = "id_controle_emprego_efetivo", unique = true, nullable = false)
	public Integer getIdControleEmpregoEfetivo() {

		return this.idControleEmpregoEfetivo;
	}

	public void setIdControleEmpregoEfetivo(final Integer idControleEmpregoEfetivo) {

		this.idControleEmpregoEfetivo = idControleEmpregoEfetivo;
	}

	@Column(name = "id_pessoa_juridica", unique = true, nullable = false)
	public Long getIdPessoaJuridica() {

		return this.idPessoaJuridica;
	}

	public void setIdPessoaJuridica(final Long idPessoaJuridica) {

		this.idPessoaJuridica = idPessoaJuridica;
	}

	public boolean equals(final Object other) {

		if (this == other) {
			return true;
		}
		if (!( other instanceof ControleEmpregoEfetivoPK )) {
			return false;
		}
		final ControleEmpregoEfetivoPK castOther = (ControleEmpregoEfetivoPK) other;
		return this.idControleEmpregoEfetivo.equals(castOther.idControleEmpregoEfetivo) && this.idPessoaJuridica.equals(castOther.idPessoaJuridica);
	}

	public int hashCode() {

		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idControleEmpregoEfetivo.hashCode();
		hash = hash * prime + this.idPessoaJuridica.hashCode();

		return hash;
	}
}
