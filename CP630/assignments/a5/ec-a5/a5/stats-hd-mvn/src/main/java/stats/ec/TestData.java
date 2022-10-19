package stats.ec;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TestData {
	public static void main(String[] args) {
		int n = 100;
		try {
			FileWriter fw = new FileWriter("statsdata.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			String content = "";
			
			for (int i=1; i<=n; i++)
				content = content+i+"\n";
			
			bw.write(content);
			bw.close();
			
			FileReader fr = new FileReader("statsdata.txt");			
			Scanner sin = new Scanner(fr);
			while (sin.hasNext()) {
				System.out.println(sin.nextLine());
			}
			System.out.print("\n");
			sin.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
