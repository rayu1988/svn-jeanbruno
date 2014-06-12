package br.com.laptracker.business.entities;

public class UserTO extends TransferObject {

	private static final long serialVersionUID = 8280890111696936238L;
	
	private Long 		id;
	private String 		nickname;
	private String 		password;
	
	// GETTERS AND SETTERS //
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
