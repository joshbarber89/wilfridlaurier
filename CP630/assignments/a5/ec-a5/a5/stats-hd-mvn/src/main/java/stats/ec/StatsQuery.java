package stats.ec;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class StatsQuery {

	public static void main(String[] args) {

		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream("C:/enterprise/tmp/model/hdstats.bin"));
			StatsSummary ss = new StatsSummary();
			ss = 	(StatsSummary) is.readObject();
			is.close();
			System.out.print(ss.toString());

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
