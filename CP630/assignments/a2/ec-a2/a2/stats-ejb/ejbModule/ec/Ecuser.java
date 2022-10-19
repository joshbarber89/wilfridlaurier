package ec;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.logging.Logger;

import ec.euser.Euser;

@Singleton
@LocalBean
public class Ecuser implements EcuserLocal {
    private static final Logger LOGGER = Logger.getLogger(Ecuser.class);
    private Euser user;
    private Boolean valid = false;
    private Integer role = 3;
    
    @PersistenceContext(unitName="primary")
    private EntityManager entityManager;


    /**
     * Default constructor. 
     */
    public Ecuser() {
    }

    @Override
    public Boolean validate(String username, String password) {
        List<Euser> users = entityManager.createNamedQuery("Euser.login", Euser.class)
                .setParameter("name", username)
                .setParameter("password", password)
                .getResultList();
        if (users.size() == 1) {
        	this.user = users.get(0);
        	this.valid = true;
        	this.role = user.getRole();        	
        	return true;        
        }
    	return false;
    }

	@Override
	public Boolean valid() {		
		return this.valid;
	}

	@Override
	public Integer role() {		
		return this.role;
	}

	@Override
	public Euser user() {		
		return this.user;
	}
}