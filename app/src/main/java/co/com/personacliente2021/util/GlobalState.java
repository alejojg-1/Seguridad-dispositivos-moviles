package co.com.personacliente2021.util;

import android.app.Application;

import lombok.Data;

@Data
public class GlobalState extends Application {

    private String accessToken;

}

