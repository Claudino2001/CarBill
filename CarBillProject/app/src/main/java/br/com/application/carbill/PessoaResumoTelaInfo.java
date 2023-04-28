package br.com.application.carbill;

public class PessoaResumoTelaInfo {

    int id_pessoa;
    String nome;
    String sobrenome;
    String apelido;
    double valor_por_corrida;
    double divida_total;

    public PessoaResumoTelaInfo() {
    }

    public int getId_pessoa() {
        return id_pessoa;
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

    public double getValor_por_corrida() {
        return valor_por_corrida;
    }

    public double getDivida_total() {
        return divida_total;
    }

    public void setId_pessoa(int id_pessoa) {
        this.id_pessoa = id_pessoa;
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

    public void setValor_por_corrida(double valor_por_corrida) {
        this.valor_por_corrida = valor_por_corrida;
    }

    public void setDivida_total(double divida_total) {
        this.divida_total = divida_total;
    }
}
