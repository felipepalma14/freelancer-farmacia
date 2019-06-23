package com.farmacia.databases.DAO.interfaces;

import com.farmacia.models.Produto;
import com.farmacia.models.Usuario;

import java.util.List;

/**
 * Created by Felipe Palma on 22/06/2019.
 */
public interface IProdutoDAO {


    public Produto getProdutoPorId(int id);

    public List<Produto> getTodosProdutos();

    // add user
    public boolean adicionarProduto(Produto produto);

}
