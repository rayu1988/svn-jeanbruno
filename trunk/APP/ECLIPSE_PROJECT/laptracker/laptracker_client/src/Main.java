import br.com.laptracker.business.service.ImportTrackMessageResult;
import br.com.laptracker.ws.client.ClientWrapper;


public class Main {

	public static void main(String[] args) {
		String sampleXmlContent = " <trackmessage> " +
				"   <laptrack> " +
				"   	<id>20121220074912</id> " +
				"   	<tipo>continua</tipo> " +
				"     <distanciaUnidade>Kilometros</distanciaUnidade> " +
				"     <distanciaValor>1.0</distanciaValor> " +
				"     <tempoTotalFormato>hh:mm:ss</tempoTotalFormato> " +
				"     <tempoTotalValor>00:00:05</tempoTotalValor> " +
				"     <divisaoDistanciaUnidade>Metros</divisaoDistanciaUnidade> " +
				"     <divisaoDistanciaValor>250.0</divisaoDistanciaValor> " +
				"     <lap> " +
				"       <id>1</id> " +
				"       <tempoFormato>hh:mm:ss</tempoFormato> " +
				"       <tempo>00:00:01</tempo> " +
				"       <UnidadeDistancia>Metros</UnidadeDistancia> " +
				"       <distancia>250.0</distancia> " +
				"     </lap> " +
				"     <lap> " +
				"       <UnidadeDistancia>Metros</UnidadeDistancia> " +
				"       <tempoFormato>hh:mm:ss</tempoFormato> " +
				"       <tempo>00:00:02</tempo> " +
				"       <id>2</id> " +
				"       <distancia>500.0</distancia> " +
				"     </lap> " +
				"     <lap> " +
				"       <UnidadeDistancia>Metros</UnidadeDistancia> " +
				"       <tempoFormato>hh:mm:ss</tempoFormato> " +
				"       <tempo>00:00:03</tempo> " +
				"       <id>3</id> " +
				"       <distancia>750.0</distancia> " +
				"     </lap> " +
				"     <lap> " +
				"       <UnidadeDistancia>Metros</UnidadeDistancia> " +
				"       <tempoFormato>hh:mm:ss</tempoFormato> " +
				"       <tempo>00:00:05</tempo> " +
				"       <id>4</id> " +
				"       <distancia>1000.0</distancia> " +
				"     </lap> " +
				"   </laptrack> " +
				" </trackmessage> ";
		
		ClientWrapper client = new ClientWrapper("http://localhost:8080/laptracker/services/importTrackMessage");
		ImportTrackMessageResult result = client.importTrackMessage("admin", "admin", sampleXmlContent);
		System.out.println(result.getValue());
	}
	
}
