package br.com.application.carbill;

import java.text.DecimalFormat;

public class ClassPessoa {
    private String nome, sobrenome, apelido, telefone, rua, bairro, numero;
    private double divida_total = 0.0f;

    public ClassPessoa(String nome, String sobrenome, String apelido, String telefone, String rua, String bairro, String numero) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.apelido = apelido;
        this.telefone = telefone;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
    }

    public double getDivida_total() {
        return divida_total;
    }

    public void setDivida_total(double divida_total) {
        this.divida_total = divida_total;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getApelido() {
        return apelido;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getRua() {
        return rua;
    }

    public String getBairro() {
        return bairro;
    }

    public String getNumero() {
        return numero;
    }
}
