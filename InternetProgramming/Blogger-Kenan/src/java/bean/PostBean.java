/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

import dao.PostDAO;
import entity.Post;
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
public class PostBean extends AbstractBean<Post> implements Serializable {
	@EJB
	private PostDAO dao;

	public PostBean() {
		super(Post.class);
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
	public List<Post> getList() {
		return this.dao.getEntities();
	}
}
