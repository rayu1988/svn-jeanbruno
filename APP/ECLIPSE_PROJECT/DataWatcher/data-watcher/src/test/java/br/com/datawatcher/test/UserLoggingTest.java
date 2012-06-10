package br.com.datawatcher.test;

import org.junit.Test;

import br.com.datawatcher.entity.UserLogging;
import br.com.datawatcher.exception.InterfaceNotImplemented;

public class UserLoggingTest {

	@Test
	public void userLogging() {
		try {
			UserLogging userLogging = new UserLogging("11111j1e11a11n11111", "br.com.datawatcher.test.DecryptTest");
			System.out.println(userLogging.getUserName());
			System.out.println(userLogging.getDecryptedUserName());
		} catch (InterfaceNotImplemented e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
