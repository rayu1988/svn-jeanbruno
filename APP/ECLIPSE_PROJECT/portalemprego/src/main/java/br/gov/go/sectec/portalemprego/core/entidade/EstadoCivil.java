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
 * <b>Title:</b> EstadoCivil.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Entidade de negocios para Estado Civil
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
@Entity(name = "adm_estado_civil")
public class EstadoCivil implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_estado_civil")
	private Long idEstadoCivil;

	@Column(name = "ds_estado_civil", nullable = false, length = 30)
	private String dsEstadoCivil;

	@OneToMany(mappedBy = "estadoCivil", fetch = FetchType.LAZY)
	private List<Aluno> alunos;

	
	public Long getIdEstadoCivil() {
	
		return idEstadoCivil;
	}

	
	public void setIdEstadoCivil(Long idEstadoCivil) {
	
		this.idEstadoCivil = idEstadoCivil;
	}

	public String getDsEstadoCivil() {

		return dsEstadoCivil;
	}

	public void setDsEstadoCivil(String dsEstadoCivil) {

		this.dsEstadoCivil = dsEstadoCivil;
	}

	public List<Aluno> getAlunos() {

		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {

		this.alunos = alunos;
	}
}
