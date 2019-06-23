package com.farmacia.databases.DAO.interfaces;

import com.farmacia.models.Cidade;
import com.farmacia.models.Usuario;

import java.util.List;

/**
 * Created by Felipe Palma on 22/06/2019.
 */
public interface ICidadeDAO {

    public Cidade getCidadePorId(int cidadeId);

    public List<Cidade> getTodasCidades();

    /*
    // add user
    public boolean adicionarUsuario(Usuario usuario);


    public boolean fazerLogin(String login, String senha);

    public boolean deleteAllUsers();
    */
}
