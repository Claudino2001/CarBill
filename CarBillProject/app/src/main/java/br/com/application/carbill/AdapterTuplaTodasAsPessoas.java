package br.com.application.carbill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterTuplaTodasAsPessoas extends ArrayAdapter<ClassPessoa> {

    private final Context context;
    private final ArrayList<ClassPessoa> pessoas;

    public AdapterTuplaTodasAsPessoas(Context context, ArrayList<ClassPessoa> pessoas){
        super(context, R.layout.tupla_contatos, pessoas);
        this.context = context;
        this.pessoas = pessoas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.tupla_contatos, parent, false);

        TextView txtLetras = (TextView) rowView.findViewById(R.id.txtLetras);
        TextView txtNomeCompleto = (TextView) rowView.findViewById(R.id.txtNomeCompleto);
        TextView textApelido = (TextView) rowView.findViewById(R.id.textApelido);

        String primeira_letra, segunda_letra;
        String nome = pessoas.get(position).getNome();
        String sobrenome = pessoas.get(position).getSobrenome();

        primeira_letra = String.valueOf(nome.charAt(0));
        segunda_letra = String.valueOf(sobrenome.charAt(0));
        txtLetras.setText(primeira_letra.toUpperCase()+segunda_letra.toUpperCase());

        txtNomeCompleto.setText(nome + " " + sobrenome);
        textApelido.setText(pessoas.get(position).getApelido());

        return rowView;
    }
}