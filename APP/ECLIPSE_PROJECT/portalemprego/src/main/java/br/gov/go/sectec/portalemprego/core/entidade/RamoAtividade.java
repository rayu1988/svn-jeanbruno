package br.gov.go.sectec.portalemprego.core.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * <p>
 * <b>Title:</b> RamoAtividade.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Entidade de negocios para Ramos de Atividade
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
@Entity(name = "adm_ramo_atividade")
public class RamoAtividade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ramo_atividade")
	private Long idRamoAtividade;

	@Column(name = "ds_ramo_atividade")
	private String dsRamoAtividade;
	
	public Long getIdRamoAtividade() {
	
		return idRamoAtividade;
	}

	public void setIdRamoAtividade(Long idRamoAtividade) {
	
		this.idRamoAtividade = idRamoAtividade;
	}
	

	public String getDsRamoAtividade() {

		return dsRamoAtividade;
	}

	public void setDsRamoAtividade(String dsRamoAtividade) {

		this.dsRamoAtividade = dsRamoAtividade;
	}

}
