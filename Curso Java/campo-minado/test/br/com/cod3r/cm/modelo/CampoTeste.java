package br.com.cod3r.cm.modelo;

//acessa true e false
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class CampoTeste {
	
	private Campo campo;
	
	@BeforeEach
	void iniciarCampo() {//iniciar a cada metodo chamado
		campo = new Campo(3, 3);
	}
	
	@Test
	void testeVizinhoEsquerda() {
		Campo vizinho = new Campo(3, 2);
		boolean result = campo.adicionarVizinho(vizinho);
		assertTrue(result);
	}
	
	@Test
	void testeVizinhoEmCima() {
		Campo vizinho = new Campo(2, 3);
		boolean result = campo.adicionarVizinho(vizinho);
		assertTrue(result);
	}
	
	@Test
	void testeVizinhoEmBaixo() {
		Campo vizinho = new Campo(4, 3);
		boolean result = campo.adicionarVizinho(vizinho);
		assertTrue(result);
	}
	
	@Test
	void testeVizinhoDiagonalEsquerdaEmCima() {
		Campo vizinho = new Campo(2, 2);
		boolean result = campo.adicionarVizinho(vizinho);
		assertTrue(result);
	}
	
	@Test
	void testeVizinhoDiagonalEsquerdaEmBaixo() {
		Campo vizinho = new Campo(4, 2);
		boolean result = campo.adicionarVizinho(vizinho);
		assertTrue(result);
	}
	
	@Test
	void testeVizinhoDiagonalDireitaEmCima() {
		Campo vizinho = new Campo(2, 4);
		boolean result = campo.adicionarVizinho(vizinho);
		assertTrue(result);
	}
	
	@Test
	void testeVizinhoDiagonalDireitaEmBaixo() {
		Campo vizinho = new Campo(4, 4);
		boolean result = campo.adicionarVizinho(vizinho);
		assertTrue(result);
	}
	
	@Test
	void testeNaoVizinhoMesmaPosicao() {
		Campo vizinho = new Campo(3, 3);
		boolean result = campo.adicionarVizinho(vizinho);
		assertFalse(result);
	}
	
	@Test
	void testeNaoVizinhoMesmaLinha() {
		Campo vizinho = new Campo(3, 5);
		boolean result = campo.adicionarVizinho(vizinho);
		assertFalse(result);
	}
	@Test
	void testeNaoVizinhoDifLinhaEDiagonal() {
		Campo vizinho = new Campo(1, 3);
		boolean result = campo.adicionarVizinho(vizinho);
		assertFalse(result);
	}
	
	@Test
	void testeValorMarcadoPadrão() {
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacao() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacaoDuasVezes() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAbrirCampoNaoMinadoENaoMarcado() {
		assertTrue(campo.abrir());
	}
	
	@Test
	void testeAbrirCampoMarcadoENaoMinado() {
		campo.alternarMarcacao();
		assertFalse(campo.abrir());
	}
	
	@Test
	void testeAbrirCampoMinadoEMarcado() {
		campo.alternarMarcacao();
		campo.minar();
		assertFalse(campo.abrir());
	}
	
	@Test
	void testeAbrirCampoMinadoENaoMarcado() {
		campo.minar();
		assertThrows(ExplosaoException.class, () -> {
			campo.abrir();
		});
	}
	
	@Test
	void testeAbrirCampoComVizinhacaSegura() {
		Campo campo22 = new Campo(2,2);
		Campo campo11 = new Campo(1,1);
		campo22.adicionarVizinho(campo11);
		
		campo.adicionarVizinho(campo22);
		campo.abrir();
		assertTrue(campo22.isAberto() && campo11.isAberto());
	}
	
	@Test
	void testeAbrirCampoComVizinhacaMinada() {
		Campo campo22 = new Campo(2,2);
		campo22.minar();
		Campo campo44 = new Campo(4,4);
		
		campo.adicionarVizinho(campo22);
		campo.adicionarVizinho(campo44);
		campo.abrir();
		assertTrue(!campo22.isAberto() && !campo44.isAberto());
	}
	
	@Test
	void testeAbrirCampoComVizinhacaMinada2() {
		Campo campo22 = new Campo(2,2);
		Campo campo11 = new Campo(1,1);
		Campo campo12 = new Campo(1,2);
		campo12.minar();
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		
		campo.adicionarVizinho(campo22);
		campo.abrir();
		assertTrue(campo22.isAberto() && campo11.isFechado());
	}
}
