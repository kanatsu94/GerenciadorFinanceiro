package persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public final class JPAUtil {
	private static final String PERSISTENCE_UNIT = "GerenciadorFinanceiro";
	private static ThreadLocal<EntityManager> threadEntityManager = new ThreadLocal<EntityManager>();
	private static EntityManagerFactory entityManagerFactory;

	private JPAUtil() {

	}

	public static void initFactory() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT);
		}
	}

	public static EntityManager getEntityManager() {
		if (entityManagerFactory == null) {
			initFactory();
		}

		EntityManager entityManager = threadEntityManager.get();

		if (entityManager == null || !entityManager.isOpen()) {
			entityManager = entityManagerFactory.createEntityManager();
			JPAUtil.threadEntityManager.set(entityManager);
		}

		return entityManager;
	}

	public static void closeEntityManager() {
		EntityManager em = threadEntityManager.get();

		if (em != null) {
			EntityTransaction transaction = em.getTransaction();

			if (transaction.isActive()) {
				transaction.commit();
			}

			em.close();

			threadEntityManager.set(null);
		}
	}

	public static void closeEntityManagerFactory() {
		closeEntityManager();
		entityManagerFactory.close();
	}
}