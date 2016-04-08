/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket.controle;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import util.ExceptionPersonalizada;
import websocket.entidade.Persistente;

/**
 *
 * @author rubensmarcon
 */
public final class ConexaoEntityManager {

    //private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("MonitoramentoPU");
    private static final EntityManager entityManager = Persistence.createEntityManagerFactory("MonitoramentoPU").createEntityManager();

    public ConexaoEntityManager() {
    }
    
    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static void inserir(Persistente objeto) throws ExceptionPersonalizada {
        try {System.out.println(objeto);
            //EntityManagerFactory factory = Persistence.createEntityManagerFactory("MonitoramentoPU");
            //EntityManager entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            System.out.println(objeto);
            //entityManager.createNativeQuery("INSERT INTO monitorador.local (nome, url) VALUES ('" + local.getNome() + "','" + local.getUrl() + "')").executeUpdate();
            entityManager.persist(objeto);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            //entityManager.close();
            throw new ExceptionPersonalizada(ex);
        }
        //entityManager.close();
    }

    public static void deletar(Persistente objeto) throws ExceptionPersonalizada {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(objeto);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            //entityManager.close();
            throw new ExceptionPersonalizada(ex);
        }
        //entityManager.close();
    }
    
    public static void alterar(Persistente objeto) throws ExceptionPersonalizada {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(objeto);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            //entityManager.close();
            throw new ExceptionPersonalizada(ex);
        }
        //entityManager.close();
    }

    public static List<? extends Persistente> listar(Class<? extends Persistente> classe) throws ExceptionPersonalizada {
        List<? extends Persistente> lista;
        /*System.out.println("---1");
        try {
            javax.sql.DataSource a = (javax.sql.DataSource)InitialContext.doLookup("jdbc/monitorador");
            System.out.println(a.getConnection().getMetaData().getURL());
            a.getConnection().getMetaData().getURL().contains("");
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(ConexaoEntityManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("---2");
        System.out.println();*/
        try {
            entityManager.getTransaction().begin();
            //lista = entityManager.createNamedQuery(classe.getSimpleName() + ".findAll").getResultList();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<? extends Persistente> criteria = builder.createQuery( classe );
            
            lista = entityManager.createQuery( criteria ).getResultList();
            entityManager.getTransaction().commit();
            //entityManager.close();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            //entityManager.close();
            throw new ExceptionPersonalizada(e);
        }
    }
    
    public static List<? extends Persistente> listarNamedQuery(Class<? extends Persistente> classe, String namedQuery, Map<String, Object> parametros) throws ExceptionPersonalizada {
        List<? extends Persistente> lista;
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createNamedQuery(classe.getSimpleName() + "." + namedQuery);
            if (parametros != null) {
                for (Map.Entry<String, Object> entry : parametros.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    query.setParameter(key, value);
                }
            }
            lista = query.getResultList();
            entityManager.getTransaction().commit();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            throw new ExceptionPersonalizada(e);
        }
    }
}
