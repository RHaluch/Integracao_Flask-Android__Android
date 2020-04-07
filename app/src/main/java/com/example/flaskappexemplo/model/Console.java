package com.example.flaskappexemplo.model;

public class Console {
    private long id;
    private String name;
    private int year;
    private double price;
    private boolean ativo;
    private int quantidade_jogos;

    public Console() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getQuantidade_jogos() {
        return quantidade_jogos;
    }

    public void setQuantidade_jogos(int quantidade_jogos) {
        this.quantidade_jogos = quantidade_jogos;
    }

    @Override
    public String toString() {
        return name;
    }
}
