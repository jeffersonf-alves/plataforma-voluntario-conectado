package com.jefferson.api_gestao_voluntarios.exceptions;

public class UserFoundException extends RuntimeException{
    public UserFoundException() {
        super("Usuário já existe!");
    }
}
