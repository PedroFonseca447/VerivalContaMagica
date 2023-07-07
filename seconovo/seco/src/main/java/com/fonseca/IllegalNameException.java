package com.fonseca;

public class IllegalNameException extends RuntimeException {
    public IllegalNameException(){
        super("Numero de conta invalido");
    }
}