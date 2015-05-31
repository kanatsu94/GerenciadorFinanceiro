package dao;

import java.util.List;

import model.Categoria;
import model.Tipo;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class CategoriaDAO extends GenericDAO<Categoria>{
	public CategoriaDAO(){
		super(Categoria.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Categoria> listaTudoPorTipo(Tipo t) {
		openEm();

		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Categoria.class);
		
		criteria.add(Restrictions.eq("tipoBean", t));


		List<Categoria> results = criteria.list();

		closeEm();

		return results;
	}
}
