package br.gov.go.sectec.portalemprego.core.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * <p>
 * <b>Title:</b> TipoGrau.java
 * </p>
 * 
 * <p>
 * <b>Description:</b>
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
@Entity(name = "adm_tipo_grau")
public class TipoGrau implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_tipo_grau", unique = true, nullable = false)
	private Long idTipoGrau;

	@Column(name = "ds_tipo_grau", length = 50)
	private String dsTipoGrau;

	// private List<FormacaoAcademica> formacaoAcademicas;

	public TipoGrau() {

	}

	
	public Long getIdTipoGrau() {
	
		return idTipoGrau;
	}


	
	public void setIdTipoGrau(Long idTipoGrau) {
	
		this.idTipoGrau = idTipoGrau;
	}


	
	public String getDsTipoGrau() {

		return this.dsTipoGrau;
	}

	public void setDsTipoGrau(final String dsTipoGrau) {

		this.dsTipoGrau = dsTipoGrau;
	}

	/*
	 * //bi-directional many-to-one association to FormacaoAcademica
	 * 
	 * @OneToMany(mappedBy="tipoGrau") public List<FormacaoAcademica> getFormacaoAcademicas() {
	 * return this.formacaoAcademicas; }
	 * 
	 * public void setFormacaoAcademicas(List<FormacaoAcademica> formacaoAcademicas) {
	 * this.formacaoAcademicas = formacaoAcademicas; }
	 * 
	 * public FormacaoAcademica addFormacaoAcademica(FormacaoAcademica formacaoAcademica) {
	 * getFormacaoAcademicas().add(formacaoAcademica); formacaoAcademica.setTipoGrau(this);
	 * 
	 * return formacaoAcademica; }
	 * 
	 * public FormacaoAcademica removeFormacaoAcademica(FormacaoAcademica formacaoAcademica) {
	 * getFormacaoAcademicas().remove(formacaoAcademica); formacaoAcademica.setTipoGrau(null);
	 * 
	 * return formacaoAcademica; }
	 */
}
