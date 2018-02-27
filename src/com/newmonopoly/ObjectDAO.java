package com.newmonopoly;

import java.util.NoSuchElementException;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.newmonopoly.vo.Player;

/**
 * ObjectDAO class.
 */
public class ObjectDAO {
	private static ObjectDAO instance; // Creates a singleton.
	private EntityManagerFactory emf;
	private EntityManager em;
	private CriteriaBuilder cb;

	/**
	 * Object default constructor
	 */
	private ObjectDAO() {
		emf = Persistence.createEntityManagerFactory("newMonopoly");
		em = emf.createEntityManager();
		cb = em.getCriteriaBuilder();
	}

	/**
	 * Method used to instantiate an instance of ObjectDAO
	 * 
	 * @return an instance of ObjectDAO
	 */
	public static ObjectDAO getInstance() {
		if (instance == null) {
			instance = new ObjectDAO();
		}

		return instance;
	}

	/**
	 * Method persists a player to the database, if the player already exists, then the new info is merged
	 */
	public Player persistPlayer(Player p) {
		if (p == null) {
			throw new IllegalArgumentException("In persistPlayer (Player p): player is null");
		}

		try {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (p.getId() == -1) {
				em.persist(p);
			}
			else {
				em.merge(p);
			}

			em.getTransaction().commit();
		}
		catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}

		return p;
	}

	/**
	 * Method returns a single player or a list of players from the database
	 */
	public List<Player> queryPlayer(Player p) {
		if (p == null) {
			throw new IllegalArgumentException("In persistPlayer (Player p): player is null");
		}

		List<Player> queryList = new Vector<Player>();

		try {
			CriteriaQuery cq = cb.createQuery();
			Root<Player> root = cq.from(Player.class);
			cq.select(root);

			if (p.getId() > -1) {
				cq.where(cb.equal(root.get("id"), p.getId()));
			}

			Query query = em.createQuery(cq);
			queryList = query.getResultList();

			if (queryList == null) {
				throw new NoSuchElementException("In queryPlayer (Player p): queryList is null");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return queryList;
	}

	/**
	 * Method removes a player from the database
	 */
	public void removePlayer(Player p) {
		if (p == null) {
			throw new IllegalArgumentException("In persistPlayer (Player p): player is null");
		}
		
		Player player = new Player();

		try {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		try {
			player = queryPlayer(p).get(0);

			if (player == null) {
				throw new NoSuchElementException("In removePlayer (Player p: could not find Player in system, no delete made");
			}

			em.remove(player);
			em.getTransaction().commit();
		}
		catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}
	}
}