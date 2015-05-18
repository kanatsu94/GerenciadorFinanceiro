package dao;

import interfaces.Dao;

import java.io.Serializable;

import javax.persistence.EntityManager;

import persistence.JPAUtil;

public abstract class GenericDAO<T> implements Dao<T>{
	protected EntityManager em;
	private Class<T> persistentClass;
	
	public GenericDAO(Class<T> persistentClass) {
	// TODO Auto-generated constructor stub
		this.persistentClass = persistentClass;
	}
	
	@Override
	public boolean salvar(T t) {
		// TODO Auto-generated method stub
		openEm();
		
		try {
			em.getTransaction().begin();
			
			em.persist(t);
			
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			em.getTransaction().rollback();
			return false;
		}finally{
			closeEm();
		}
	}

	@Override
	public boolean remover(T t) {
		// TODO Auto-generated method stub
		openEm();
		
		try {
			em.getTransaction().begin();
			
			em.remove(em.merge(t));
			
			em.getTransaction().commit();
			
			return true;
		} catch (Exception e) {
			em.getTransaction().rollback();
			return false;
		}finally{
			closeEm();
		}
	}

	@Override
	public T procurar(Serializable id) {
		// TODO Auto-generated method stub
		openEm();
		T obj = em.find(persistentClass, id);
		closeEm();
		
		return obj;
	}

	@Override
	public boolean atualiza(T t) {
		// TODO Auto-generated method stub
		openEm();
		
		try {
			em.getTransaction().begin();
			
			em.merge(t);
			
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			em.getTransaction().rollback();
			return false;
		}finally{
			closeEm();
		}
	}
	
	protected void openEm(){
		this.em = JPAUtil.getEntityManager();
	}
	
	protected void closeEm(){
		JPAUtil.closeEntityManager();
	}
}
