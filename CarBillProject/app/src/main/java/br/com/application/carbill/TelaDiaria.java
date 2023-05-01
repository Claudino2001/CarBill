package br.com.application.carbill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
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
    private ArrayAdapter<PessoaResumoTelaDiaria> tupla_adapter;

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
                finish();
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
        tupla_adapter = new AdapterTuplaPessoaTelaDiaria(this, pessoas);

        try{
            banco = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            Cursor meuCursor = banco.rawQuery("SELECT id_pessoa,apelido FROM tb_pessoa order by apelido desc;", null);

            if (meuCursor.moveToFirst()) {
                do {
                    int id_pessoa = meuCursor.getInt((int) meuCursor.getColumnIndex("id_pessoa"));
                    String nome = meuCursor.getString((int) meuCursor.getColumnIndex("apelido"));
                    PessoaResumoTelaDiaria pessoa = new PessoaResumoTelaDiaria(nome, id_pessoa);
                    pessoas.add(pessoa);
                } while (meuCursor.moveToNext());

                listviewCorridasDiaria.setAdapter(tupla_adapter);
                banco.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return tupla_adapter;
    }

    void registrarCorridaDiaria(){
        for(int i=0; i<tupla_adapter.getCount(); i++){
            String nome = ((PessoaResumoTelaDiaria) tupla_adapter.getItem(i)).getNome();
            boolean ida = ((PessoaResumoTelaDiaria) tupla_adapter.getItem(i)).isIda();
            boolean volta = ((PessoaResumoTelaDiaria) tupla_adapter.getItem(i)).isVolta();
            int id = ((PessoaResumoTelaDiaria) tupla_adapter.getItem(i)).getId_pessoa();

            System.out.println("Nome: " + nome + "Id: "+ id + " Ida: " + ida + " Volta: "+ volta);

            if(ida || volta){
                try{
                    banco = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

                    Cursor meuCursor = banco.rawQuery("SELECT valor_por_corrida FROM tb_pessoa WHERE id_pessoa = " + id +";", null);
                    double val = 0.1f;
                    if (meuCursor.moveToFirst()) {
                        do {
                            val = meuCursor.getDouble((int) meuCursor.getColumnIndex("valor_por_corrida"));
                            System.out.println(">>>VALOR POR CORRIDA DO CARA >>> " + val);
                        } while (meuCursor.moveToNext());
                    }

                    if(ida){
                        String sql = "INSERT INTO tb_viagem(id_pessoa, id_tipo, data, valor) VALUES(?,?,?,?);";
                        SQLiteStatement stmt = banco.compileStatement(sql);
                        stmt.bindString(1, String.valueOf(id));
                        stmt.bindString(2, "1");
                        String data_insert = _ano + "/" + _mes + "/" + _data;
                        stmt.bindString(3, data_insert);
                        stmt.bindString(4, String.valueOf(val));
                        stmt.executeInsert();
                    }
                    if(volta){
                        String sql = "INSERT INTO tb_viagem(id_pessoa, id_tipo, data, valor) VALUES(?,?,?,?);";
                        SQLiteStatement stmt = banco.compileStatement(sql);
                        stmt.bindString(1, String.valueOf(id));
                        stmt.bindString(2, "2");
                        String data_insert = _ano + "/" + _mes + "/" + _data;
                        stmt.bindString(3, data_insert);
                        stmt.bindString(4, String.valueOf(val));
                        stmt.executeInsert();
                    }

                    banco.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}