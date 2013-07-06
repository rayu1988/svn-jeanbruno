<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form>
	
	<ul id="menu-empresa">
		<li><s:a namespace="/curriculo" action="abreDadosPessoais">Dados Pessoais</s:a></li>
		<li id="a"><s:a>Formação Academica</s:a></li>
		<li><s:a  namespace="/curriculo" action="abreDadosExperienciaProfissional" >Experiência Profissional</s:a></li>
		<li><s:a namespace="/curriculo" action="abreDadosExperienciaProfissional"  >Emcaminhamento de Emprego</s:a></li>
	</ul>
	
</s:form>
