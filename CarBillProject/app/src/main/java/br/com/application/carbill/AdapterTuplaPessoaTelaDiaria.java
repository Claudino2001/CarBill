package br.com.application.carbill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterTuplaPessoaTelaDiaria extends ArrayAdapter<PessoaResumoTelaDiaria> {

    private final Context context;
    private final ArrayList<PessoaResumoTelaDiaria> elementos;

    public AdapterTuplaPessoaTelaDiaria(Context context, ArrayList<PessoaResumoTelaDiaria> elementos){
        super(context, R.layout.tupla_corrida_diaria, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.tupla_corrida_diaria, parent, false);
        TextView txt_nome = (TextView) rowView.findViewById(R.id.txt_nome);

        CheckBox checkBoxIda = (CheckBox) rowView.findViewById(R.id.checkBoxIda);
        CheckBox checkBoxVolta = (CheckBox) rowView.findViewById(R.id.checkBoxVolta);

        txt_nome.setText(elementos.get(position).getNome());

        checkBoxIda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                elementos.get(position).setIda(isChecked);
                checkBoxIda.setChecked(elementos.get(position).isIda());
            }
        });

        checkBoxVolta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                elementos.get(position).setVolta(isChecked);
                checkBoxVolta.setChecked(elementos.get(position).isVolta());
            }
        });

        return rowView;
    }


}
