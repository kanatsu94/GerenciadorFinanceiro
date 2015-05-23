package teste;

import model.DespesaReceita;

import org.joda.time.LocalDate;

import persistence.JPAUtil;
import controller.DespesaReceitaController;
import dao.CartaoCreditoDAO;
import dao.CategoriaDAO;
import dao.ContaDAO;
import dao.DespesaReceitaDAO;
import dao.TipoDAO;




public class Teste {
	public static void main(String[] args) {
//		ContaController contaController = new ContaController();
		ContaDAO daoConta = new ContaDAO();
		TipoDAO daoTipo = new TipoDAO();
		CategoriaDAO daoCategoria = new CategoriaDAO();
		LocalDate dataVencimento = new LocalDate();
		CartaoCreditoDAO daoCartao = new CartaoCreditoDAO();
		
		DespesaReceitaDAO dao = new DespesaReceitaDAO();
		
		DespesaReceita dr = dao.procurar(30);
		
		dr.setValor(5200.00);
		dr.setDescricao("TV LED UHD");
		
		DespesaReceitaController controller = new DespesaReceitaController();
		
		controller.atualizar(dr);
		
//		controller.remover(dao.procurar(21));
		
//		List<DespesaReceita> lista = controller.listarTudo(daoTipo.procurar(1));
		
//		controller.salvar("   ", dataVencimento, null, 5500.00, false, daoCategoria.procurar(1), daoTipo.procurar(1), daoConta.procurar(1), daoCartao.procurar(3), 2, false);
		
//		List<DespesaReceita> lista = controller.listarTudo();
		
//		for(DespesaReceita dr : lista){
//			System.out.println("DESCRIÇÃO: " + dr.getDescricao() + "\nVENCIMENTO: " + dr.getDataVencimento());
//		}
		
		
//		dao.removerPorParcelaId(28);
		
//		DespesaReceitaFactory.novaDespesaReceita("TV", dataVencimento, null, 415.00, false, daoCategoria.procurar(2),
//				daoTipo.procurar(1), daoConta.procurar(1), daoCartao.procurar(1), 8);
		
		JPAUtil.closeEntityManagerFactory();
	}
}