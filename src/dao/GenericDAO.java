package dao;

import interfaces.Dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.criterion.Example;

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
	public boolean atualizar(T t) {
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
	
	// USANDO UNCHECKED PORQUE A JPA NAO POSSUI O METODO EM.GETCRITERIABUILDER().CREATEQUERY()<T>
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<T> listaTudo(){
		List<T> lista;
		
		openEm();
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(persistentClass);
		criteriaQuery.select(criteriaQuery.from(persistentClass));
		
		lista = em.createQuery(criteriaQuery).getResultList();
		
		closeEm();
		
		return lista;
	}
	
	protected void openEm(){
		this.em = JPAUtil.getEntityManager();
	}
	
	protected void closeEm(){
		JPAUtil.closeEntityManager();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> consultaDinamica(T t){
		openEm();
		
		Session session = em.unwrap(Session.class);
		List<T> results = session.createCriteria(persistentClass).add(Example.create(t)).list();
		
		closeEm();
		
		return results;
	}
}
