package com.fonseca;

public class IllegalNumberException extends RuntimeException {
    public IllegalNumberException(){
        super("Numero de conta invalido");
    }
}