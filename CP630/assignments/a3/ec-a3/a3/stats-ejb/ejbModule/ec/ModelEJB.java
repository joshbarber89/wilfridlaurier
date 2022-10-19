package ec;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import ec.model.Model;
import weka.classifiers.functions.LinearRegression;

/**
 * Session Bean implementation class ModelEJB
 */
@Stateless
@LocalBean
public class ModelEJB implements ModelEJBRemote, ModelEJBLocal {
    private static final Logger LOGGER = Logger.getLogger(ModelEJB.class);
    
    @EJB
    private Ecuser validation;
    
    @EJB
    private StatsEJBSingletonRemote statsEjb;
    
    @PersistenceContext(unitName="primary")
    private EntityManager entityManager;
    
    /**
     * Default constructor. 
     */
    public ModelEJB() {
        // TODO Auto-generated constructor stub
    }
	@Override
	public StatsSummary getObject() {    	
    	if (validation.valid() && validation.role() <= 3) {
            List<Model> models = entityManager.createNamedQuery("Model.getLatest", Model.class)
            		.setMaxResults(1)
                    .getResultList();
           
            if (models.size() > 0) {
            	Model model = models.get(0);
            	
            	byte[] buf = model.getObject();
    	        if (buf != null) { 
    	        	try {
	    	        	ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));    	
	    	        	StatsSummary statsSummary = (StatsSummary)  objectIn.readObject();
	    	        	return statsSummary;
    	        	}
    	        	catch (Exception ex) {
    	        		LOGGER.debug(ex);
    	        		return null;
    	        	}
    	        }
            }
    	}

		return null;
	}
	
	@Override
	public LinearRegression getLRObject() {    	
    	
        List<Model> models = entityManager.createNamedQuery("Model.getRegression", Model.class)
        		.setMaxResults(1)
                .getResultList();
       
        if (models.size() > 0) {
        	Model model = models.get(0);
        	
        	byte[] buf = model.getObject();
	        if (buf != null) { 
	        	try {
    	        	ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));    	
    	        	LinearRegression statsSummary = (LinearRegression)  objectIn.readObject();
    	        	return statsSummary;
	        	}
	        	catch (Exception ex) {
	        		LOGGER.debug(ex);
	        		return null;
	        	}
	        }
        }
    	

		return null;
	}
	@Override
	public boolean saveObject(String name, double data) {
		try {
			StatsSummary sm = this.getObject();
			if (sm != null) {
				statsEjb.setData(sm);
				statsEjb.addData(data);			
				sm = statsEjb.getData();
				Query query = entityManager.createNativeQuery("INSERT INTO model (name, classname, object) VALUES (:name, :classname, :object);")
						.setParameter("name", name)
						.setParameter("classname", sm.getClass().getName())
						.setParameter("object", sm);
				int result =query.executeUpdate();
				if (result == 1) {
					return true;
				}
			} else {
				statsEjb.addData(data);			
				sm = statsEjb.getData();
				Query query = entityManager.createNativeQuery("INSERT INTO model (name, classname, object) VALUES (:name, :classname, :object);")
						.setParameter("name", "stats")
						.setParameter("classname", sm.getClass().getName())
						.setParameter("object", sm);
				int result = query.executeUpdate();
				if (result == 1) {
					return true;
				}
			}
		} catch (Exception ex) {
			
		}
		return false;
	}

}
