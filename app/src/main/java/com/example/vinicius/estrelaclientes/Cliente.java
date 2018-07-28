package com.example.vinicius.estrelaclientes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Vinicius on 13/10/2017.
 */

public class Cliente implements Parcelable {
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

    protected Cliente(Parcel in) {
        chave = in.readInt();
        nome = in.readString();
        telefone = in.readString();
        endereco = in.readString();
        referencia = in.readString();
    }

    public static final Creator<Cliente> CREATOR = new Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }

        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(chave);
        parcel.writeString(nome);
        parcel.writeString(telefone);
        parcel.writeString(endereco);
        parcel.writeString(referencia);
    }
}
