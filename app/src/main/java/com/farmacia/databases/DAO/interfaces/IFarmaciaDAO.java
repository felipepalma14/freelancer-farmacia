package com.farmacia.databases.DAO.interfaces;

import com.farmacia.models.Farmacia;
import com.farmacia.models.Usuario;

import java.util.List;

/**
 * Created by Felipe Palma on 22/06/2019.
 */
public interface IFarmaciaDAO {

    public Farmacia getFarmaciaPorId(int id);

    public List<Farmacia> getTodasFarmacias();

    // add user
    public boolean adicionarFarmacia(Farmacia farmacia);


}
