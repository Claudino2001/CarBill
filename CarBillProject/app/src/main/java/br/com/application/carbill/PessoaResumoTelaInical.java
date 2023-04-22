package br.com.application.carbill;

public class PessoaResumoTelaInical {
    private String nome;
    private double Total;
    public String getNome() {
        return nome;
    }
    public double getTotal() {
        return Total;
    }
    public PessoaResumoTelaInical(String nome, double total) {
        this.nome = nome;
        this.Total = total;
    }
}
