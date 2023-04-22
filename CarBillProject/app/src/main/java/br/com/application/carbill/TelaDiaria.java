package br.com.application.carbill;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TelaDiaria extends AppCompatActivity {

    public String _data, _mes, _ano;
    public TextView textViewDataAtual, txt_nome;
    public ListView listviewCorridasDiaria;
    public Button buttonRegistroDiario;
    private SQLiteDatabase banco;
    public CheckBox checkBoxIda, checkBoxVolta;
    private static final String DATABASE_NAME = "banco_de_dados_carbill";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_diaria);

        textViewDataAtual = (TextView) findViewById(R.id.textViewDataAtual);
        listviewCorridasDiaria = (ListView) findViewById(R.id.listviewCorridasDiaria);
        buttonRegistroDiario = (Button) findViewById(R.id.buttonRegistroDiario);

        obterDataAtual();

        listarDadosCorridaDiaria();

        buttonRegistroDiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarCorridaDiaria();
            }
        });

    }

    public void obterDataAtual(){

        SimpleDateFormat dia = new SimpleDateFormat("dd");
        Date data_dia = new Date();
        String str_dia = dia.format(data_dia);

        SimpleDateFormat mes = new SimpleDateFormat("MM");
        Date data_mes = new Date();
        String str_mes = mes.format(data_mes);

        SimpleDateFormat ano = new SimpleDateFormat("yyyy");
        Date data_ano = new Date();
        String str_ano = ano.format(data_ano);

        //System.out.println("Data formatada: " + str_dia + "/" + str_mes + "/" + str_ano);
        textViewDataAtual.setText(str_dia + "/" + str_mes + "/" + str_ano);
        _data = str_dia; _mes = str_mes; _ano = str_ano;
    }

    public ArrayAdapter<PessoaResumoTelaDiaria> listarDadosCorridaDiaria(){

        ArrayList<PessoaResumoTelaDiaria> pessoas = new ArrayList<PessoaResumoTelaDiaria>();
        ArrayAdapter<PessoaResumoTelaDiaria> adapter = new AdapterTuplaPessoaTelaDiaria(this, pessoas);

        try{
            banco = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            Cursor meuCursor = banco.rawQuery("SELECT id_pessoa,nome FROM tb_pessoa;", null);

            if (meuCursor.moveToFirst()) {
                do {
                    int id_pessoa = meuCursor.getInt((int) meuCursor.getColumnIndex("id_pessoa"));
                    String nome = meuCursor.getString((int) meuCursor.getColumnIndex("nome"));
                    PessoaResumoTelaDiaria pessoa = new PessoaResumoTelaDiaria(nome, id_pessoa);
                    pessoas.add(pessoa);
                } while (meuCursor.moveToNext());

                listviewCorridasDiaria.setAdapter(adapter);
                banco.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return adapter;
    }

    void registrarCorridaDiaria(){
        ArrayAdapter<PessoaResumoTelaDiaria> tuplas = listarDadosCorridaDiaria();
        int position = 1;
        System.out.println("id_pessoa: " + tuplas.getItem(position).getId_pessoa() + "\nNome: " + tuplas.getItem(position).getNome());
        //View rowView = listviewCorridasDiaria.getChildAt(position);
        //checkBoxIda = (CheckBox) rowView.findViewById(R.id.checkBoxIda);
        //checkBoxVolta = (CheckBox) rowView.findViewById(R.id.checkBoxVolta);
        //txt_nome = (TextView) rowView.findViewById(R.id.txt_nome);
        //System.out.println(txt_nome + " " + String.valueOf(checkBoxIda.isChecked()) + " " + String.valueOf(checkBoxVolta.isChecked()));

    }

}