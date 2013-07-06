<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<script type="text/javascript">
	function abreCurriculo(dado) {

		var valor = dado.innerHTML;

		$('input[name=nomeCargoInteresseCurriculo]').val(valor);

		$('#formVisualizarCurriculoCargo').submit();

	}
</script>

<s:hidden name="nomeCargoInteresseCurriculo" />
<h1 id="Curriculo-cargo">Curriculo por Cargo</h1>

<ul id="comercial-vendas">${nomeAreaInteresseCurriculo}


	<c:forEach var="cargo" items="${curriculoCargoList}" varStatus="contCargo">
		<li class="area" id="cargo_${contCargo.index}" style="cursor: pointer;"  onclick="abreCurriculo(this);">${cargo.cargo}</li>
		<li class="total">${cargo.total}</li>
	</c:forEach>

</ul>

