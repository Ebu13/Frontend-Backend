/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

import dao.SystemUserDAO;
import entity.SystemUser;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
@Named
@ViewScoped
public class SystemUserBean extends AbstractBean<SystemUser> implements Serializable {

	@EJB
	private SystemUserDAO dao;

	@Inject
	private FacesContext fc;

	private String email;
	private String password;

	public SystemUserBean() {
		super(SystemUser.class);
	}

	@Override
	public void delete() {
		this.dao.delete(entity);
		this.clearEntity();
	}

	public String login() {
		try {
			final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
			final byte[] hashbytes = digest.digest(this.password.getBytes(StandardCharsets.UTF_8));
			String sha3Hex = bytesToHex(hashbytes);
			
			SystemUser u = dao.getLogin(email, password);
			if ( u != null ) {
				fc.getExternalContext().getSessionMap().put("validUser", u);
				return "/panel/category/index?faces-redirect=true";
			}
			
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(SystemUserBean.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "/login?faces-redirect=true";
	}

	@Override
	public void create() {

		try {
			final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
			final byte[] hashbytes = digest.digest(this.entity.getPassword().getBytes(StandardCharsets.UTF_8));
			String sha3Hex = bytesToHex(hashbytes);

			this.entity.setPassword(sha3Hex);

			this.dao.create(this.entity);
			this.clearEntity();
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(SystemUserBean.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder(2 * hash.length);
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	@Override
	public void update() {

		try {
			final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
			final byte[] hashbytes = digest.digest(this.entity.getPassword().getBytes(StandardCharsets.UTF_8));
			String sha3Hex = bytesToHex(hashbytes);

			this.entity.setPassword(sha3Hex);

			this.dao.update(this.entity);
			this.clearEntity();
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(SystemUserBean.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public List<SystemUser> getList() {
		return this.dao.getEntities();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
