/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 *
 * @author Administrator
 */
@Entity
public class SystemGroup extends AbstractEntity {
	
	@Column(unique = true)
	private String groupName;

	public SystemGroup() {
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String toString() {
		return "Group{" + "groupName=" + groupName + '}';
	}
}
