/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Category;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import java.io.Serializable;

/**
 *
 * @author Administrator
 */
@Local
@Stateless
public class CategoryDAO extends AbstractDAO<Category> implements Serializable {
	public CategoryDAO() {
		super(Category.class);
	}
}
