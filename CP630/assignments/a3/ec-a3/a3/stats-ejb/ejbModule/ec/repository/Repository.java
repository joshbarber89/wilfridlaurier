package ec.repository;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.Ecuser;
import ec.euser.Euser;

/**
 * Session Bean implementation class Repository
 */
@Stateless
@LocalBean
public class Repository implements RepositoryRemote, RepositoryLocal {
    @PersistenceContext(unitName="primary")
    private EntityManager entityManager;
    
    @EJB
    private Ecuser validation;
    /**
     * Default constructor. 
     */
    public Repository() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public String getAllUsers() {
		if (validation.valid() && validation.role() == 1) {
	        List<Euser> users = entityManager.createNamedQuery("Euser.allUsers", Euser.class)
	                .getResultList();
	        String usersToString = "";
	        for(int i = 0; i < users.size(); i++) {
	        	Euser user = users.get(i);
	        	usersToString += user.toString();
	        }
	        return usersToString;
		} else {			
			return "";
		}
		
	}

}
