package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.VagaOfertada;
import br.gov.go.sectec.portalemprego.comum.interfacebo.NegocioGenerico;

public interface VagaOfertadaBo extends NegocioGenerico<VagaOfertada,Integer>{
	public VagaOfertada consultar(Integer id);
	public List<VagaOfertada> listar();
	public void incluir(VagaOfertada vagaOfertada);
	public VagaOfertada alterar(VagaOfertada vagaOfertada);
	public void excluir(VagaOfertada vagaOfertada);
}
