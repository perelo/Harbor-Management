package cas2.controler.dao;

import java.io.Serializable;
import java.util.List;

import cas2.controler.CExc;

public interface DAO<T, ID extends Serializable> {

    /**
     * Permet la suppression d'un tuple de la base
     * 
     * @param obj
     */
    public void delete(T obj) throws CExc;

    /**
     * Permet de récupérer tous les objets d'une table
     * 
     * @return
     */
    public List<T> findAll();

    /**
     * Permet de récupérer un objet via son ID
     * 
     * @param id
     * @return
     */
    public T getById(ID id);

    /**
     * Permet de créer une entrée dans la base de données par rapport
     * à un objet
     * 
     * @param obj
     * @throws CExc 
     */
    public T create(T obj) throws CExc;

    /**
     * Permet de mettre à jour les données d'un tuple dans la base à partir
     * d'un objet passé en paramètre
     * 
     * @param obj
     */
    public void update(T obj) throws CExc;

}