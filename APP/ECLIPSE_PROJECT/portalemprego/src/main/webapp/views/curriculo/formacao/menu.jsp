<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form>
	
	<ul id="menu-empresa">
		<li><s:a namespace="/curriculo" action="abreDadosPessoais">Dados Pessoais</s:a></li>
		<li id="a"><s:a>Forma��o Academica</s:a></li>
		<li><s:a  namespace="/curriculo" action="abreDadosExperienciaProfissional" >Experi�ncia Profissional</s:a></li>
		<li><s:a namespace="/curriculo" action="abreDadosExperienciaProfissional"  >Emcaminhamento de Emprego</s:a></li>
	</ul>
	
</s:form>
