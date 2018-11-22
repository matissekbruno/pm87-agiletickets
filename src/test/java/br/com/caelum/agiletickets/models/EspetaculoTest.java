package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Test;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}
	
	@Test
	public void deveCriarUmaSessaoCasoDatasDeInicioEFimSejamIguaisPeriodicidadeDiaria() {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate(2018, 11, 05);
		LocalDate fim = new LocalDate(2018, 11, 05);
		LocalTime horario = new LocalTime();
		Periodicidade periodicidade = Periodicidade.DIARIA;
		
		List<Sessao> listaSessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		
		assertTrue(listaSessoes.size() == 1);
		int i = 0;
		for (Sessao sessao : listaSessoes) {
			Assert.assertEquals(sessao.getEspetaculo(), espetaculo);
			Assert.assertEquals(sessao.getInicio(), inicio.plusDays(i*periodicidade.getIntervalo()).toDateTime(horario));
			i++;
		}

	}
	
	@Test
	public void deveCriarCincoSessoesCasoADiferencaEntreDataDeInicioEFimSejaDe4DiasPeriodicidadeDiaria() {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate(2018, 11, 5);
		LocalDate fim = new LocalDate(2018, 11, 9);
		LocalTime horario = new LocalTime();
		Periodicidade periodicidade = Periodicidade.DIARIA;
		
		List<Sessao> listaSessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		
		assertTrue(listaSessoes.size() == 5);
		int i = 0;
		for (Sessao sessao : listaSessoes) {
			Assert.assertEquals(sessao.getEspetaculo(), espetaculo);
			Assert.assertEquals(sessao.getInicio(), inicio.plusDays(i*periodicidade.getIntervalo()).toDateTime(horario));
			i++;
		}
	}
	
	@Test
	public void naoDeveCriarSessoesCasoADataDeInicioSejaMaiorQueADeFimPeriodicidadeDiaria() {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate(2018, 11, 5);
		LocalDate fim = new LocalDate(2018, 11, 4);
		LocalTime horario = new LocalTime();
		Periodicidade periodicidade = Periodicidade.DIARIA;
		
		List<Sessao> listaSessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		
		assertTrue(listaSessoes.size() == 0);

	}
	
	@Test
	public void deveCriarUmaSessaoCasoDatasDeInicioEFimSejamIguaisNaPeriodicidadeSemanal() {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate(2018, 11, 5);
		LocalDate fim = new LocalDate(2018, 11, 5);
		LocalTime horario = new LocalTime();
		Periodicidade periodicidade = Periodicidade.SEMANAL;
		
		List<Sessao> listaSessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		
		assertTrue(listaSessoes.size() == 1);
		int i = 0;
		for (Sessao sessao : listaSessoes) {
			Assert.assertEquals(sessao.getEspetaculo(), espetaculo);
			Assert.assertEquals(sessao.getInicio(), inicio.plusDays(i*periodicidade.getIntervalo()).toDateTime(horario));
			i++;
		}
	}
	
	@Test
	public void deveCriarDuasSessoesCasoADiferencaEntreDataDeInicioEFimSejaDe7DiasPeriodicidadeSemanal() {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate(2018, 11, 5);
		LocalDate fim = new LocalDate(2018, 11, 12);
		LocalTime horario = new LocalTime();
		Periodicidade periodicidade = Periodicidade.SEMANAL;
		
		List<Sessao> listaSessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		
		assertTrue(listaSessoes.size() == 2);
		int i = 0;
		for (Sessao sessao : listaSessoes) {
			Assert.assertEquals(sessao.getEspetaculo(), espetaculo);
			Assert.assertEquals(sessao.getInicio(), inicio.plusDays(i*periodicidade.getIntervalo()).toDateTime(horario));
			i++;
		}
	}
	
	@Test
	public void deveCriarTresSessoesCasoADiferencaEntreDataDeInicioEFimSejaDe20DiasPeriodicidadeSemanal() {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate(2018, 11, 5);
		LocalDate fim = new LocalDate(2018, 11, 25);
		LocalTime horario = new LocalTime();
		Periodicidade periodicidade = Periodicidade.SEMANAL;
		
		List<Sessao> listaSessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		
		assertTrue(listaSessoes.size() == 3);
		int i = 0;
		for (Sessao sessao : listaSessoes) {
			Assert.assertEquals(sessao.getEspetaculo(), espetaculo);
			Assert.assertEquals(sessao.getInicio(), inicio.plusDays(i*periodicidade.getIntervalo()).toDateTime(horario));
			i++;
		}
	}
	
	
	@Test
	public void naoDeveCriarSessoesCasoADataDeInicioSejaMaiorQueADeFimPeriodicidadeSemanal() {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate(2018, 11, 5);
		LocalDate fim = new LocalDate(2018, 11, 4);
		LocalTime horario = new LocalTime();
		Periodicidade periodicidade = Periodicidade.SEMANAL;
		
		List<Sessao> listaSessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		
		assertTrue(listaSessoes.size() == 0);
		
	}
	

	
	
}
