/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

import dao.CategoryDAO;
import entity.Category;
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
public class CategoryBean extends AbstractBean<Category> implements Serializable {
	@EJB
	private CategoryDAO dao;

	public CategoryBean() {
		super(Category.class);
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
	public List<Category> getList() {
		return this.dao.getEntities();
	}
}
