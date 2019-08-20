import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;




public class Algorithm {
	private static Random random = new Random();
	private static double ENVIRONMENT_HARDNESS = 0.5;
	private static double MUTATION_CHANCE = 0.03;
	private static int CHILDREN_COUNT = 1000;
	private static int ITERATION_COUNT = 1000;
	private static double crossOver = 0.7;
	private static double MUTATION_RATE = 0.03;

	private ArrayList<Order> orders = new ArrayList<>();
	private Seller[] sellers = {new Seller(41049792000000l, 29003031000000l, "Kýrmýzý"),
			new Seller(41069940000000l, 29019250000000l, "Yeþil"),
			new Seller(41049997000000l, 29026108000000l, "Mavi")};
	private ArrayList<int[]> children = new ArrayList<int[]>();

	public Algorithm() throws IOException {
		orders = new ReadFile().read();
		for(int i = 0 ; i < CHILDREN_COUNT ; i++) {
			int[] child = new int[100];
			for(int j = 0 ; j < 100 ; j++) {
				child[j] = random.nextInt(3);
			}
			children.add(child);

		}




		for(int currentIteration = 0 ; currentIteration < ITERATION_COUNT ; currentIteration++ )
			iterate();

		printPopulation();

	}

	private void printPopulation() {
		String print ="";
		int minor = 0;
		double distance = Double.MAX_VALUE;
		for(int j = 0; j < children.size(); j++) {
			int[] child = children.get(j);
			print += j + ": [ ";
			double dist = fitness(child);
			if(dist < distance) {
				distance = dist;
				minor = j;
			}
			for(int i = 0 ; i < child.length ; i++) {
				print += child[i] + ",";
			}
			print +="]" + "Distance: " + dist +"\n";
		}
		System.out.println(print);
		System.out.println(minor);
		printMinor(minor);
	}

	private void printMinor(int minor) {
		int[] child = children.get(minor);
		String print = "";
		print += ": [ ";
		for(int i = 0 ; i < child.length ; i++) {
			if(child[i] == 0) {
				print += "KIRMIZI" + ",";
			}
			if(child[i] == 1) {
				print += "YEÞÝL" + ",";
			}
			
			if(child[i] == 2) {
				print += "MAVÝ" + ",";
			}
			
			
		}
		System.out.println(print);
	}

	private void iterate() {
		ArrayList<ChildInfo> childInfo = new ArrayList<ChildInfo>();
		for(int i = 0; i< children.size() ; i++) {
			int[] child = children.get(i);
			double fit = fitness(child);
			int j = 0;
			boolean last = false;
			while(childInfo.size() > 0 && j < childInfo.size() && fit > childInfo.get(j).getFitnessPoint())
				j++;
			last = j == childInfo.size();
			if(last)
				childInfo.add(new ChildInfo(i, fit));
			else
				childInfo.add(j,new ChildInfo(i, fit));


		}
		ArrayList<int[]> newChildren = new ArrayList<int[]>();
		for(int live = (int)(childInfo.size()*ENVIRONMENT_HARDNESS) ; live >= 0; live--) {
			newChildren.add(children.get(childInfo.get(live).getChild()));
		}
		children = populateChildren(newChildren);
	}



	private ArrayList<int[]> populateChildren(ArrayList<int[]> newChildren) {
		ArrayList<int[]> finalChildren = newChildren;
		for (int i = 0; i < newChildren.size()-1; i++) {
			int[] parent1 = newChildren.get(i);
			int[] parent2 = newChildren.get(i+1);
			int crossOverPoint = (int)(crossOver * parent1.length);

			int[] child1 = new int[parent1.length];
			int[] child2 = new int[parent2.length];

			for (int j = 0; j < crossOverPoint; j++) {
				child1[j] = parent1[j];
				child2[j] = parent2[j];

			}
			for (int j = crossOverPoint; j <parent1.length ; j++) {
				child1[j] = parent2[j];
				child2[j] = parent1[j];

			}

			int x = random.nextInt(100)+1;
			if(x<= (MUTATION_CHANCE*100)) {
				int a = random.nextInt((int) (MUTATION_RATE*100))+1;
				for (int z = 0; z <a; z++) {
					int degisecekIndex = random.nextInt(100);
					int rand = random.nextInt(3);
					while (rand == child1[degisecekIndex])
						rand = random.nextInt(3);
					child1[degisecekIndex] = rand;
				}
			}

			int y = random.nextInt(100)+1;
			if(y<= (MUTATION_CHANCE*100)) {
				int a = random.nextInt((int) (MUTATION_RATE*100))+1;
				for (int z = 0; z <a; z++) {
					int degisecekIndex = random.nextInt(100);
					int rand = random.nextInt(3);
					while (rand == child2[degisecekIndex])
						rand = random.nextInt(3);
					child2[degisecekIndex] = rand;
				}
			}

			if(finalChildren.size() < CHILDREN_COUNT)
				finalChildren.add(child1);
			if(finalChildren.size() < CHILDREN_COUNT)
				finalChildren.add(child2);

		}
		return finalChildren;
	}

	private double fitness(int[] child) {
		int green = 0;
		int red = 0;
		int blue = 0;
		double distance = 0;
	
		for(int j = 0; j < child.length ; j++) {
			switch(child[j]) {
			case 0:
				red++;
				distance += calcDist(orders.get(j), 0);
				break;
			case 1:
				green++;
				distance += calcDist(orders.get(j), 1);
				break;
			case 2:
				blue++;
				distance += calcDist(orders.get(j),2);
				break;
			}
		}
		boolean deathCheck = (red <20 || red > 30) || (green < 35 || green > 50) || (blue < 20 || blue> 80);
		if(deathCheck)
			return Double.MAX_VALUE;
		return distance;
	}

	private double calcDist(Order order, int i) {
		Seller dis = sellers[i];
		return Math.sqrt(Math.pow((dis.getAttitude() - order.getLatitude()), 2)+Math.pow((dis.getLongtitude() - order.getLongtitude()), 2));
	}



}
