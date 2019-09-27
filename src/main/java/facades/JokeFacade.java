/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Joke;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author benja
 */
public class JokeFacade {

    private static JokeFacade instance;
    private static EntityManagerFactory emf;

    private JokeFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static JokeFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new JokeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Joke> getAllJokes() {
        TypedQuery<Joke> query = getEntityManager().createQuery("SELECT j FROM Joke j", Joke.class);
        return query.getResultList();
    }

    public Joke getJokeById(long id) {
        
    TypedQuery<Joke> query = getEntityManager().createQuery("SELECT j FROM Joke j where j.id = :id", Joke.class);
     return query.setParameter("id", id).getSingleResult();
    }

    public Joke getRandomJoke() {
        Random ran = new Random();
        List jokes = getAllJokes();
        return (Joke) jokes.get(ran.nextInt(jokes.size()));
    }

}
