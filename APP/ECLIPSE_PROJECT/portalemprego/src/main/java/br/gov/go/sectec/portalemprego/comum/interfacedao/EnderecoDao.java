package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.Endereco;

public interface EnderecoDao extends PersistenciaGenerica<Endereco, Integer> {
	public Endereco consultar(Integer id);
	public List<Endereco> listar();
	public void incluir(Endereco endereco);
	public Endereco alterar(Endereco endereco);
	public void excluir(Endereco endereco);

}
