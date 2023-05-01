package br.com.application.carbill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TelaConfiguracoes extends AppCompatActivity {

    public Button btnGitHub, btnContatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_configuracoes);

        btnGitHub = (Button) findViewById(R.id.btnGitHub);
        btnContatos = (Button) findViewById(R.id.btnContatos);

        btnGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse("https://github.com/Claudino2001/CarBill.git"));
                startActivity(myWebLink);
            }
        });

        btnContatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TelaConfiguracoes.this, TelaContatos.class));
            }
        });

    }
}