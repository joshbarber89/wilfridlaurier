package ec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class RegressionPredict {
	public static void main(String[] args) throws Exception {
		
		String outputFile = "";
		String modelFile = "";
		String predictFile = "";
		String testFile = "";
		String trainFile = "";
		
		if (args.length > 0) {
			String arg = "";
			for (int i = 0; i < args.length; i++) {
				if (arg.equals("input")) {
					if (args[i].contains("test")) {
						testFile = args[i];						
					} else if (args[i].contains("unknown")) {
						predictFile = args[i];						
					} else {
						trainFile = args[i];						
					}
					arg = "";
				} else if (arg.equals("output")) {
					outputFile = args[i];
					arg = "";
				} else if (arg.equals("model")) {
					modelFile = args[i];
					arg = "";
				}
				if (args[i].equals("-i")) {
					arg = "input";
				} else if (args[i].equals("-m")) {
					arg = "model";
				} else if (args[i].equals("-o")) {
					arg = "output";
				}
			}
		}
		
		//load model
		LinearRegression cls = (LinearRegression) weka.core.SerializationHelper.read("model/" + modelFile);		
		
		Instances predicationDataSet = DataSource.read("data/" + predictFile);		    
		predicationDataSet.setClassIndex(predicationDataSet.numAttributes()-1);
		
	    // label instances
	    for (int i = 0; i < predicationDataSet.numInstances(); i++) {
	      double clsLabel = cls.classifyInstance(predicationDataSet.instance(i));
	      System.out.println(predicationDataSet.instance(i));
	      predicationDataSet.instance(i).setClassValue(clsLabel);
	    }
	    System.out.println(predicationDataSet.toString());
	    
		if (!outputFile.equals("") && outputFile.contains("txt")) {
	        try {
	            FileWriter writer = new FileWriter("results/" + outputFile, false);
	            writer.write(predicationDataSet.toString());	            
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }					
		}
	}
}
