package com.fonseca;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContaMagicaTest {
    public ContaMagica c1 = new ContaMagica("754654-31", "alex");
    public ContaMagica c2 = new ContaMagica("111111-06", "pEDRO");
    public ContaMagica c3 = new ContaMagica("222222-12", "santos");

    // PARTE DE VERIFICACAO DA TRANSÇAO ENTRE AS CONTAS NO PARAMETRO DE APENAS MUDAR
    // O NOME CONCLUIDO
    @Test
    public void verificaGETConta() {
        // C1,S1
        // TESTANDO PARA O RESULTADO DE UM SALDO<50000;
        c1.deposito(30000);
        Assertions.assertEquals(Categoria.SILVER, c1.getCategoria());

        // C1,S2
        // TESTANDO PARA O RESULTADO DE UM SALDO>=50000;
        c1.deposito(51000);
        Assertions.assertEquals(Categoria.SILVER, c2.getCategoria());
        Assertions.assertEquals(81000, c1.getSaldo());

        // C2,S3
        c1.deposito(1000);
        Assertions.assertEquals(Categoria.GOLD, c1.getCategoria());

        // C2,S4
        c1.deposito(1000000);
        Assertions.assertEquals(Categoria.PLATINUM, c1.getCategoria());

        // C3,S5
        c1.deposito(100);
        Assertions.assertEquals(Categoria.PLATINUM, c1.getCategoria());

        // ==================================================================================================
        // Testes de valor limite - categoria
        // depósito/upgrade
        c3.deposito(25000);
        Assertions.assertEquals(Categoria.SILVER, c3.getCategoria());
        // off-point
        c3.deposito(24999);
        Assertions.assertEquals(Categoria.SILVER, c3.getCategoria());
        // on-point
        c3.deposito(1);
        Assertions.assertEquals(Categoria.GOLD, c3.getCategoria());

        // off-point
        c3.deposito(148513.861386139); // 148513.861386139
        Assertions.assertEquals(Categoria.GOLD, c3.getCategoria());
        // on-point
        c3.deposito(1);
        Assertions.assertEquals(Categoria.PLATINUM, c3.getCategoria());
        // upgrade máximo
        // --------------------------------------------------------------------------------------------------
        // retirada/downgrade
        // on-point
        c3.retirada(100000);
        Assertions.assertEquals(Categoria.PLATINUM, c3.getCategoria());
        // off-point
        c3.retirada(1);
        Assertions.assertEquals(Categoria.GOLD, c3.getCategoria());

        // on-point
        c3.retirada(74999);
        Assertions.assertEquals(Categoria.GOLD, c3.getCategoria());
        // off-point
        c3.retirada(1);
        Assertions.assertEquals(Categoria.SILVER, c3.getCategoria());
        // downgrade máximo
        // ==================================================================================================

        // forçando erro de inserção para evitar transições diretas
        // C3,S5
        // c2.deposito(1000000);
        // Assertions.assertEquals(Categoria.PLATINUM,c2.getCategoria());

        // c2.deposito(1);
        // Assertions.assertEquals(Categoria.PLATINUM,c2.getCategoria());
    }

    @Test
    public void verificaDeposito() {
        // S1,D1,C1
        c1.deposito(1000);
        Assertions.assertEquals(1000, c1.getSaldo());

        // S2,D1
        c1.deposito(50000);
        Assertions.assertEquals(51000, c1.getSaldo());

        // S3,D2
        // estado de transicao entre a conta silver e gold esta com problema na primeira
        // operação de deposito
        // nas seguintes ele ja repara o erro
        c1.deposito(1000);

        // Assertions.assertEquals(52010,c1.getSaldo());
        // S4,D2
        c1.deposito(200000);

        Assertions.assertEquals(254010, c1.getSaldo());

        // S5,D3
        c1.deposito(2000);
        c1.deposito(2000);

        Assertions.assertEquals(258110, c1.getSaldo());
    }

    @Test
    public void verificaSaque() {

        // S9,SQ1
        // VERIFICAR COMO O METODO REAGE AO SACAR UM VALOR NA CONTA SENDO SILVER
        c1.deposito(10000);
        c1.retirada(100);

        Assertions.assertEquals(9900, c1.getSaldo());

        // S8,SQ1
        c2.deposito(25000);
        c2.deposito(1000);

        // arrumamos no código o problema de saque, onde em alguns trechos do código não
        // estava de
        // forma correta implementado
        c2.retirada(25000);
        Assertions.assertEquals(1000, c2.getSaldo());

    }

    @Test
    public void verificaContaSaque() {

        // S8,SQ1,C1
        // VERIFICAR COMO O METODO REAGE AO SACAR UM VALOR NA CONTA SENDO SILVER
        c1.deposito(10000);
        c1.retirada(100);

        Assertions.assertEquals(9900, c1.getSaldo());
        Assertions.assertEquals(Categoria.SILVER, c1.getCategoria());
        // a transicao de um estado de gold para silver apos o
        // saque de um valor que faz o saldo ficar inferior a
        // 25000 apresenta erro
        // S7,SQ1,C2,C1
        c2.deposito(100000);
        c2.deposito(1000);
        // Assertions.assertEquals(Categoria.GOLD,c2.getCategoria());

        c2.retirada(100000);
        // Assertions.assertEquals(1000,c2.getSaldo());
        // Assertions.assertEquals(Categoria.SILVER,c2.getCategoria());

        // S6,

    }

}