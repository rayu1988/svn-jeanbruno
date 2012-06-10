import br.com.datawatcher.entity.DataWatcher;
import br.com.datawatcher.xstream.XStreamFactory;

/**
 * @author carrefour
 *
 */
public class MainFromXML {
	
	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException {
		DataWatcher dataWatcher = XStreamFactory.getInstance().fromXML(new MainFromXML().getClass().getResource("datawatcher.xml"));
		
		System.out.println(XStreamFactory.getInstance().toXML(dataWatcher));
	}
}
