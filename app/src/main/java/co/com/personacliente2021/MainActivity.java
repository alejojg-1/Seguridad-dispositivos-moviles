package co.com.personacliente2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.com.personacliente2021.dto.PersonaDTO;
import co.com.personacliente2021.model.Persona;
import co.com.personacliente2021.service.persona.PersonaServiceImpl;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.listViewPersonas)
    ListView listViewPersonas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadInformation();
        goToIntent();

    }

    private void goToIntent() {
        listViewPersonas.setClickable(true);
        listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToRegistroPersona(i);

            }
        });
    }

    private void loadInformation() {
        PersonaServiceImpl personaService = new PersonaServiceImpl(this);
        personaService.getPersona(listViewPersonas);
    }


    public void goToRegistroPersona(View view) {
        Intent intent = new Intent(MainActivity.this,RegistroPersonaActivity.class);
        startActivity(intent);
    }

    public void goToRegistroPersona(int i) {
        Persona personaSelecionado = (Persona)listViewPersonas.getItemAtPosition(i);
        PersonaDTO personaDTO = new PersonaDTO();
        personaDTO.setIdPersona(personaSelecionado.getIdPersona());
        personaDTO.setIdTipoDocumento(personaSelecionado.getTipoDocumento().getIdTipoDocumento());
        personaDTO.setNumeroDocumento(personaSelecionado.getNumeroDocumento());
        personaDTO.setNombre(personaSelecionado.getNombre());
        personaDTO.setApellido(personaSelecionado.getApellido());
        Intent intent = new Intent(this,RegistroPersonaActivity.class);
        intent.putExtra("persona", personaDTO);
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        loadInformation();
    }
}