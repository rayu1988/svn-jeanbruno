package br.gov.go.sectec.portalemprego.comum.interfacedao;

import br.gov.go.sectec.portalemprego.core.entidade.Empresa;

public interface EmpresaDao extends PersistenciaGenerica<Empresa, Integer> {

	public Empresa buscarEmpresaPorCNPJ(String cnpj);
	public Empresa buscarEmpresaPorEmail(String email);
}
