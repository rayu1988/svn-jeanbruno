package br.com.barganhas.business.entities;

import br.com.barganhas.business.entities.annotations.IdField;

@SuppressWarnings("serial")
public class AdministratorTO extends TransferObject {

	@IdField
	private String			nickname;

	private String			fullname;
	private String			email;
	private String 			password;
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
