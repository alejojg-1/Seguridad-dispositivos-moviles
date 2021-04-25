package co.com.personacliente2021.service.persona;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import co.com.personacliente2021.adapter.PersonaAdapter;
import co.com.personacliente2021.dto.PersonaDTO;
import co.com.personacliente2021.model.Persona;
import co.com.personacliente2021.service.persona.respuesta.RespuestaPersona;
import co.com.personacliente2021.util.CustomResponse;
import co.com.personacliente2021.util.Parametro;
import co.com.personacliente2021.util.RetrofitFactory;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

 public class PersonaServiceImpl extends RetrofitFactory {



     public PersonaServiceImpl(Context context){
         super(context);
     }




    public void getPersona(ListView listViewPersonas){
        Retrofit retrofit =  getTokenInstance();
        PersonaClient client = retrofit.create(PersonaClient.class);
        Call<List<Persona>> response = client.getPersonas();
        response.enqueue(new Callback<List<Persona>>() {
            @Override
            public void onResponse(Call<List<Persona>> call, Response<List<Persona>> response) {
                List<Persona> listaPersonas = response.body();
                if(listaPersonas != null){
                    PersonaAdapter personaAdapter = new PersonaAdapter(getContext(),listaPersonas);
                    listViewPersonas.setAdapter(personaAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Persona>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de comunicación: "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

     public void insertar(PersonaDTO persona) {
         Retrofit retrofit =  getTokenInstance();
         PersonaClient client = retrofit.create(PersonaClient.class);
         Call<CustomResponse<RespuestaPersona>> response =client.insertar(Parametro.CONTENT_TYPE_APPLICATION_JSON,persona);
         response.enqueue(new Callback<CustomResponse<RespuestaPersona>>() {
             @Override
             public void onResponse(Call<CustomResponse<RespuestaPersona>> call, Response<CustomResponse<RespuestaPersona>> response) {
                 CustomResponse<RespuestaPersona> respuestaPersona = response.body();
                 if(respuestaPersona != null && respuestaPersona.getValor() != null){
                     respuestaPersona.getValor();
                 }else{
                     response.raw().code();
                 }             }

             @Override
             public void onFailure(Call<CustomResponse<RespuestaPersona>> call, Throwable t) {
                 Toast.makeText(getContext(), "Error de comunicación: "+ t.getMessage(), Toast.LENGTH_LONG).show();
             }
         });
     }

     public void Update( PersonaDTO persona) {
         Retrofit retrofit =   getTokenInstance();
         PersonaClient client = retrofit.create(PersonaClient.class);
         Call<CustomResponse<RespuestaPersona>> response =client.UpdatePersona(Parametro.CONTENT_TYPE_APPLICATION_JSON,persona.getIdPersona(),persona);
         response.enqueue(new Callback<CustomResponse<RespuestaPersona>>() {
             @Override
             public void onResponse(Call<CustomResponse<RespuestaPersona>> call, Response<CustomResponse<RespuestaPersona>> response) {
                 CustomResponse<RespuestaPersona> respuestaPersona = response.body();
                 if(respuestaPersona != null && respuestaPersona.getValor() != null){
                     respuestaPersona.getValor();
                 }else{
                     response.raw().code();
                 }             }

             @Override
             public void onFailure(Call<CustomResponse<RespuestaPersona>> call, Throwable t) {
                 Toast.makeText(getContext(), "Error de comunicación: "+ t.getMessage(), Toast.LENGTH_LONG).show();
             }
         });
     }

     public void eliminar(PersonaDTO persona) {
         Retrofit retrofit =  getTokenInstance();
         PersonaClient client = retrofit.create(PersonaClient.class);
         Call<CustomResponse<RespuestaPersona>> response =client.DeletePersona(Parametro.CONTENT_TYPE_APPLICATION_JSON,persona.getIdPersona());
         response.enqueue(new Callback<CustomResponse<RespuestaPersona>>() {
             @Override
             public void onResponse(Call<CustomResponse<RespuestaPersona>> call, Response<CustomResponse<RespuestaPersona>> response) {
                 CustomResponse<RespuestaPersona> respuestaPersona = response.body();
                 if(respuestaPersona != null && respuestaPersona.getValor() != null){
                     respuestaPersona.getValor();
                 }else{
                     response.raw().code();
                 }             }

             @Override
             public void onFailure(Call<CustomResponse<RespuestaPersona>> call, Throwable t) {
                 Toast.makeText(getContext(), "Error de comunicación: "+ t.getMessage(), Toast.LENGTH_LONG).show();
             }
         });
     }
 }
