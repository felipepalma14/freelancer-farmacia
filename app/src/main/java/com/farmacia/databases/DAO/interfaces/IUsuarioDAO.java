package com.farmacia.databases.DAO.interfaces;

import com.farmacia.models.Usuario;

import java.util.List;

/**
 * Created by Felipe Palma on 22/06/2019.
 */
public interface IUsuarioDAO {

    public Usuario getUsuarioPorId(int usuarioId);

    public List<Usuario> getTodosUsuario();

    // add user
    public boolean adicionarUsuario(Usuario usuario);


    public Usuario fazerLogin(String login, String senha);

    public boolean deleteAllUsers();
}
