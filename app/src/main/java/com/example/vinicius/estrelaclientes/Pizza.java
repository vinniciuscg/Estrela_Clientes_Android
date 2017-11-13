package com.example.vinicius.estrelaclientes;


import java.io.Serializable;

/**
 * Created by Vinicius on 21/10/2017.
 */

public class Pizza implements Serializable {
    String sabor;
    String ingredientes;

    public Pizza(String sabor) {
        this.sabor = sabor;
    }

    public Pizza(String sabor, String ingredientes) {
        this.sabor = sabor;
        this.ingredientes = ingredientes;
    }

    public String getSabor() { return sabor; }

    public void setSabor(String sabor) { this.sabor = sabor; }

    public String getIngredientes() { return ingredientes; }

    public void setIngredientes(String ingredientes) { this.ingredientes = ingredientes; }
}
