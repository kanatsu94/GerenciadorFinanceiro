package dao;

import java.util.List;

import javax.persistence.Query;

import model.CartaoCredito;

public class CartaoCreditoDAO extends GenericDAO<CartaoCredito>{
	public CartaoCreditoDAO(){
		super(CartaoCredito.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<CartaoCredito> listaAtivos(){
		openEm();

		try {
			em.getTransaction().begin();

			Query q = em.createNamedQuery(CartaoCredito.LISTAR_ATIVOS);
			
			// O METODO EXECUTEUPDATE() RETORNA A QUANTIDADE DE LINHAS AFETADAS
			// NO BANCO DE DADOS.
			List<CartaoCredito> lista = q.getResultList();

			em.getTransaction().commit();

			return lista;
		} catch (Exception e) {
			em.getTransaction().rollback();
			return null;
		} finally {
			closeEm();
		}
	}
}
