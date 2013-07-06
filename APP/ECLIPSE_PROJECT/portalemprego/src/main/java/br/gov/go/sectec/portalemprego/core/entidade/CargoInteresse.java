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
 * <b>Title:</b> CargoInteresse.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Entidade de negocios para Cargos de Interesse
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
@Entity(name = "adm_cargo_interesse")
public class CargoInteresse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cargo_interesse")
	private Long idCargoInteresse;

	@Column(name = "ds_cargo_interesse", length = 100)
	private String dsCargoInteresse;

	@OneToMany(mappedBy = "cargoInteresse", fetch = FetchType.LAZY)
	private List<Aluno> aluno;

	@ManyToOne
	@JoinColumn(name = "id_area_interesse")
	private AreaInteresse areaInteresse;

	public Long getIdCargoInteresse() {

		return idCargoInteresse;
	}

	public void setIdCargoInteresse(Long idCargoInteresse) {

		this.idCargoInteresse = idCargoInteresse;
	}

	public String getDsCargoInteresse() {

		return dsCargoInteresse;
	}

	public void setDsCargoInteresse(String dsCargoInteresse) {

		this.dsCargoInteresse = dsCargoInteresse;
	}

	public List<Aluno> getAluno() {

		return aluno;
	}

	public void setAluno(List<Aluno> aluno) {

		this.aluno = aluno;
	}

}
