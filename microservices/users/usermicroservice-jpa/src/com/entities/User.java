package com.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the User database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("usermicroservice-jpa");
	private static final long serialVersionUID = 1L;

	@Id
	private String username;

	private String email;

	private String name;

	private String password;

	public User() {
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public static User getUser(String username) {
		EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
		EntityTransaction et = null;
		User user = null;
		try {

			et = em.getTransaction();
			et.begin();
			user = em.find( User.class , username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			em.close();
		}
		return user;
	}
	
	public boolean save() {
		EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
		EntityTransaction et = null;
		
		boolean succesfulltransaction = false;
		
		try {
			
			et = em.getTransaction();
			et.begin();
			if(User.getUser(this.username)==null)
				em.persist(this);
			et.commit();
			succesfulltransaction = true;
			
		} catch (Exception e) {
			if (et != null) {
				et.rollback();
			}
		}
		finally {
			em.close();
		}
		return succesfulltransaction;
	}

}