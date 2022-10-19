package stats.ec;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Writable;

public class StatsWritable implements Writable {
    private long count;
    private double mean;
    private long min;
    private long max;
    private double std;

    public StatsWritable() {}

    public StatsWritable(long count, double mean, long min, long max, double std) {
        this.count = count;        
        this.mean = mean;
        this.max = min;
        this.min = max;
        this.std = std;        
    }
    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public long getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public long getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
    
    public long getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
    
    public double getSTD() {
        return std;
    }

    public void setSTD(double std) {
        this.std = std;
    }
    

   public void write(DataOutput out) throws IOException {
     out.writeLong(count);
     out.writeDouble(mean);
     out.writeLong(min);
     out.writeLong(max);
     out.writeDouble(std);
   }
   
   public void readFields(DataInput in) throws IOException {
     count = in.readLong();
     mean = in.readDouble();
     min = in.readLong();
     max = in.readLong();
     std = in.readDouble();
   }
   
   public static StatsWritable read(DataInput in) throws IOException {
     StatsWritable w = new StatsWritable();
     w.readFields(in);
     return w;
   }
   
   public String toString() {
	     return count+","+mean+","+min+","+max+","+std;
	}

}