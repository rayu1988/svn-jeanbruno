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
		try {
			DataWatcher dataWatcher = XStreamFactory.getInstance().fromXML(new MainFromXML().getClass().getResource("datawatcher.xml"));
			dataWatcher.start();
		} catch (DataWatcherException e) {
			e.printStackTrace();
		}
	}
}
