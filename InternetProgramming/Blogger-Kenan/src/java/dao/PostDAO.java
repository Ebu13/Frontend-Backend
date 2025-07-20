/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Post;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import java.io.Serializable;

/**
 *
 * @author Administrator
 */
@Local
@Stateless
public class PostDAO extends AbstractDAO<Post> implements Serializable {
	public PostDAO() {
		super(Post.class);
	}
}
