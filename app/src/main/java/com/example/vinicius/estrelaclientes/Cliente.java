package com.example.vinicius.estrelaclientes;

import java.io.Serializable;

/**
 * Created by Vinicius on 13/10/2017.
 */

public class Cliente implements Serializable {
    private int chave;
    private String nome;
    private String telefone;
    private String endereco;
    private String referencia;

    public Cliente(){}

    public Cliente(int chave, String nome, String telefone, String endereco, String referencia) {
        this.chave = chave;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.referencia = referencia;
    }

    public int getChave() {
        return chave;
    }

    public void setChave(int chave) {
        this.chave = chave;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}
