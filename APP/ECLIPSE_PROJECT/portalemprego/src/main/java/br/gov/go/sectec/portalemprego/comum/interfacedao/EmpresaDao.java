package br.gov.go.sectec.portalemprego.comum.interfacedao;

import br.gov.go.sectec.portalemprego.core.entidade.Empresa;

public interface EmpresaDao extends PersistenciaGenerica<Empresa, Integer> {

	public Empresa buscarEmpresaPorCNPJ(String cnpj);
	public Empresa buscarEmpresaPorEmail(String email);
	public boolean checkExistEmpresa(String cnpj);
	public Empresa login(String dsLogin, String dsSenha);
	public Empresa obterEmpresaPeloId(Long id);
	public Empresa salvar(Empresa empresa);
	
}
