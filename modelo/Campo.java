package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class Campo {
	
	private final int linha;
	private final int coluna;
	
	private boolean minado = false;
	private boolean marcado = false;
	private boolean aberto = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDif = linha != vizinho.linha;
		boolean colunaDif = coluna != vizinho.coluna;
		boolean diagonal = linhaDif && colunaDif;
		
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaLinha + deltaColuna;
		//posso ter um cenário em que seja da mesma linha ou coluna e 
		//esteja a dois campos de distância: n eh vizinho, mas estaria
		//como fosse
		if(deltaGeral == 1 && !diagonal) { 
			vizinhos.add(vizinho);
			return true;
		}else if(deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		}
		else {
			return false;
		}
	}
	
	void alternarMarcacao() {
		if(!aberto) {
			marcado = !marcado;
		}
	}
	
	boolean abrir() {
		
		if(!aberto && !marcado) {
			aberto = true;
			if(minado) {
				throw new ExplosaoException();
			}
		
			if(vizinhacaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			
			return true;
		}else {
			return false;
		}
		
	}
	
	boolean vizinhacaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	boolean isMarcado() {
		return marcado;
	}
	
	boolean minar() {
		return minado = true;
	}
	
	boolean isMinado() {
		return minado;
	}
	
	public void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	boolean isAberto() {
		return aberto;
	}
	
	boolean isFechado() {
		return !aberto;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = aberto && !minado;
		boolean protegido = !aberto && marcado;
		
		return desvendado || protegido;
	}
	
	long minasNaVizinhaca() {
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	public String toString() {
		if(marcado) {
			return "x";
		}else if(aberto && minado) {
			return "*";
		}else if(aberto && minasNaVizinhaca() > 0) {
			return Long.toString(minasNaVizinhaca());
		}else if(aberto) {
			return " ";
		}else {
			return "?";
		}
	}
}
