package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {
	
	private static final double PORCENTAGEM_ULTIMOS_INGRESSOS_CINEMA = 0.05;
	private static final double PORCENTAGEM_ULTIMOS_INGRESSOS_SHOW = 0.05;
	private static final double PORCENTAGEM_ULTIMOS_INGRESSOS_BALLET = 0.50;
	private static final double PORCENTAGEM_ULTIMOS_INGRESSOS_ORQUESTRA = 0.50;

	private static final BigDecimal MULTIPLICADOR_PRECO_ULTIMOS_INGRESSOS_CINEMA = BigDecimal.valueOf(0.10);
	private static final BigDecimal MULTIPLICADOR_PRECO_ULTIMOS_INGRESSOS_SHOW = BigDecimal.valueOf(0.10);
	private static final BigDecimal MULTIPLICADOR_PRECO_ULTIMOS_INGRESSOS_BALLET = BigDecimal.valueOf(0.20);
	private static final BigDecimal MULTIPLICADOR_PRECO_ULTIMOS_INGRESSOS_ORQUESTRA = BigDecimal.valueOf(0.20);
	
	private static final BigDecimal MULTIPLICADOR_PRECO_DURACAO_SUPERIOR_UMA_HORA_BALLET = BigDecimal.valueOf(0.10);
	private static final BigDecimal MULTIPLICADOR_PRECO_DURACAO_SUPERIOR_UMA_HORA_ORQUESTRA = BigDecimal.valueOf(0.10);
	
	public static double calculaPorcentagemIngressosRestantes(Sessao sessao) {
		return (sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue();
	}
	
	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
		
		if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.CINEMA) || sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.SHOW)) {
			//quando estiver acabando os ingressos... 
			if(calculaPorcentagemIngressosRestantes(sessao) <= PORCENTAGEM_ULTIMOS_INGRESSOS_CINEMA) { 
				preco = sessao.getPreco().add(sessao.getPreco().multiply(MULTIPLICADOR_PRECO_ULTIMOS_INGRESSOS_CINEMA));
			} else {
				preco = sessao.getPreco();
			}
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.BALLET)) {
			if(calculaPorcentagemIngressosRestantes(sessao) <= PORCENTAGEM_ULTIMOS_INGRESSOS_BALLET) { 
				preco = sessao.getPreco().add(sessao.getPreco().multiply(MULTIPLICADOR_PRECO_ULTIMOS_INGRESSOS_BALLET));
			} else {
				preco = sessao.getPreco();
			}
			
			if(sessao.getDuracaoEmMinutos() > 60){
				preco = preco.add(sessao.getPreco().multiply(MULTIPLICADOR_PRECO_DURACAO_SUPERIOR_UMA_HORA_BALLET));
			}
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.ORQUESTRA)) {
			if(calculaPorcentagemIngressosRestantes(sessao) <= PORCENTAGEM_ULTIMOS_INGRESSOS_ORQUESTRA) { 
				preco = sessao.getPreco().add(sessao.getPreco().multiply(MULTIPLICADOR_PRECO_ULTIMOS_INGRESSOS_ORQUESTRA));
			} else {
				preco = sessao.getPreco();
			}

			if(sessao.getDuracaoEmMinutos() > 60){
				preco = preco.add(sessao.getPreco().multiply(MULTIPLICADOR_PRECO_DURACAO_SUPERIOR_UMA_HORA_ORQUESTRA));
			}
		}  else {
			//nao aplica aumento para teatro (quem vai é pobretão)
			preco = sessao.getPreco();
		} 

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

}