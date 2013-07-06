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
import javax.persistence.Transient;

import br.gov.go.sectec.portalemprego.comum.utilitario.ValidatorUtil;

/**
 * 
 * <p>
 * <b>Title:</b> Endereco.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Entidade de negocios para Endereços
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
@Entity(name = "adm_endereco")
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_endereco")
	private Long idEndereco;

	@Column(name = "ds_bairro")
	private String dsBairro;

	@Column(name = "ds_complemento")
	private String dsComplemento;

	@Column(name = "ds_endereco")
	private String dsEndereco;

	@Column(name = "nu_cep")
	private Integer nuCep;

	@Column(name = "nu_endereco")
	private Integer nuEndereco;

	@ManyToOne
	@JoinColumn(name = "id_cidade")
	private Cidade cidade;

	@OneToMany(mappedBy = "endereco", fetch = FetchType.LAZY)
	private List<Empresa> empresas;

	@OneToMany(mappedBy = "endereco", fetch = FetchType.LAZY)
	private List<Aluno> alunos;

	@Transient
	private boolean todosPreenchidos;

	public Long getIdEndereco() {
	
		return idEndereco;
	}
	
	public void setIdEndereco(Long idEndereco) {
	
		this.idEndereco = idEndereco;
	}

	public String getDsBairro() {

		return dsBairro;
	}

	public void setDsBairro(String dsBairro) {

		this.dsBairro = dsBairro;
	}

	public String getDsComplemento() {

		return dsComplemento;
	}

	public void setDsComplemento(String dsComplemento) {

		this.dsComplemento = dsComplemento;
	}

	public String getDsEndereco() {

		return dsEndereco;
	}

	public void setDsEndereco(String dsEndereco) {

		this.dsEndereco = dsEndereco;
	}

	public Integer getNuCep() {

		return nuCep;
	}

	public void setNuCep(Integer nuCep) {

		this.nuCep = nuCep;
	}

	public Integer getNuEndereco() {

		return nuEndereco;
	}

	public void setNuEndereco(Integer nuEndereco) {

		this.nuEndereco = nuEndereco;
	}

	public Cidade getCidade() {

		return cidade;
	}

	public void setCidade(Cidade cidade) {

		this.cidade = cidade;
	}

	public List<Empresa> getEmpresas() {

		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {

		this.empresas = empresas;
	}

	public List<Aluno> getAlunos() {

		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {

		this.alunos = alunos;
	}

	public boolean isTodosPreenchidos() {

		todosPreenchidos = !( ( ValidatorUtil.isNull(cidade) || ValidatorUtil.isBlank(dsBairro) || ValidatorUtil.isBlank(dsEndereco) || ValidatorUtil.isNull(nuCep) || ValidatorUtil
				.isNull(nuEndereco) ) );

		return todosPreenchidos;
	}

	public void setTodosPreenchidos(boolean todosPreenchidos) {

		this.todosPreenchidos = todosPreenchidos;
	}

}
