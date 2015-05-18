package dao;

import interfaces.DaoDespesaReceita;

import javax.persistence.Query;

import model.DespesaReceita;

public class DespesaReceitaDAO extends GenericDAO<DespesaReceita> implements DaoDespesaReceita{
	public DespesaReceitaDAO(){
		super(DespesaReceita.class);
	}
	
	@Override
	public int removerPorParcelaId(int parcelaId){
		int i = 0;
		openEm();
		
		try {
			em.getTransaction().begin();
			
			Query q = em.createNamedQuery(DespesaReceita.REMOVER_POR_PARCELA_ID);
			q.setParameter("parcela_id", parcelaId);
			
			i = q.executeUpdate();
			
			em.getTransaction().commit();
			
			return i;
		} catch (Exception e) {
			em.getTransaction().rollback();
			return i;
		}finally{
			closeEm();
		}
	}
}
