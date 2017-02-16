package com.banque.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Session Factory hibernate.
 */
public final class HibernateSessionFactory {

	protected static Log LOG = LogFactory.getLog(HibernateSessionFactory.class);

	private static HibernateSessionFactory instance;

	private SessionFactory sessionFactory;

	/**
	 * Constructeur de l'objet.
	 * 
	 * @throws Exception
	 *             si un problème survient
	 */
	public HibernateSessionFactory() throws Exception {
		super();
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		this.sessionFactory = configuration
				.buildSessionFactory(serviceRegistry);
	}

	/**
	 * Recupere la session factory.
	 *
	 * @return la session factory.
	 */
	public static synchronized HibernateSessionFactory getInstance() {
		if (HibernateSessionFactory.instance == null) {
			try {
				HibernateSessionFactory.instance = new HibernateSessionFactory();
			} catch (Throwable e) {
				HibernateSessionFactory.LOG.fatal("Impossible d'initialiser Hibernate", e);
			}
		}
		return HibernateSessionFactory.instance;
	}

	/**
	 * Recupere la session factory.
	 * 
	 * @return la session factory.
	 */
	public SessionFactory getSessionFactory() {
		if (this.sessionFactory == null || this.sessionFactory.isClosed()) {
			HibernateSessionFactory.LOG.fatal("Attention, la session factory est fermee !");
			return null;
		}
		return this.sessionFactory;
	}

	@Override
	protected void finalize() throws Throwable {
		this.close();
		super.finalize();
	}

	/**
	 * Fermeture de la session factory.
	 */
	public void close() {
		if (this.sessionFactory != null) {
			try {
				this.sessionFactory.close();
				this.sessionFactory = null;
			} catch (Throwable e) {
				HibernateSessionFactory.LOG.warn("Fermeture de la session factory", e);
			}
		}
	}
}