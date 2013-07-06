package br.gov.go.sectec.portalemprego.core.entidade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 
 * <p>
 * <b>Title:</b> Uf.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Entidade de negocios para Unidades Federativas
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
@Entity(name = "adm_uf")
public class Uf implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_uf")
	private Long idUf;

	@Column(name = "ds_nome")
	private String dsNome;

	@Column(name = "ds_sigla")
	private String dsSigla;

	@ManyToOne
	@JoinColumn(name = "id_pais")
	private Pais pais;

	
	public Long getIdUf() {
	
		return idUf;
	}

	
	public void setIdUf(Long idUf) {
	
		this.idUf = idUf;
	}

	
	public String getDsNome() {
	
		return dsNome;
	}

	
	public void setDsNome(String dsNome) {
	
		this.dsNome = dsNome;
	}

	
	public String getDsSigla() {
	
		return dsSigla;
	}

	
	public void setDsSigla(String dsSigla) {
	
		this.dsSigla = dsSigla;
	}

	
	public Pais getPais() {
	
		return pais;
	}

	
	public void setPais(Pais pais) {
	
		this.pais = pais;
	}

}
