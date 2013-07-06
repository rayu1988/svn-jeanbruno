package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.PessoaJuridica;

public interface PessoaJuridicaDao extends PersistenciaGenerica<PessoaJuridica, Integer> {
	public PessoaJuridica consultar(Integer id);
	public List<PessoaJuridica> listar();
	public void incluir(PessoaJuridica pessoaJuridica);
	public PessoaJuridica alterar(PessoaJuridica pessoaJuridica);
	public void excluir(PessoaJuridica pessoaJuridica);
}
