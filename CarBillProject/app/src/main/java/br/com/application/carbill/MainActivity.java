package br.com.application.carbill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public FloatingActionButton buttonCorridaDiaria, buttonAddNewPerson;
    public ImageButton buttonConfig;
    public TextView textValorTotal;
    public ListView listviewTelaInicial;
    private SQLiteDatabase banco;
    private static final String DATABASE_NAME = "banco_de_dados_carbill";
    ArrayList<PessoaResumoTelaInical> pessoas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCorridaDiaria = (FloatingActionButton) findViewById(R.id.buttonCorridaDiaria);
        buttonAddNewPerson = (FloatingActionButton) findViewById(R.id.buttonAddNewPerson);
        buttonConfig = (ImageButton) findViewById(R.id.buttonConfig);
        listviewTelaInicial = (ListView) findViewById(R.id.listviewTelaInicial);
        textValorTotal = (TextView) findViewById(R.id.textValorTotal);

        criarBancoDeDados();
        //inserirTiposDeViagem();
        listarDadosTelaInicial();
        insetTiposViagem();

        buttonCorridaDiaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TelaDiaria.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        buttonAddNewPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AdicionarPessoa.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });

        buttonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TelaConfiguracoes.class));
                overridePendingTransition(R.anim.slide_in_cima, R.anim.slide_out_baixo);
            }
        });
        
//        buttonConfig.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                registrarTiposDeViagem();
//                return true;
//            }
//        });

        listviewTelaInicial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, TelaInfoPerson.class);
                try{
                    intent.putExtra("id_pessoa", pessoas.get(i).getId_pessoa());

                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "erro: intent.putExtra(id)", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                //System.out.println("\n\nOLHA ISSO VEI: " + pessoas.get(i).getId_pessoa() + "\n\n");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_baixo, R.anim.slide_out_cima);

            }
        });

    }

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
                    "numero VARCHAR (100) NOT NULL," +
                    "valor_por_corrida DECIMAL(10,5) NOT NULL" +
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

            //EXECUTAR SÓ UMA VEZ
            //banco.execSQL("INSERT INTO tb_pessoa (nome, sobrenome, apelido, telefone, rua, bairro, numero) VALUES ('fulano', 'de tal', 'teste', 123, '234', '234', '123');");
            //banco.execSQL("INSERT INTO tb_viagem (id_pessoa, id_tipo, data, valor) VALUES (1, 1, '2023-01-01', 5.50);");
            //banco.execSQL("INSERT INTO tb_tipo (tipo) VALUES ('IDA');");
            //banco.execSQL("INSERT INTO tb_tipo (tipo) VALUES ('VOLTA');");

            banco.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listarDadosTelaInicial(){
        try{
            banco = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            Cursor meuCursor = banco.rawQuery("SELECT TB_PESSOA.id_pessoa, TB_PESSOA.apelido, SUM(valor) Total FROM TB_PESSOA " +
                    "INNER JOIN tb_viagem ON tb_pessoa.id_pessoa = tb_viagem.id_pessoa " +
                    "INNER JOIN tb_tipo ON tb_viagem.id_tipo = tb_tipo.id_tipo " +
                    "group by nome " +
                    "order by Total desc;", null);

            pessoas = new ArrayList<PessoaResumoTelaInical>();

            if (meuCursor.moveToFirst()) {
                do {
                    String nome = meuCursor.getString((int) meuCursor.getColumnIndex("apelido"));
                    double total = meuCursor.getDouble((int) meuCursor.getColumnIndex("Total"));
                    int id_pessoa = meuCursor.getInt((int) meuCursor.getColumnIndex("id_pessoa"));
                    System.out.println(">>>>>>>>>>> ID_PESSOA: " + id_pessoa);
                    PessoaResumoTelaInical pessoa = new PessoaResumoTelaInical(id_pessoa, nome, total);
                    pessoas.add(pessoa);
                } while (meuCursor.moveToNext());

                ArrayAdapter<PessoaResumoTelaInical> adapter = new AdapterTuplaPessoaTelaInicial(this, pessoas);

                double total_das_dividas = 0.0f;
                for(int i =0; i < pessoas.size(); i++){
                    total_das_dividas += pessoas.get(i).getTotal();
                }

                //System.out.println(">>>>> Total das dividas: " + total_das_dividas + "\n");
                listviewTelaInicial.setAdapter(adapter);
                String valorFormatado = NumberFormat.getCurrencyInstance().format(total_das_dividas);
                textValorTotal.setText("TOTAL\n" + valorFormatado);
                banco.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarDadosTelaInicial();
    }
    
    public void registrarTiposDeViagem(){
        try {
            banco = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            banco.execSQL("INSERT INTO tb_tipo (tipo) VALUES ('IDA');");
            banco.execSQL("INSERT INTO tb_tipo (tipo) VALUES ('VOLTA');");
            banco.close();
            Toast.makeText(this, "Tipos de viagem adicionados.", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "erro: registrarTiposDeViagem", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void insetTiposViagem(){
        if(tabelaTipoIsEmpty()){
            try {
                banco = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
                banco.execSQL("INSERT INTO tb_tipo (tipo) VALUES ('IDA');");
                banco.execSQL("INSERT INTO tb_tipo (tipo) VALUES ('VOLTA');");
                banco.close();
//                Toast.makeText(this, "Tipos de viagem adicionados.", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(this, "erro: insetTiposViagem()", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    public boolean tabelaTipoIsEmpty(){
        boolean isEmpty = false;
        ArrayList<Classe_tb_tipo> tipos = new ArrayList<>();
        try{
            banco = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            Cursor cursor = banco.rawQuery("SELECT id_tipo, tipo FROM tb_tipo",null);

            if(cursor.moveToFirst()){
                do{
                    Classe_tb_tipo t;
                    int id_tipo = cursor.getInt((int) cursor.getColumnIndex("id_tipo"));
                    String tipo = cursor.getString((int) cursor.getColumnIndex("tipo"));
                    t = new Classe_tb_tipo(id_tipo, tipo);
                    tipos.add(t);
                }while (cursor.moveToNext());
            }

            if(tipos.size() > 0){
                System.out.println("Povoada/false");
                isEmpty = false;
            }else{
                System.out.println("Nada/Vazia");
                isEmpty = true;
            }

            cursor.close();
            banco.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return isEmpty;
    }
    
}