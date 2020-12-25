package com.example.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GithubUser {
	private String login;
	private String email;
	private String company;
	private String location;
	@JsonProperty("created_at")
	private String createdAt;
	@JsonProperty("updated_at")
	private String updatedAt;
	@JsonProperty("public_repos")
	private String publicRepos;
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getPublicRepos() {
		return publicRepos;
	}
	public void setPublicRepos(String publicRepos) {
		this.publicRepos = publicRepos;
	}
	@Override
	public String toString() {
		return "GithubUser [login=" + login + ", email=" + email + ", company=" + company + ", location=" + location
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", publicRepos=" + publicRepos + "]";
	}
	
	
}