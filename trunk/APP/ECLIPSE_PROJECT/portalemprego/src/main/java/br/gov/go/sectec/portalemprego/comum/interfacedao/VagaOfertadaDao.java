package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.VagaOfertada;

public interface VagaOfertadaDao extends PersistenciaGenerica<VagaOfertada, Integer> {
	public VagaOfertada consultar(Integer id);
	public List<VagaOfertada> listar();
	public void incluir(VagaOfertada vagaOfertada);
	public VagaOfertada alterar(VagaOfertada vagaOfertada);
	public void excluir(VagaOfertada vagaOfertada);

}
