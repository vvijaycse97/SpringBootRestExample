package com.example.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Login {
	@NotNull
	@Size(min = 2, max = 30)
	private String userName;
	@NotNull
	private String userPwd;
	private String githubusername;

	public Login() {

	}

	public Login(String userName, String userPwd) {
		super();
		this.userName = userName;
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getGithubusername() {
		return githubusername;
	}

	public void setGithubusername(String githubusername) {
		this.githubusername = githubusername;
	}

}
