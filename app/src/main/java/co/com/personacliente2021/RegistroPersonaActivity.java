package co.com.personacliente2021;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.com.personacliente2021.dto.PersonaDTO;
import co.com.personacliente2021.service.persona.PersonaServiceImpl;
import co.com.personacliente2021.service.tipodocumento.TipoDocumentoServiceImpl;
import co.com.personacliente2021.util.ActionBarUtil;


public class RegistroPersonaActivity extends AppCompatActivity {

    @BindView(R.id.txt_documento)
    EditText txtDocumento;

    @BindView(R.id.txt_nombre)
    EditText txtNombre;

    @BindView(R.id.txt_apellido)
    EditText txtApellido;

    @BindView(R.id.spinnerTipoDocumento)
    Spinner spinnerTipoDocumento;

    PersonaDTO persona;

    PersonaDTO personaItem;

    private Integer documentoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_persona);
        ButterKnife.bind(this);
        persona = new PersonaDTO();
        ActionBarUtil.getInstance(this, true).setToolBar(getString(R.string.registro_persona), getString(R.string.insertar));
        onSelectItemSpinner();
        cargarDatosPersona();
    }

    private void cargarDatosPersona() {
        personaItem = (PersonaDTO) getIntent().getSerializableExtra("persona");
        if(personaItem!=null){
        txtDocumento.setText(personaItem.getNumeroDocumento());
        txtNombre.setText(personaItem.getNombre());
        txtApellido.setText(personaItem.getApellido());
        documentoSeleccionado=personaItem.getIdTipoDocumento();
        }
        listarTiposDocumentos();
    }

    private void CargarInformacion(){
        persona.setIdTipoDocumento(documentoSeleccionado);
        persona.setNumeroDocumento(txtDocumento.getText().toString());
        persona.setNombre(txtNombre.getText().toString());
        persona.setApellido(txtApellido.getText().toString());
        persona.setActivo(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistroPersonaActivity.this);
        builder.setCancelable(false);
        builder.setTitle(R.string.confirm);
        builder.setMessage(R.string.confirm_message_guardar_informacion);
        builder.setPositiveButton(R.string.confirm_action, (dialog, which) -> {
            if(personaItem!=null){
                persona.setIdPersona(personaItem.getIdPersona());
                ActualizarInformacion();

            }else{
                GuardarInformacion();
            }
        });
        builder.setNegativeButton(R.string.cancelar, (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void onSelectItemSpinner() {
        spinnerTipoDocumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 documentoSeleccionado = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void listarTiposDocumentos() {
        TipoDocumentoServiceImpl tipoDocumentoService = new TipoDocumentoServiceImpl(this);
        if(personaItem!=null){
            tipoDocumentoService.getTipoDocumento(spinnerTipoDocumento,personaItem.getIdTipoDocumento());
        }else{
            tipoDocumentoService.getTipoDocumento(spinnerTipoDocumento);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            ValidarInformacionRequerida();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean requerirNumeroDocumento() {
        if(txtDocumento.getText()==null||"".equals(txtDocumento.getText().toString())){
            txtDocumento.setError(getString(R.string.requerido));
            return true;
        }else{
            return false;
        }
    }


    public boolean requerirNombre() {
        if(txtNombre.getText()==null ||"".equals(txtNombre.getText().toString())){
            txtNombre.setError(getString(R.string.requerido));
            return true;
        }else{
            return false;
        }

    }
    public boolean requerirApellido() {
        if(txtApellido.getText()==null ||"".equals(txtApellido.getText().toString())){
            txtApellido.setError(getString(R.string.requerido));
            return true;
        }else{
            return false;
        }
    }

    private void ValidarInformacionRequerida(){
        if(requerirNumeroDocumento()==false&&requerirNombre()==false&&requerirApellido()==false){
            CargarInformacion();
        }
    }

    private void ActualizarInformacion(){
        PersonaServiceImpl personaService = new PersonaServiceImpl(this);
        personaService.Update(persona);
        finish();

    }

    private void GuardarInformacion() {
        PersonaServiceImpl personaService = new PersonaServiceImpl(this);
        personaService.insertar(persona);
        finish();

    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}