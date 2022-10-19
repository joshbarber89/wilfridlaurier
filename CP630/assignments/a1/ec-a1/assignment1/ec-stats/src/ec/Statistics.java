package ec;

public interface Statistics {
	
	/**
	  * @brief Add data to statistics, which then calculates count, min, max, mean and stddev using incremental algorithms. 
	  * 
	  * @param double value  
	  */ 
	public void addData(double value); 
	
	/**
	  * @brief Retrieve count of data. 
	  * 
	  * @return double count 
	  */ 
	public double getCount();
	
	/**
	  * @brief Retrieve minimum value in data. 
	  * 
	  * @return double min 
	  */ 
	public double getMin();
	
	/**
	  * @brief Retrieve maximum value in data. 
	  * 
	  * @return double max 
	  */ 
	public double getMax();
	
	/**
	  * @brief Retrieve mean value in data. 
	  * 
	  * @return double mean
	  */ 
	public double getMean();
	
	/**
	  * @brief Retrieve standard deviation value in data. 
	  * 
	  * @return double std
	  */ 
	public double getSTD();
	
	/**
	  * @brief Compute and update the data, to make sure it is more precise. 
	  * 
	  */ 
	public void stats();
}
