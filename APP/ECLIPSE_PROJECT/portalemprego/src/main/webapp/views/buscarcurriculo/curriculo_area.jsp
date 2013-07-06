<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<script type="text/javascript">
           function MeuTeste(teste) {

        	   var valor = teste.innerHTML;

         	   $('input[name=nomeAreaInteresseCurriculo]').val(valor);

         	   $('#formVisualizarCurriculoArea').submit();
         	   
      	     }
           </script>


            <s:hidden name="nomeAreaInteresseCurriculo" />
        	<h1 id="curriculo">Currículo por Área</h1>
                <ul id="curriculo-area" style="width:350px;">
                
                <li class="tit">Total</li>
                <li class="soma-total">${totalArea}</li>
                 <c:forEach  var="teste" items="${curriculoAreaList}"  varStatus="contador">
                      <li class="area"  id="area_${contador.index}"  style="cursor: pointer;" onclick="MeuTeste(this);"> ${teste.area}</li>
                      <li class="total"> ${teste.total}</li>
                 </c:forEach>
                      <li class="passar">
                        <input type="button" class="ir" value=""/> 
                        <input type="button" class="vir" value="" /> 
                      </li>
                </ul>
