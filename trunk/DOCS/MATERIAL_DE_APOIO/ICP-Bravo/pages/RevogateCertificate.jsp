<html>
<header>
	<title>ICPBravo - Revoga��o de certificado - Solicita��o</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<h1>Revoga��o de certificado - Solicita��o</h1>
<h3>Client-Side</h3>
<p>O exemplo corrente visa demonstrar a inclus�o de um certificado na lista de revogados.</p>
<p>Este passo � apenas o formul�rio para solicita��o.</p>
<i>
<p>obs.: Uma caracter�stica de nosso exemplo que pode comprometer o teste � a seguinte:<br />Quando geramos a nossa primeira lista de revogados (CRL), informamos que o prazo de validade dela era de 1 m�s. Quando fizemos a verifica��o do certificado, esta lista foi carregada para o cache da biblioteca, onde s� ser� recarregada ao fim do prazo de validade do CRL, portanto, ao gerarmos esta nova lista para este exemplo, devemos apagar a lista do cache da biblioteca (no diret�rio C:\Users\<Seu usu�rio>\.ICPBravo\CRL\*).</p>
<p>Em uma AC em produ��o este problema n�o acontece, pois n�o � previsto a gera��o de CRLs antes do prazo de validade da mesma.</p>
</i>
<hr />
<form name='crl' action='RevogateCertificateServer.jsp'>
N�mero de S�rie: <input type='text' name='numSerie' value='0'><br /> 
Raz�o: <select name='reason'>
	<option value='0'>Unspecified</option>
	<option value='1'>Key Compromise</option>
	<option value='2'>CA Compromise</option>
	<option value='3'>Affiliation Changed</option>
	<option value='4'>Superseded</option>
	<option value='5'>Cessation of Operation</option>
	<option value='6'>Certificate Hold</option>
	<option value='8'>Remove from CRL</option>
	<option value='9'>Privilege with drawn</option>
	<option value='10'>AACompromise</option>
</select><br />
<input type='Submit' value='Revogar'>
</form>

</body></html>