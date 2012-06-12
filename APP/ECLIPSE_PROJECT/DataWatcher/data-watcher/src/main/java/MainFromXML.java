import br.com.datawatcher.entity.DataWatcher;
import br.com.datawatcher.exception.DataWatcherException;
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
		try {
			dataWatcher.start();
			System.out.println(XStreamFactory.getInstance().toXML(dataWatcher));
		} catch (DataWatcherException e) {
			e.printStackTrace();
		}
		
	}
}
