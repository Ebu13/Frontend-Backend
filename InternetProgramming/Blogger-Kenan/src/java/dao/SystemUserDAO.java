/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.SystemUser;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Administrator
 */
@Local
@Stateless
public class SystemUserDAO extends AbstractDAO<SystemUser> implements Serializable {
	public SystemUserDAO() {
		super(SystemUser.class);
	}
	
	public SystemUser getLogin(String email, String pass) {
		Query q = this.em.createQuery("select u from SystemUser u where u.email=:email and u.password=:pass", SystemUser.class);
		q.setParameter("user", email);
		q.setParameter("pass", pass);
		List<SystemUser> l =  q.getResultList();
		if ( l.isEmpty() ) {
			return null;
		} else {
			return l.get(0);
		}
	}
}
