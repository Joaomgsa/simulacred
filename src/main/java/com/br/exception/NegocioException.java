package com.br.exception;

public class NegocioException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public NegocioException(String msg){
        super(msg);
    }
    public static void throwIf(Boolean condicao, String msg){
        if (condicao) {
            throw new NegocioException(msg);
        }
    }
}
