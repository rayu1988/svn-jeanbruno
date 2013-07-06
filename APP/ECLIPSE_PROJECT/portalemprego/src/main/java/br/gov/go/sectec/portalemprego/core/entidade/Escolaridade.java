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
 * <b>Title:</b> Escolaridade.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Entidade de negocios para Escolaridade
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
@Entity(name = "acd_escolaridade")
public class Escolaridade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_escolaridade")
	private Long idEscolaridade;

	@Column(name = "ds_escolaridade")
	private String dsEscolaridade;

	@OneToMany(mappedBy = "escolaridade", fetch = FetchType.LAZY)
	private List<Aluno> alunos;

	
	public Long getIdEscolaridade() {
	
		return idEscolaridade;
	}

	
	public void setIdEscolaridade(Long idEscolaridade) {
	
		this.idEscolaridade = idEscolaridade;
	}

	public String getDsEscolaridade() {

		return dsEscolaridade;
	}

	public void setDsEscolaridade(String dsEscolaridade) {

		this.dsEscolaridade = dsEscolaridade;
	}

	public List<Aluno> getAlunos() {

		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {

		this.alunos = alunos;
	}

}
