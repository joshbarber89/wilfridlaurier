package ec.lr;

import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.ModelEJBRemote;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Session Bean implementation class LRStateless
 */
@Stateless
@LocalBean
public class LRStateless implements LRStatelessLocal {

	@EJB
	private ModelEJBRemote model;
    /**
     * Default constructor. 
     */
    public LRStateless() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public String predict(String[] attrs) throws Exception {    	
    	   	
        ArrayList<Attribute> atts = new ArrayList<Attribute>();
        
        Instances data;       
        
        double[] vals;       
    	
    	for (int i = 0; i < attrs.length; i++) {    		
    		atts.add(new Attribute("att"+(i+1)));    		
    	}    	
    	
        data=new Instances("MyRelation",atts,0);
        
        vals=new double[data.numAttributes()];

    	for (int i = 0; i < attrs.length; i++) {
    		String attr = attrs[i];
    		vals[i] = Double.parseDouble(attr);    		
    	}    
    	
        
        data.add(new DenseInstance(1,vals));
        System.out.println(data);
        
    	Classifier cls = model.getLRObject();
    	
		Instance predicationDataInstance = data.lastInstance();
		double value = cls.classifyInstance(predicationDataInstance);
		System.out.println(value);

    	
		return Double.toString(value);
    }

}
