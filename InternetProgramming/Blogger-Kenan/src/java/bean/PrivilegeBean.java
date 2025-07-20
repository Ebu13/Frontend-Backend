/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

import dao.PrivilegeDAO;
import entity.Privilege;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Administrator
 */
@Named
@SessionScoped
public class PrivilegeBean extends AbstractBean<Privilege> implements Serializable {
	@EJB
	private PrivilegeDAO dao;

	public PrivilegeBean() {
		super(Privilege.class);
	}
	
	@Override
	public void delete() {
		this.dao.delete(entity);
		this.clearEntity();
	}
	
	@Override
	public void create() {
		this.dao.create(this.entity);
		this.clearEntity();
	}
	
	@Override
	public void update() {
		this.dao.update(this.entity);
		this.clearEntity();
	}

	@Override
	public List<Privilege> getList() {
		return this.dao.getEntities();
	}
}
