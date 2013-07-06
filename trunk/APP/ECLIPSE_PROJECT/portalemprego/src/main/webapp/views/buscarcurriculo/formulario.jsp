<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="grid" uri="/struts-jquery-grid-tags"%>

<s:form cssClass="dados-empresariais" method="post" theme="simple"
	id="filtros">

	<jsp:include page="filtros.jsp" />

</s:form>


<div id="curriculo-area">
	<s:form namespace="/visualizar" action="abreCurriculoPorCargo"
		method="post" theme="simple" id="formVisualizarCurriculoArea">

		<s:if test="showCurriculoArea">

			<jsp:include page="curriculo_area.jsp" />

		</s:if>
	</s:form>

</div>



<div id="curriculo-cargo">
	<s:form namespace="/visualizar" action="abreVisualizarCurriculoTable"
		method="post" theme="simple" id="formVisualizarCurriculoCargo">

		<s:if test="showCurriculoCargo">

			<jsp:include page="curriculo_cargo.jsp" />

		</s:if>

	</s:form>
</div>



<div id="lista-curriculo">

	<s:form namespace="/visualizar" action="gerarRelatorioAluno"
		method="post" theme="simple" id="formVisualizarCurriculoAluno">

		<s:if test="showCurriculo">

			<jsp:include page="curriculo.jsp" />

		</s:if>

	</s:form>

</div>
