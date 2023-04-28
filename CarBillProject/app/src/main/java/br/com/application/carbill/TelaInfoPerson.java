package br.com.application.carbill;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TelaInfoPerson extends AppCompatActivity {

    public TextView txtNome, txtApelido, txtDividaTotal, txtValorPorViagem;
    public Button btnQuitarDivida;
    public ListView listViewHistorico;
    String strNome;
    int id;

    //BANCO
    private SQLiteDatabase banco;
    private static final String DATABASE_NAME = "banco_de_dados_carbill";

    public PessoaResumoTelaInfo pessoa;
    public ArrayList<HistoricoDeViagem> viagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_info_person);

        txtNome = (TextView) findViewById(R.id.txtNome);
        txtApelido = (TextView) findViewById(R.id.txtApelido);
        txtDividaTotal = (TextView) findViewById(R.id.txtDividaTotal);
        txtValorPorViagem = (TextView) findViewById(R.id.txtValorPorViagem);
        listViewHistorico = (ListView) findViewById(R.id.listViewHistorico);
        btnQuitarDivida = (Button) findViewById(R.id.btnQuitarDivida);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id_pessoa");
        }

        //System.out.println(">>>> ID RECEBIDO: " + id);
        //Toast.makeText(this, "id_pessoa: " + id, Toast.LENGTH_SHORT).show();

        listarInformacoes();

        btnQuitarDivida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitarConfirmacao();
            }
        });

    }

    public void listarInformacoes(){
        try{
            banco = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            // ---------------------------------------------------------------------------------------//
            // ------------------ APRESENTANDO AS INFORMAÇÕES DA PESSOA NA TELA  ---------------------//
            // ---------------------------------------------------------------------------------------//
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
            // ---------------------------------------------------------------------------------------//
            // ------------------   CONSULTANDO O QUANTO A PESSOA DEVE NO TOTAL ---------------------//
            // ---------------------------------------------------------------------------------------//
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

            // ---------------------------------------------------------------------------------------//
            // ------------------   POVOANDO A LISTA DE HITÓRICO DE VIAGENS --------------------------//
            // ---------------------------------------------------------------------------------------//
            Cursor CursorTres = banco.rawQuery("SELECT id_viagem, id_tipo, data, valor FROM TB_VIAGEM where id_pessoa = " + pessoa.getId_pessoa() + ";", null);

            viagens = new ArrayList<HistoricoDeViagem>();

            if(CursorTres.moveToFirst()){
                do{
                    int id_viagem = CursorTres.getInt((int) CursorTres.getColumnIndex("id_viagem"));
                    int id_tipo = CursorTres.getInt((int) CursorTres.getColumnIndex("id_tipo"));
                    String data = CursorTres.getString((int) CursorTres.getColumnIndex("data"));
                    double valor = CursorTres.getDouble((int) CursorTres.getColumnIndex("valor"));

                    HistoricoDeViagem h = new HistoricoDeViagem();
                    h.setId_viagem(id_viagem);
                    h.setId_tipo(id_tipo);
                    h.setData(data);
                    h.setValor(valor);

                    viagens.add(h);
                }while (CursorTres.moveToNext());
            }
            CursorTres.close();

            ArrayAdapter<HistoricoDeViagem> adapter = new AdpterTuplaHistoricoTelaInfo(this, viagens);
            listViewHistorico.setAdapter(adapter);

            banco.close();
        }catch (Exception e){
            Toast.makeText(this, "erro: listarInformacoes", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void solicitarConfirmacao(){
        AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
        msgBox.setTitle("Quitar dívida do carona.");
        msgBox.setIcon(R.drawable.ic_cifrao);
        msgBox.setMessage("Confirme a quitação da dívida do carona.\nAo fazer isso, todas as viagens até o momento serão quitadas e não vão mais constar no histórico.");
        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                quitarDivida();
                listarInformacoes();
                finish();
                startActivity(new Intent(TelaInfoPerson.this, MainActivity.class));
                Toast.makeText(TelaInfoPerson.this, pessoa.getNome() + pessoa.getSobrenome() + " não possui mais dívidas.", Toast.LENGTH_SHORT).show();
            }
        });
        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(TelaInfoPerson.this, "Operação cancelada.", Toast.LENGTH_SHORT).show();
            }
        });
        msgBox.show();
    }

    public void quitarDivida(){
        try {
            banco = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            banco.execSQL("DELETE FROM TB_VIAGEM where id_pessoa = "+ pessoa.getId_pessoa() +";");
            banco.close();
        }catch (Exception e){
            Toast.makeText(this, "erro: quitarDivida()", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}