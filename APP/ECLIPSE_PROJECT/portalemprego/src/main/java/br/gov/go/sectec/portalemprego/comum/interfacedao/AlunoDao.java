package br.gov.go.sectec.portalemprego.comum.interfacedao;

import br.gov.go.sectec.portalemprego.core.entidade.Aluno;

public interface AlunoDao extends PersistenciaGenerica<Aluno, Long> {

	/**
	 * Método responsável por obter aluno por login e senha.
	 * 
	 * @author Silvânio
	 * 
	 * @param login
	 * 
	 * @param senha
	 * 
	 * @return <code>Aluno</code>
	 */
	Aluno obterAlunoPorLoginSenha(String login, String senha);
}
