package br.com.oaks.ICPBravo.applet;

import java.io.FileNotFoundException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import br.com.oaks.ICPBravo.applet.ConfiguracaoApplet.ConfiguracaoAlteradaListener;
import br.com.oaks.ICPBravo.exceptions.CryptographicDeviceNotFoundException;
import br.com.oaks.ICPBravo.manager.ICPBravoManager;

public class CurrentConfiguration {
	private ICPBravoManager _current = null;
	ConfiguracaoApplet _conf;
	
	public ConfiguracaoApplet getConf() {
		return _conf;
	}

	public void configura(final ConfiguracaoAlteradaListener listener) {
		this._conf.configura(new ConfiguracaoAlteradaListener() {
			public void onAlterarConfiguracao() {
				try {
					_current = _conf.getManager();
					listener.onAlterarConfiguracao();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public CurrentConfiguration(ConfiguracaoApplet conf) {
		this._conf = conf;
		try {
			_current = _conf.getManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void load(String key) throws NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, FileNotFoundException, CryptographicDeviceNotFoundException {
		_current = ICPBravoManager.getManagerInstance(key);
	}
	
	public ICPBravoManager getManager() throws NoSuchAlgorithmException {
		if (_current == null)
			_current = ICPBravoManager.getDefaultManager();
		return _current;
	}
	
	public void setManager(ICPBravoManager manager) {
		_current = manager;
	}
}
