package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class Tabuleiro {
	
	private int qtdLinhas;
	private int qtdColunas;
	private int qtdMinas;
	
	private final List<Campo> campos = new ArrayList<>();

	public Tabuleiro(int qtdLinhas, int qtdColunas, int qtdMinas) {
		this.qtdLinhas = qtdLinhas;
		this.qtdColunas = qtdColunas;
		this.qtdMinas = qtdMinas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	public void abrir(int linha, int coluna) {
		try {
			campos.stream()
				.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
				.findFirst()
				.ifPresent(c -> c.abrir());
		} catch (ExplosaoException e) {
			campos.stream().forEach(c -> c.setAberto(true));
			throw e;
		}
	}
	
	public void alternarMarcacao(int linha, int coluna) {
		campos.stream()
		.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst()
		.ifPresent(c -> c.alternarMarcacao());
	}
	
	private void gerarCampos() {
		for (int linha = 0; linha < qtdLinhas; linha++) {
			for (int coluna = 0; coluna < qtdColunas; coluna++) {
				campos.add(new Campo(linha, coluna));
			}
		}
	}

	private void associarVizinhos() {
		for(Campo campo1: campos) {
			for(Campo campo2: campos) {
				campo1.adicionarVizinho(campo2);
			}
		}
		
	}

	private void sortearMinas() {
		long minasSorteadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		
		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasSorteadas = campos.stream().filter(minado).count();
			
		}while(minasSorteadas < qtdMinas);
	} 
	
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}
	
	
	public String toString() {
		int aux = 0;//preciso disso pq to percorrendo uma lista, não uma matriz
		StringBuilder sb = new StringBuilder();
		
		sb.append("  ");
		for (int coluna = 0; coluna < qtdColunas; coluna++) {
			sb.append(" ");
			sb.append(coluna);
			sb.append(" ");
		}
		
		sb.append("\n");
		
		for (int linha = 0; linha < qtdLinhas; linha++) {
			
			sb.append(linha);
			sb.append(" ");
			
			for (int coluna = 0; coluna < qtdColunas; coluna++) {
				sb.append(" ");
				sb.append(campos.get(aux));
				sb.append(" ");
				aux++;
			}
			sb.append("\n");
			
		}
		return sb.toString();
	}
	
	
}
