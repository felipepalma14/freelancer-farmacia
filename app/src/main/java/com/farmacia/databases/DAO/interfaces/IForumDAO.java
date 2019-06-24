package com.farmacia.databases.DAO.interfaces;

import com.farmacia.models.Categoria;
import com.farmacia.models.Forum;

import java.util.List;

/**
 * Created by Felipe Palma on 22/06/2019.
 */
public interface IForumDAO {


    public Forum getForumPorId(int id);

    public List<Forum> getTodosForuns();

    // add user
    public boolean adicionarForum(Forum forum);

}
