<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<script type="text/javascript">
	function abrePDF(valor) {
		$('#formVisualizarCurriculoAluno').submit();
	}
</script>


				<h1 id="lista-curriculo">Lista de Currículos</h1>
            	<ul id="lista-curriculo">
                  <c:forEach  var="curriculo" items="${curriculoAlunoList}"  varStatus="contAluno">
                  
                    <li class="nome" id="curriculovis_${contAluno.index}">${curriculo.aluno}</li>
                        
                        <li class="vizualizar">
                        	<input type="button"  value="" title="${curriculo.idCurriculo}" onclick="abrePDF(this.title);" />
                        </li>
                 
                 </c:forEach>
                </ul>

