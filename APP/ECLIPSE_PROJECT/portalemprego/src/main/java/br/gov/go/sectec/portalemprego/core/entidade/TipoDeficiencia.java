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
 * <b>Title:</b> TipoDeficiencia.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Entidade de negocios para Tipo de Deficiência
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
@Entity(name = "adm_tipo_deficiencia")
public class TipoDeficiencia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tipo_deficiencia")
	private Long idTipoDeficiencia;

	@Column(name = "ds_tipo_deficiencia")
	private String dsTipoDeficiencia;

	@OneToMany(mappedBy = "tipoDeficiencia", fetch = FetchType.LAZY)
	private List<Aluno> alunos;


	
	public Long getIdTipoDeficiencia() {
	
		return idTipoDeficiencia;
	}

	
	public void setIdTipoDeficiencia(Long idTipoDeficiencia) {
	
		this.idTipoDeficiencia = idTipoDeficiencia;
	}

	public String getDsTipoDeficiencia() {

		return dsTipoDeficiencia;
	}

	public void setDsTipoDeficiencia(String dsTipoDeficiencia) {

		this.dsTipoDeficiencia = dsTipoDeficiencia;
	}

	public List<Aluno> getAlunos() {

		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {

		this.alunos = alunos;
	}

}
