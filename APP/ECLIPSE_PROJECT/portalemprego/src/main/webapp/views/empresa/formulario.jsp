<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:form namespace="empresa" action="incluirEmpresa" cssClass="dados-empresariais" method="post" theme="simple" id="formEmpresaId" > 

 	<jsp:include page="dados_pessoais.jsp" />
 	<jsp:include page="dados_endereco.jsp" />
 	<jsp:include page="dados_telefone.jsp" />
 	<jsp:include page="dados_atividade.jsp" />
 	<jsp:include page="dados_vagas.jsp" />
 	
 	<br/>
 
    <div style="text-align: center;">
    	<c:if test="${session['empresaLogin'] eq null}">
			<s:submit value="Incluir" cssClass="ui-state-default ui-button"  cssStyle="border-radius: 3px; margin-top:20px;" />
    	</c:if>
    	<c:if test="${session['empresaLogin'] ne null}">
			<s:submit value="Salvar" action="salvarEmpresa" cssClass="ui-state-default ui-button"  cssStyle="border-radius: 3px; margin-top:20px;" />
    	</c:if>
		<s:submit value="Voltar" action="voltarInicioEmpresa" cssClass="ui-state-default ui-button"  cssStyle="border-radius: 3px; margin-top:20px;" />
    </div>
</s:form>

