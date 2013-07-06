package br.gov.go.sectec.portalemprego.web.form;

import org.springframework.stereotype.Component;

import br.gov.go.sectec.portalemprego.core.entidade.Empresa;

@Component
public class EmpresaForm {

	private Empresa empresa;

	private Long idPais;

	private Long idUf;

	private Long idCidade;

	private Long idTipoTelefone;

	private Long idRamoAtividade;

	private Long idCidadeVaga;

	public Empresa getEmpresa() {

		return this.empresa;
	}

	public void setEmpresa(final Empresa empresa) {

		this.empresa = empresa;
	}

	public Long getIdPais() {

		return this.idPais;
	}

	public void setIdPais(final Long idPais) {

		this.idPais = idPais;
	}

	public Long getIdUf() {

		return this.idUf;
	}

	public void setIdUf(final Long idUf) {

		this.idUf = idUf;
	}

	public Long getIdCidade() {

		return this.idCidade;
	}

	public void setIdCidade(final Long idCidade) {

		this.idCidade = idCidade;
	}

	public Long getIdTipoTelefone() {

		return this.idTipoTelefone;
	}

	public void setIdTipoTelefone(final Long idTipoTelefone) {

		this.idTipoTelefone = idTipoTelefone;
	}

	public Long getIdRamoAtividade() {

		return this.idRamoAtividade;
	}

	public void setIdRamoAtividade(final Long idRamoAtividade) {

		this.idRamoAtividade = idRamoAtividade;
	}

	public Long getIdCidadeVaga() {

		return this.idCidadeVaga;
	}

	public void setIdCidadeVaga(final Long idCidadeVaga) {

		this.idCidadeVaga = idCidadeVaga;
	}

}
