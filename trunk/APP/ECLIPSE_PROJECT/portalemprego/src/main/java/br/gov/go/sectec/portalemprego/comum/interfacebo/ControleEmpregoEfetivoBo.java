package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.ControleEmpregoEfetivo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.NegocioGenerico;

public interface ControleEmpregoEfetivoBo extends NegocioGenerico<ControleEmpregoEfetivo, Integer>{
	public ControleEmpregoEfetivo consultar(Integer id);
	public List<ControleEmpregoEfetivo> listar();
	public void incluir(ControleEmpregoEfetivo controleEmpregoEfetivo);
	public ControleEmpregoEfetivo alterar(ControleEmpregoEfetivo controleEmpregoEfetivo);
	public void excluir(ControleEmpregoEfetivo controleEmpregoEfetivo);
}
