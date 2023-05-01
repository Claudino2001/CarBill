package br.com.application.carbill;

import java.text.DecimalFormat;

public class ClassPessoa {
    private String nome, sobrenome, apelido, telefone, rua, bairro;

    int numero;
    int id;
    private double divida_total = 0.0f;

    private double valor_por_corrida = 0.0f;

    public ClassPessoa() {
    }

    public ClassPessoa(int id, String nome, String sobrenome, String apelido) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.apelido = apelido;
    }

    public ClassPessoa(String nome, String sobrenome, String apelido, String telefone, String rua, String bairro, int numero, double valor_por_corrida) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.apelido = apelido;
        this.telefone = telefone;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.valor_por_corrida = valor_por_corrida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor_por_corrida() {
        return valor_por_corrida;
    }

    public void setValor_por_corrida(double valor_por_corrida) {
        this.valor_por_corrida = valor_por_corrida;
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

    public void setNumero(int numero) {
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

    public int getNumero() {
        return numero;
    }
}
