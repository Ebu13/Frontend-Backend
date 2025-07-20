/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import java.io.Serializable;

/**
 *
 * @author Administrator
 */
@Entity
@NamedQuery(name = "findAll", query = "select c from Category c order by c.id desc")
@NamedQuery(name = "findByID", query = "select c from Category c where c.id=:id")
public class Category extends AbstractEntity implements Serializable{
	
	private String categoryName;
	
	@ManyToOne(targetEntity = Category.class)
	private Category parent;

	public Category() {
	}

	public Category(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

}
