package IcpBravoJSP;

public class Util {
	public static String getUser(String login, String pw) {
		java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("users");

		for (int a=0; ; a++) {
			try {
				String usr = resourceBundle.getString("USR_"+a);
				if (usr == null)
					break;
				String stPw = resourceBundle.getString("PW_"+a);
				if (login.toLowerCase().equals(usr.toLowerCase()) && pw.equals(stPw))
					return login;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
}
