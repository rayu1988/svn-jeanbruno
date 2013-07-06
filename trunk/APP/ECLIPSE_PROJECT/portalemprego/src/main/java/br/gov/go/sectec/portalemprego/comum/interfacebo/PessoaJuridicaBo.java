package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.PessoaJuridica;
import br.gov.go.sectec.portalemprego.comum.interfacebo.NegocioGenerico;

public interface PessoaJuridicaBo extends NegocioGenerico<PessoaJuridica,Integer>{
	public PessoaJuridica consultar(Integer id);
	public List<PessoaJuridica> listar();
	public void incluir(PessoaJuridica pessoaJuridica);
	public PessoaJuridica alterar(PessoaJuridica pessoaJuridica);
	public void excluir(PessoaJuridica pessoaJuridica);
}
