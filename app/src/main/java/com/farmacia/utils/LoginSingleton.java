package com.farmacia.utils;

import com.farmacia.models.Usuario;

/**
 * Created by Felipe Palma on 22/06/2019.
 */
public class LoginSingleton {
    private static LoginSingleton mLoginSingleton;

    private static Usuario mUsuario;

    //private constructor.
    private LoginSingleton(){

        //Prevent form the reflection api.
        if (mLoginSingleton != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }


    public static LoginSingleton getInstance(){
        if (mLoginSingleton == null){ //if there is no instance available... create new one
            mLoginSingleton = new LoginSingleton();
        }

        return mLoginSingleton;
    }

    public  void setUsuarioAutenticado(Usuario usuario){
        mLoginSingleton.mUsuario = usuario;
    }

    public  Usuario getUsuarioAutenticado(){
        return mUsuario;
    }
}
