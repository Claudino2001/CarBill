package br.com.application.carbill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    public Button btnInfoPessoa;
    private SQLiteDatabase banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        criarBancoDeDados();

        buttonCorridaDiaria = (FloatingActionButton) findViewById(R.id.buttonCorridaDiaria);
        buttonAddNewPerson = (FloatingActionButton) findViewById(R.id.buttonAddNewPerson);
        buttonConfig = (ImageButton) findViewById(R.id.buttonConfig);
        //btnInfoPessoa = (Button) findViewById(R.id.btnInfoPessoa);

        ListView lista = (ListView) findViewById(R.id.listviewTelaInicial);
        ArrayAdapter adapter = new AdapterTuplaPessoaTelaInicial(this,addPessoas());
        lista.setAdapter(adapter);

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

    public void criarBancoDeDados(){
    }

}