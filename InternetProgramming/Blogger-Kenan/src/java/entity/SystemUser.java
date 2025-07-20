/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;

/**
 *
 * @author Administrator
 */
@Entity
public class SystemUser extends AbstractEntity {

	@Column(unique = true)
	private String email;

	@ManyToOne(targetEntity = SystemGroup.class)
	private SystemGroup group;

	private String nameSurname;
	private String password;

	public SystemUser() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNameSurname() {
		return nameSurname;
	}

	public void setNameSurname(String nameSurname) {
		this.nameSurname = nameSurname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SystemGroup getGroup() {
		return group;
	}

	public void setGroup(SystemGroup group) {
		this.group = group;
	}

}
