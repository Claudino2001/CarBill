package br.com.application.carbill;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class AdapterTuplaPessoaTelaInicial extends ArrayAdapter<PessoaResumoTelaInical> {

    private final Context context;
    private final ArrayList<PessoaResumoTelaInical> elementos;

    public AdapterTuplaPessoaTelaInicial(Context context, ArrayList<PessoaResumoTelaInical> elementos){
        super(context, R.layout.tupla_pessoa_e_divida_total, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.tupla_pessoa_e_divida_total, parent, false);
        TextView txt_apelido = (TextView) rowView.findViewById(R.id.txt_apelido);
        TextView txt_divida_total = (TextView) rowView.findViewById(R.id.txt_divida_total);

        txt_apelido.setText(elementos.get(position).getNome());
        String valorFormatado = NumberFormat.getCurrencyInstance().format(elementos.get(position).getTotal());
        txt_divida_total.setText(String.valueOf(valorFormatado));

        return rowView;
    }
}
