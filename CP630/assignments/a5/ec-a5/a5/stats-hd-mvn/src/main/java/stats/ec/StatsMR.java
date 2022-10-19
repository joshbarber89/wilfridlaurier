package stats.ec;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.Text;

public class StatsMR {
	public static void main(String[] args) throws Exception { 
	    Configuration conf = new Configuration();
	  
	    Path output = new Path(args[1]);
        FileSystem hdfs = FileSystem.get(conf);
        if (hdfs.exists(output)) {
            hdfs.delete(output, true);
        }    
	    
	    Job job = Job.getInstance(conf, "simple stats");
	    job.setJarByClass(StatsMR.class);
	    job.setMapperClass(StatsMap.class);
	    job.setCombinerClass(StatsCombine.class);    
	    job.setReducerClass(StatsReduce.class); 

	    job.setMapOutputKeyClass(Text.class);
	    job.setMapOutputValueClass(StatsWritable.class);

	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(StatsWritable.class); 
	 
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
