import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ReadFile {
	File file;
	public ReadFile() {
		file = new File("siparis ve bayi koordinatlarý.csv");
	}
	
	public ArrayList<Order> read() throws IOException {
		FileReader fr = new FileReader(file);
		ArrayList<Order> result = new ArrayList<Order>();
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		while((line=br.readLine()) != null) {
			String[] ins = line.split(";");
			Order order = new Order(Integer.parseInt(ins[0]),Long.parseLong(ins[1].replace(".","")), Long.parseLong(ins[2].replace(".","")));
			result.add(order); 
		}
		
		return result;
	}
}
