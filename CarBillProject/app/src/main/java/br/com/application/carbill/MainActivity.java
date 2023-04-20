package br.com.application.carbill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public FloatingActionButton buttonCorridaDiaria, buttonAddNewPerson;
    public ImageButton buttonConfig;
    public ListView listviewTelaInicial;
    private SQLiteDatabase banco;
    private static final String DATABASE_NAME = "banco_de_dados_carbill";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        criarBancoDeDados();
        listarDadosTelaInicial();

        buttonCorridaDiaria = (FloatingActionButton) findViewById(R.id.buttonCorridaDiaria);
        buttonAddNewPerson = (FloatingActionButton) findViewById(R.id.buttonAddNewPerson);
        buttonConfig = (ImageButton) findViewById(R.id.buttonConfig);
        listviewTelaInicial = (ListView) findViewById(R.id.listviewTelaInicial);


        //btnInfoPessoa = (Button) findViewById(R.id.btnInfoPessoa);
        /*
            ListView lista = (ListView) findViewById(R.id.listviewTelaInicial);
            ArrayAdapter adapter = new AdapterTuplaPessoaTelaInicial(this,addPessoas());
            lista.setAdapter(adapter);
         */


        buttonCorridaDiaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TelaDiaria.class));
            }
        });

        buttonAddNewPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AdicionarPessoa.class));
            }
        });

        buttonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TelaConfiguracoes.class));
            }
        });

        /*
                btnInfoPessoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TelaInfoPerson.class));
            }
        });
         */
    }

    /*
    private ArrayList<ClassPessoa> addPessoas() {
            ArrayList<ClassPessoa> pessoas = new ArrayList<ClassPessoa>();
            ClassPessoa e = new ClassPessoa("Gabriel", "Claudino", "Dinho", "xxxxxxx", "Rua", "Bairro", "R$: 45,00");
            pessoas.add(e);
            e = new ClassPessoa("Amanda", "Leal", "Amandinha", "xxxxxxx", "Rua", "Bairro", "R$: 14,50");
            pessoas.add(e);
            e = new ClassPessoa("Gabriel", "Claudino", "Rafinha", "xxxxxxx", "Rua", "Bairro", "R$: 7,50");
            pessoas.add(e);
            e = new ClassPessoa("Gabriel", "Claudino", "Pedrinho", "xxxxxxx", "Rua", "Bairro", "R$: 23,45");
            pessoas.add(e);
            e = new ClassPessoa("Amanda", "Leal", "Gabriel", "xxxxxxx", "Rua", "Bairro", "R$: 19,50");
            pessoas.add(e);
            e = new ClassPessoa("Gabriel", "Claudino", "Araujo", "xxxxxxx", "Rua", "Bairro", "R$: 10,50");
            pessoas.add(e);
            e = new ClassPessoa("Gabriel", "Claudino", "Jaspion", "xxxxxxx", "Rua", "Bairro", "R$: 73,25");
            pessoas.add(e);
            return pessoas;
        }
     */

    public void criarBancoDeDados(){
        try{
            banco = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            banco.execSQL("PRAGMA foreign_keys = ON;");
            banco.execSQL("CREATE TABLE IF NOT EXISTS tb_pessoa(" +
                    "id_pessoa INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome VARCHAR (100) NOT NULL," +
                    "sobrenome VARCHAR (100) NOT NULL," +
                    "apelido VARCHAR (100) NOT NULL," +
                    "telefone VARCHAR (100) NOT NULL," +
                    "rua VARCHAR (100) NOT NULL," +
                    "bairro VARCHAR (100) NOT NULL," +
                    "numero VARCHAR (100) NOT NULL" +
                    ");");
            banco.execSQL("CREATE TABLE IF NOT EXISTS tb_viagem(" +
                    "id_viagem INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "id_pessoa INTEGER NOT NULL," +
                    "id_tipo INTEGER NOT NULL," +
                    "data DATE NOT NULL," +
                    "valor DECIMAL(10,5) NOT NULL," +
                    "FOREIGN KEY (id_pessoa) REFERENCES tb_pessoa(id_pessoa)," +
                    "FOREIGN KEY (id_tipo) REFERENCES tb_tipo(id_tipo)" +
                    ");");
            banco.execSQL("CREATE TABLE IF NOT EXISTS tb_tipo(" +
                    "id_tipo INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tipo VARCHAR(10) NOT NULL " +
                    ");");
            banco.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listarDadosTelaInicial(){
        try{
            banco = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            Cursor meuCursor = banco.rawQuery("SELECT nome, SUM(valor) Total FROM TB_PESSOA " +
                    "INNER JOIN tb_viagem ON tb_pessoa.id_pessoa = tb_viagem.id_pessoa " +
                    "INNER JOIN tb_tipo ON tb_viagem.id_tipo = tb_tipo.id_tipo " +
                    "group by nome " +
                    "order by data;", null);
            ArrayList<ClassPessoa> pessoas = new ArrayList<ClassPessoa>();
            ArrayAdapter adapter = new AdapterTuplaPessoaTelaInicial(this, pessoas);
            listviewTelaInicial.setAdapter(adapter);
            meuCursor.moveToFirst();
            while (meuCursor != null){
                //pessoas.add(meuCursor.getClass());
                meuCursor.moveToNext();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}