/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.SystemGroup;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import java.io.Serializable;

/**
 *
 * @author Administrator
 */
@Local
@Stateless
public class GroupDAO extends AbstractDAO<SystemGroup> implements Serializable {
	public GroupDAO() {
		super(SystemGroup.class);
	}
}
