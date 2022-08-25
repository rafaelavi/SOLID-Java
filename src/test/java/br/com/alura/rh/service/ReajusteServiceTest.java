package br.com.alura.rh.service;

import br.com.alura.rh.ValidacaoException;
import br.com.alura.rh.model.Cargo;
import br.com.alura.rh.model.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReajusteServiceTest {

    private ReajusteService reajusteService;
    private Funcionario funcionario;

    @BeforeEach
    public void inicializar(){
        this.reajusteService = new ReajusteService(List.of(new ValidacaoPeriodicidadeEntreReajuste(),
                new ValidacaoPercentualReajuste()));

        this.funcionario = new Funcionario("Ana", "987456", Cargo.ANALISTA, new BigDecimal("1000.00"));
    }

    @Test
    public void validacaoPercentualReajusteMenorQue40Porcento() {
        reajusteService.reajustarSalarioDoFuncionario(funcionario, new BigDecimal("300.00"));

        assertEquals(new BigDecimal("1300.00"), funcionario.getSalario());
    }

    @Test
    public void validacaoPercentualReajusteMaiorrQue40Porcento() {
        assertThrows( ValidacaoException.class,
                () ->  reajusteService.reajustarSalarioDoFuncionario(funcionario, new BigDecimal("500.00")));
    }

    @Test
    public void validacaoPeriodicidadeEntreReajusteComMenosDe6Meses(){
        //é preciso alterar o LocalDate na classe funcionario
        assertThrows( ValidacaoException.class,
                () -> reajusteService.reajustarSalarioDoFuncionario(funcionario, new BigDecimal("300.00")));
    }

    @Test
    public void validacaoPeriodicidadeEntreReajusteComMaisDe6Meses(){
        //é preciso alterar o LocalDate na classe funcionario
        reajusteService.reajustarSalarioDoFuncionario(funcionario, new BigDecimal("300.00"));
        assertEquals(new BigDecimal("1300.00"), funcionario.getSalario());
    }

}