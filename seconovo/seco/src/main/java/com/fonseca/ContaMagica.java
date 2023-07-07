package com.fonseca;

public class ContaMagica {
    private String numero;
    private String nomeCorrentista;
    private double saldo;
    private Categoria categoria;

    public ContaMagica(String numero, String nomeCorrentista) {
        this.numero = numero;
        this.nomeCorrentista = nomeCorrentista;
        this.saldo = 0.0; 
        this.categoria = Categoria.SILVER;
        verificaNome(nomeCorrentista);
        verificaNroConta(numero);
     }

    public String getNumeroConta(){
        return numero;
    }

    public String getNomeCorrentista(){
        return nomeCorrentista;
    }

    public double getSaldo(){
        return saldo;
    }

    public Categoria getCategoria(){
        return categoria;
    }

    public boolean deposito(double valor){
       
        if (valor <= 0.0){
            return false;
        }

        if (categoria == Categoria.SILVER){
            saldo += valor;
            if(saldo>=50000){categoria = Categoria.GOLD;}
            return true;
        }

        if (categoria == Categoria.GOLD){
            saldo += valor+(valor*0.01);
            if(saldo>=200000){categoria = Categoria.PLATINUM;}
            return true;
        }

        if (categoria == Categoria.PLATINUM ){
            saldo += valor+(valor*0.025);
            return true;
        }

        return false;
    }

    public boolean retirada(double valor){
        if (valor <= 0.0){
            return true;
        }

        if(valor > saldo){
            return false;
        }

        if (categoria == Categoria.PLATINUM){ 
            saldo -= valor;
            if((saldo)<100000){categoria = Categoria.GOLD;}
            return true;
        }

        if (categoria == Categoria.GOLD){ 
            saldo -= valor;
            if((saldo)<25000){categoria = Categoria.SILVER;}
            return true;
        }

        if (categoria == Categoria.SILVER){
            saldo -= valor;
            if((saldo)<25000){categoria = Categoria.SILVER;}
            return true;
        }
       
        return false;
    }

    private void verificaNroConta(String numero){
        int posTraco = numero.indexOf('-');
        String nroStr = numero.substring(0,posTraco);
        int nroConta = Integer.parseInt(nroStr);
        int verificador = Integer.parseInt(numero.substring(posTraco+1));
        
        if (nroConta < 99999 || nroConta > 999999){
            throw new IllegalNumberException();
        }
        int soma = 0;
        for(int i=0;i<nroStr.length();i++){
            soma += (Integer.parseInt(nroStr.charAt(i)+""));
        }
        if (soma != verificador){
            throw new IllegalNumberException();
        }
    }

    @Override
    public String toString() {
        return "ContaMagica [categoria=" + categoria + ", nomeCorrentista=" + nomeCorrentista + ", numero=" + numero
                + ", saldo=" + saldo + "]";
    }

    private void verificaNome(String nome){ 
        if (nome.length() < 3){
            throw new IllegalNameException();
        }
    }
}