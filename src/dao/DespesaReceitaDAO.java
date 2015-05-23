package dao;

import interfaces.DaoDespesaReceita;

import java.util.List;

import javax.persistence.Query;

import model.DespesaReceita;
import model.Tipo;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

@SuppressWarnings({ "unchecked"})
public class DespesaReceitaDAO extends GenericDAO<DespesaReceita> implements
		DaoDespesaReceita {
	public DespesaReceitaDAO() {
		super(DespesaReceita.class);
	}

	@Override
	public int removerPorParcelaId(int parcelaId) {
		int i = 0;
		openEm();

		try {
			em.getTransaction().begin();

			Query q = em.createNamedQuery(DespesaReceita.REMOVER_POR_PARCELA_ID);
			q.setParameter("parcela_id", parcelaId);
			
			// O METODO EXECUTEUPDATE() RETORNA A QUANTIDADE DE LINHAS AFETADAS
			// NO BANCO DE DADOS.
			i = q.executeUpdate();

			em.getTransaction().commit();

			return i;
		} catch (Exception e) {
			em.getTransaction().rollback();
			return i;
		} finally {
			closeEm();
		}
	}
	
	public boolean atualizarPorParcelaId(DespesaReceita dr) {
		openEm();

		try {
			em.getTransaction().begin();

			Query q = em.createNamedQuery(DespesaReceita.LISTAR_POR_PARCELA_ID);
			q.setParameter("parcela_id", dr.getParcelaId());
			
			// O METODO EXECUTEUPDATE() RETORNA A QUANTIDADE DE LINHAS AFETADAS
			// NO BANCO DE DADOS.
			List<DespesaReceita> lista = q.getResultList();
			for(DespesaReceita d : lista){
				d.setDescricao(dr.getDescricao());
				d.setValor(dr.getValor());
				d.setFixa(dr.getFixa());
				d.setCategoriaBean(dr.getCategoriaBean());
				d.setContaBean(dr.getContaBean());
				
				em.merge(d);
			}
			
			em.getTransaction().commit();

			return true;
		} catch (Exception e) {
			em.getTransaction().rollback();
			return false;
		} finally {
			closeEm();
		}
	}


	public List<DespesaReceita> listaTudoPorTipo(Tipo t) {
		openEm();

		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(DespesaReceita.class);
		
		criteria.add(Restrictions.eq("tipoBean", t));

		List<DespesaReceita> results = criteria.list();

		closeEm();

		return results;
	}

	@Override
	public List<DespesaReceita> consultaDinamica(DespesaReceita dr) {
		openEm();

		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(DespesaReceita.class);

		// POR ENQUANTO, SOMENTE FARA CONSULTAS POR DESCRICAO!
		if (dr.getDescricao() != null)
			criteria.add(Restrictions.ilike("descricao", dr.getDescricao(),
					MatchMode.ANYWHERE));
		if (dr.getTipoBean() != null)
			criteria.add(Restrictions.eq("tipoBean", dr.getTipoBean()));

		List<DespesaReceita> results = criteria.list();

		closeEm();

		return results;
	}
}
