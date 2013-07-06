<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form>
	<ul id="menu-empresa">
		<li id="a"><s:a namespace="empresa" action="iniciarEmpresa">Cadastro de Instituição / Empresa</s:a></li>
		<li><s:a namespace="empresa" action="iniciarEmprego">Empregos Efetivados</s:a></li>
	</ul>
</s:form>