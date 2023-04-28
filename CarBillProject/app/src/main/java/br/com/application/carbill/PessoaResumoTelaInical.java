package br.com.application.carbill;

public class PessoaResumoTelaInical {
    private String nome;
    private double Total;
    private int id_pessoa;
    public int getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(int id_pessoa) {
        this.id_pessoa = id_pessoa;
    }

    public String getNome() {
        return nome;
    }
    public double getTotal() {
        return Total;
    }
    public PessoaResumoTelaInical(int id_pessoa, String nome, double total) {
        this.id_pessoa = id_pessoa;
        this.nome = nome;
        this.Total = total;
    }
}
