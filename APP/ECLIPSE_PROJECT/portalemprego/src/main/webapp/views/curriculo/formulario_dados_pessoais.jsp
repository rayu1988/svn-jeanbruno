<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
	
	<script>
	function SubmitFormacao(){
		 $('#formCurriculoDadosId').submit();
    };
	</script>

<s:form namespace="/curriculo" windowState="true" action="abreFormacaoAcademica"  cssClass="dados-empresariais" method="post" theme="simple" id="formCurriculoDadosId" > 
    	
    	<s:hidden name="aluno.idAluno" />
    	<s:hidden name="aluno.dsLogin" />
    	<s:hidden name="aluno.dsMatricula" />
    	<s:hidden name="aluno.dtCadastro" />
    	<s:hidden name="aluno.dtCadastro" />
    	<s:hidden name="aluno.dtRgEmissao" />
    	<s:hidden name="aluno.idControleEncaminhamento" />
    	<s:hidden name="aluno.idNaturalidade" />
    	<s:hidden name="aluno.dsSenha" />
    	<jsp:include page="dados_pessoais.jsp" />
    	<jsp:include page="dados_endereco.jsp" />
    	<jsp:include page="dados_telefone.jsp" />
    	<jsp:include page="dados_comuns.jsp" />
        <br/>

    <div style="text-align: center;">
		<s:submit value="Prosseguir"  id="botaoAbreFormacao"  cssClass="ui-state-default ui-button"  cssStyle="border-radius: 3px; margin-top:20px;" />
		
		<s:submit value="Voltar" action="sairCurriculo"   cssClass="ui-state-default ui-button"  cssStyle="border-radius: 3px; margin-top:20px;" />
    </div>
</s:form>

