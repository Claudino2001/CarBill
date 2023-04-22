package br.com.application.carbill;

public class PessoaResumoTelaDiaria {
    private String nome;
    private int id_pessoa;

    public PessoaResumoTelaDiaria(String nome, int id_pessoa) {
        this.nome = nome;
        this.id_pessoa = id_pessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(int id_pessoa) {
        this.id_pessoa = id_pessoa;
    }
}
