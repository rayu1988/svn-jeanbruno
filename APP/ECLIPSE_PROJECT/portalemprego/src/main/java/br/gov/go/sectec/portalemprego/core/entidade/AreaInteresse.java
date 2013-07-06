package br.gov.go.sectec.portalemprego.core.entidade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * 
 * <p>
 * <b>Title:</b> AreaInteresse.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Entidade de negocio Áreas de Interesse
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
@Entity(name = "adm_area_interesse")
public class AreaInteresse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_area_interesse")
	private Long idAreaInteresse;

	@Column(name = "ds_area_interesse")
	private String dsAreaInteresse;

	@OneToMany(mappedBy = "areaInteresse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CargoInteresse> cargosInteresse;


	public Long getIdAreaInteresse() {

		return this.idAreaInteresse;
	}

	public void setIdAreaInteresse(final Long idAreaInteresse) {

		this.idAreaInteresse = idAreaInteresse;
	}

	public String getDsAreaInteresse() {

		return this.dsAreaInteresse;
	}

	public void setDsAreaInteresse(final String dsAreaInteresse) {

		this.dsAreaInteresse = dsAreaInteresse;
	}

}
