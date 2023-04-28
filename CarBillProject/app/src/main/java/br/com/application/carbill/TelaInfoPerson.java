package br.com.application.carbill;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TelaInfoPerson extends AppCompatActivity {

    public TextView txtNome, txtApelido, txtDividaTotal, txtValorPorViagem;
    public ListView listViewHistorico;
    String strNome;
    int id;

    //BANCO
    private SQLiteDatabase banco;
    private static final String DATABASE_NAME = "banco_de_dados_carbill";

    public PessoaResumoTelaInfo pessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_info_person);

        txtNome = (TextView) findViewById(R.id.txtNome);
        txtApelido = (TextView) findViewById(R.id.txtApelido);
        txtDividaTotal = (TextView) findViewById(R.id.txtDividaTotal);
        txtValorPorViagem = (TextView) findViewById(R.id.txtValorPorViagem);
        listViewHistorico = (ListView) findViewById(R.id.listViewHistorico);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id_pessoa");
        }

        System.out.println(">>>> ID RECEBIDO: " + id);
        //Toast.makeText(this, "id_pessoa: " + id, Toast.LENGTH_SHORT).show();

        listarInformacoes();

    }

    public void listarInformacoes(){
        try{
            banco = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            Cursor cursor = banco.rawQuery("SELECT id_pessoa, nome, sobrenome, apelido, valor_por_corrida " +
                    " FROM tb_pessoa WHERE id_pessoa = " + id +";", null);
            if(cursor.moveToFirst()){
                do{
                    int id_pessoa = cursor.getInt((int) cursor.getColumnIndex("id_pessoa"));
                    String nome = cursor.getString((int) cursor.getColumnIndex("nome"));
                    String sobrenome = cursor.getString((int) cursor.getColumnIndex("sobrenome"));
                    String apelido = cursor.getString((int) cursor.getColumnIndex("apelido"));
                    double valor_por_corrida = cursor.getDouble((int) cursor.getColumnIndex("valor_por_corrida"));
                    pessoa = new PessoaResumoTelaInfo();
                    pessoa.setId_pessoa(id_pessoa);
                    pessoa.setNome(nome);
                    pessoa.setSobrenome(sobrenome);
                    pessoa.setApelido(apelido);
                    pessoa.setValor_por_corrida(valor_por_corrida);
                }while (cursor.moveToNext());
            }
            cursor.close();

            txtNome.setText(pessoa.getNome() + " " +pessoa.getSobrenome());
            txtApelido.setText("Vulgo " + pessoa.getApelido());
            txtValorPorViagem.setText("Paga R$: " + pessoa.valor_por_corrida + " por viagem");

            Cursor CursorTwo = banco.rawQuery("SELECT SUM(valor) Total FROM TB_PESSOA " +
                    "INNER JOIN tb_viagem ON tb_pessoa.id_pessoa = tb_viagem.id_pessoa " +
                    "INNER JOIN tb_tipo ON tb_viagem.id_tipo = tb_tipo.id_tipo " +
                    "where tb_pessoa.id_pessoa = "+ pessoa.getId_pessoa() + ";", null);

            if(CursorTwo.moveToFirst()){
                do{
                    double total = CursorTwo.getDouble((int) CursorTwo.getColumnIndex("Total"));
                    //System.out.println("MINHA VOIDA::: " + total);
                    pessoa.setDivida_total(total);
                }while (CursorTwo.moveToNext());
            }
            CursorTwo.close();

            txtDividaTotal.setText("Dívida total R$: " + pessoa.getDivida_total());

            banco.close();
        }catch (Exception e){
            Toast.makeText(this, "erro: listarInformacoes", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    
}