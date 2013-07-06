package br.gov.go.sectec.portalemprego.core.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the adm_tipo_telefone database table.
 * 
 */
@Entity
@Table(name = "adm_tipo_telefone")
public class TipoTelefone implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_tipo_telefone", unique = true, nullable = false)
	private Long idTipoTelefone;

	@Column(name = "ds_tipo_telefone", length = 50)
	private String dsTipoTelefone;

	public TipoTelefone() {

	}

	public Long getIdTipoTelefone() {

		return this.idTipoTelefone;
	}

	public void setIdTipoTelefone(final Long idTipoTelefone) {

		this.idTipoTelefone = idTipoTelefone;
	}

	public String getDsTipoTelefone() {

		return this.dsTipoTelefone;
	}

	public void setDsTipoTelefone(final String dsTipoTelefone) {

		this.dsTipoTelefone = dsTipoTelefone;
	}

	/*
	 * //bi-directional many-to-one association to Telefone
	 * 
	 * @OneToMany(mappedBy="tipoTelefone") public List<Telefone> getTelefones() { return
	 * this.telefones; }
	 * 
	 * public void setTelefones(List<Telefone> telefones) { this.telefones = telefones; }
	 * 
	 * public Telefone addTelefone(Telefone telefone) { getTelefones().add(telefone);
	 * telefone.setTipoTelefone(this);
	 * 
	 * return telefone; }
	 * 
	 * public Telefone removeTelefone(Telefone telefone) { getTelefones().remove(telefone);
	 * telefone.setTipoTelefone(null);
	 * 
	 * return telefone; }
	 */

}
