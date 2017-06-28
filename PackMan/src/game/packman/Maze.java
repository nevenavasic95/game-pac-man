package game.packman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Maze {

	ArrayList<String> lines;
	int rows, columns; 
	int width, height;
	public Position packmanPos;
	public Position ghostPos;
	public ArrayList<Position> pills; 
	
	public Maze() {
		// load the lines
		try {
			pills = new ArrayList<Position>();
			lines = new ArrayList<String>();
			Scanner s = new Scanner(new File("mazes/0"));
			int r = 0;
			while (s.hasNextLine()) {
				String line = s.nextLine();
				lines.add(line);
				if (line.contains("4")) {
					ghostPos = new Position(r, line.indexOf('4'));
				}
				if (line.contains("5")){ 
					packmanPos = new Position(r, line.indexOf('5'));
				}
				for(int i=0; i<line.length(); i++){
					if(line.charAt(i)== '2'){
						pills.add(new Position(r,i));
					}
				}
				r++;
			}
			s.close();
			
			rows = lines.size();
			columns = lines.get(0).length();
			
			width = columns*2;
			height = rows*2;
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
//vidi za sta je
	public char charAt(int r, int c) {
		return lines.get(r).charAt(c);
	}

}
