package ec;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ConverterUtils.DataSource;

public class RegressionModel {
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
		
		//initiate linear regressioon
		LinearRegression cls;		
		if (!modelFile.equals("")) {
			cls = (LinearRegression) weka.core.SerializationHelper.read("model/" + modelFile);			
		} else {
			cls = new LinearRegression();
		}
		
		//training dataset
		Instances trainingDataSet = null;
		if (!trainFile.equals("")) {		
			trainingDataSet = DataSource.read("data/" + trainFile);
			trainingDataSet.setClassIndex(trainingDataSet.numAttributes()-1);
		}
		
		//testing dataset
		Instances testingDataSet = null;
		if (!testFile.equals("")) {
			testingDataSet = DataSource.read("data/" + testFile);	
			testingDataSet.setClassIndex(testingDataSet.numAttributes()-1);	
		}
		

		
		if (trainingDataSet != null) {
			// set attribute selection method, no method
			SelectedTag method = new SelectedTag(LinearRegression.SELECTION_NONE, LinearRegression.TAGS_SELECTION);
			cls.setAttributeSelectionMethod(method); 
			//cls.setEliminateColinearAttributes(false);
			

			if (testingDataSet != null) {
				
			    //get coefficients of the linear regression model
				double[] lmCoeffs = cls.coefficients();
				System.out.println(Arrays.toString(lmCoeffs));
			
				// model testing
				Evaluation eval = new Evaluation(trainingDataSet);
				eval.evaluateModel(cls, testingDataSet);
				System.out.println("Linear Regression Model Evaluation");
				System.out.println(eval.toSummaryString());

				
				if (!outputFile.equals("") && outputFile.contains("txt")) {
			        try {
			            FileWriter writer = new FileWriter("results/" + outputFile, false);
			            writer.write(eval.toSummaryString());
			            writer.close();
			        } catch (IOException e) {
			            e.printStackTrace();
			        }					
				}
			} else {
				//training the model
				cls.buildClassifier(trainingDataSet);
				System.out.println(cls);
			}
		} 		 
		
		//save model
		if (!outputFile.equals("") && !outputFile.contains("txt")) {
			weka.core.SerializationHelper.write("model/" + outputFile, cls);
		}

		
	}
}
