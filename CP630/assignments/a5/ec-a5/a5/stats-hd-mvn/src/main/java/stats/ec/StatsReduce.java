package stats.ec;

import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class StatsReduce extends Reducer<Text, StatsWritable, Text, StatsWritable> {
    DoubleWritable avg = new DoubleWritable();

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

		for (StatsWritable val : values) {
			if (isFirst) {
				count = val.getCount();
				mean = val.getMean();
				min = val.getMin();
				max = val.getMax();	
				std = val.getSTD();
				isFirst = false;
			} else {
				pcount = count;
				count = pcount + val.getCount();
				pmean = mean;
				mean = (pmean * pcount + val.getMean() * val.getCount()) / count;				
				max = val.getMax() > max ? val.getCount() : max;				
				min = val.getMin() <  min ? val.getMin() : min;
				std = Math.sqrt(((Math.pow(std, 2) * count) + std) / (pcount));	
			}
		}

		context.write(key, new StatsWritable(count, mean, min, max, std));
	}
}