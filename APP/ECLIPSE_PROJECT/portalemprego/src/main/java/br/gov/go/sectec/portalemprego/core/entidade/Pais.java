package br.gov.go.sectec.portalemprego.core.entidade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * 
 * <p>
 * <b>Title:</b> Pais.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Entidade de negocios para País
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
@Entity(name = "adm_pais")
public class Pais implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pais")
	private Long idPais;

	@Column(name = "ds_nome")
	private String dsNome;

	@OneToMany(mappedBy = "pais", fetch = FetchType.LAZY)
	private List<Uf> uf;

	public Long getIdPais() {

		return this.idPais;
	}

	public void setIdPais(final Long idPais) {

		this.idPais = idPais;
	}

	public String getDsNome() {

		return this.dsNome;
	}

	public void setDsNome(final String dsNome) {

		this.dsNome = dsNome;
	}

}
