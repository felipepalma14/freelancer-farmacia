package com.farmacia.databases.DAO.interfaces;

import com.farmacia.models.Categoria;

import java.util.List;

/**
 * Created by Felipe Palma on 22/06/2019.
 */
public interface IForumDAO {


    public Categoria getCategoriaPorId(int id);

    public List<Categoria> getTodasCategorias();

    // add user
    //public boolean adicionarCategoria(Categoria categoria);

}
