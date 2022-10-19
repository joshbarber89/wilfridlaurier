package stats.ec;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class StatsCombine extends Reducer<Text, StatsWritable, Text, StatsWritable> {
	protected void reduce(Text key, Iterable<StatsWritable> values, Context context)
			throws IOException, InterruptedException {
		long count = 0;
		long pcount = 0;
		double mean = 0;
		double pmean = 0;
		long min = 0;
		long max = 0;
		double std = 0;
		boolean isFirst = true;
		
		double variance = 0;
		double total = 0;
		double total2 = 0;
		for (StatsWritable val : values) {	
			
			if (isFirst) {
				count = val.getCount();
				mean = val.getMean();
				min = val.getMin();
				max = val.getMax();
				total = val.getMean();	
				total2 = val.getMean() * val.getMean();
				isFirst = false;
			} else {
				pcount = count;
				count = pcount + val.getCount();
				pmean = mean;
				mean = (pmean * pcount + val.getMean() * val.getCount()) / count;				
				max = val.getMax() > max ? val.getCount() : max;				
				min = val.getMin() <  min ? val.getMin() : min;
				total += val.getMean();	
				total2 += val.getMean() * val.getMean();
			}
		}
		variance = (count * total2 - total * total) / (count * count);
		std = Math.sqrt(variance);

		context.write(key, new StatsWritable(count, mean, min, max, std));
	}
}
