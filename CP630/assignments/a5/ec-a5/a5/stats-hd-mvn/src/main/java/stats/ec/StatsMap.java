package stats.ec;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class StatsMap extends Mapper<Object, Text, Text, StatsWritable> {
    private Text key = new Text("stats");
    
    Text name = new Text();
    String[] row;

    protected void map(Object offSet, Text line, Context context) throws IOException, InterruptedException {
      row = line.toString().split(" ");
      double a = Double.parseDouble(row[0]);
      System.out.print(a);
      context.write(key, new StatsWritable(1, a, (long)a, (long)a, (long)a));
    }
}