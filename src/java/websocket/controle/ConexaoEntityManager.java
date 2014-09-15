/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket.controle;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import util.ExceptionPersonalizada;
import websocket.entidade.LocalMaquina;

/**
 *
 * @author rubensmarcon
 */
public class ConexaoEntityManager {

    //private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("MonitoramentoPU");
    private static final EntityManager entityManager = Persistence.createEntityManagerFactory("MonitoramentoPU").createEntityManager();

    public ConexaoEntityManager() {
    }
    
    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static void inserir(LocalMaquina local) throws ExceptionPersonalizada {
        try {System.out.println(local);
            //EntityManagerFactory factory = Persistence.createEntityManagerFactory("MonitoramentoPU");
            //EntityManager entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            System.out.println(local);
            //entityManager.createNativeQuery("INSERT INTO monitorador.local (nome, url) VALUES ('" + local.getNome() + "','" + local.getUrl() + "')").executeUpdate();
            entityManager.persist(local);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            //entityManager.close();
            throw new ExceptionPersonalizada(ex);
        }
        //entityManager.close();
    }

    public static void deletar(Object objeto) throws ExceptionPersonalizada {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(objeto);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            //entityManager.close();
            throw new ExceptionPersonalizada(ex);
        }
        //entityManager.close();
    }
    
    public static void alterar(Object objeto) throws ExceptionPersonalizada {
        try {
            entityManager.getTransaction().begin();
            entityManager.refresh(objeto);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            //entityManager.close();
            throw new ExceptionPersonalizada(ex);
        }
        //entityManager.close();
    }

}
