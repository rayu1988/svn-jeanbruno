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
 * <b>Title:</b> Cidade.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Entidade de negocios para Cidade
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
@Entity(name = "adm_cidade")
public class Cidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cidade")
	private Long idCidade;

	@Column(name="ds_nome")
	private String dsNome;

	@ManyToOne
	@JoinColumn(name="id_uf")
	private Uf uf;


	
	public Long getIdCidade() {
	
		return idCidade;
	}

	
	public void setIdCidade(Long idCidade) {
	
		this.idCidade = idCidade;
	}

	
	public String getDsNome() {
	
		return dsNome;
	}

	
	public void setDsNome(String dsNome) {
	
		this.dsNome = dsNome;
	}

	
	public Uf getUf() {
	
		return uf;
	}

	
	public void setUf(Uf uf) {
	
		this.uf = uf;
	}
	
}
