package game.packman;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;



public class GameData {

	int mazeNo = 0;
	CopyOnWriteArrayList<Position> pills;
	CopyOnWriteArrayList<Position> powerPills;
	public MoverInfo packman;
	public GhostInfo[] ghostInfos = new GhostInfo[4];
	public int score;
	Maze mazes;
	boolean dead = false;
	boolean completed = false;
	
	public GameData() {
		mazes = new Maze();
		
		//mazeNo =0;
		setMaze(0);
	}
	
	// set the packman, list of pills and 4 ghosts
	private void setMaze(int m) {
		packman = new MoverInfo(mazes.packmanPos);
		for (int g=0; g<4; g++)
		 {
			ghostInfos[g] = new GhostInfo(mazes.ghostPos);
		 }
		pills = new CopyOnWriteArrayList ((List<Position>)(mazes.pills.clone()));
		powerPills = new CopyOnWriteArrayList ((List<Position>)(mazes.powerPills.clone()));
	}


	public void movePackMan(int reqDir) {
		if(move(reqDir , packman)){
			packman.curDir = reqDir;
		} else {
			move(packman.curDir,packman);
		}
	}
	
	
	
	private int wrap (int value, int incre, int max)
	{
		return (value+max+incre)%max;
	}
	
	
	private boolean move(int reqDir, MoverInfo info) {
		// current position of packman is (row, column)
		
		int row = info.pos.row;
		int column = info.pos.column;
		int rows = mazes.rows;
		int columns = mazes.columns;
		int nrow = wrap(row, MoverInfo.DROW[reqDir], rows);
		int ncol = wrap(column, MoverInfo.DCOL[reqDir], columns);
		
		if(mazes.charAt(nrow, ncol) != '0' ){
			info.pos.row = nrow;
			info.pos.column =ncol;
			return true;
		}
			
		
		return false;
	}
	
	public void update() {
		if(pills.contains(packman.pos)){
			pills.remove(packman.pos);
			score +=5;
		} else if (powerPills.contains(packman.pos)) {
			powerPills.remove(packman.pos);
			score += 5;
			for(GhostInfo g: ghostInfos){
				g.edibleCountDown = 500;
			}
		}
		for(GhostInfo g: ghostInfos)
		{
			if(g.edibleCountDown > 0)
			{
				if(touching(g.pos, packman.pos))
				{
					//eat the ghost and reset
					score +=10;
					g.curDir = g.reqDir = MoverInfo.LEFT;
					g.pos.row =mazes.ghostPos.row;
					g.pos.column = mazes.ghostPos.column;
					g.edibleCountDown = 0;
				}
				g.edibleCountDown--;
			} else {
				if(touching(g.pos, packman.pos))
				{
					dead = true;
				}
			}
		}
		//level is cleared
		if(pills.isEmpty()&&powerPills.isEmpty()){
			System.out.println("end");
			completed = true;
			dead = true;
		}
		
	}
	private boolean touching(Position a, Position b) {
		return Math.abs(a.row-b.row) + Math.abs(a.column-b.column )<3;
	}


	public void moveGhosts(int[] reqDirs) {
		for(int i=0; i<4; i++)
		{
			GhostInfo info = ghostInfos[i];
			info.reqDir = reqDirs[i];
			if(move(info.reqDir, info)) {
				info.curDir = info.reqDir;
			} else {
				move(info.curDir,info);
			}
		}
	}
	public int getWidth() {
		return mazes.width;
	}
	
	public int getHeight() {
		return mazes.height;
	}


	public List<Integer> getPossibleDirs(Position pos) {
		List<Integer> list = new ArrayList<Integer>();
		for(int d=0; d<4; d++)
		{
			Position npos = getNextPositionInDir(pos,d);
			if(mazes.charAt(npos.row, npos.column)!= '0')
			{
				list.add(d);
			}
		}
		return list;
	}


	private Position getNextPositionInDir(Position pos, int d) {
		int nrow= wrap(pos.row, MoverInfo.DROW[d], mazes.rows);
		int ncol= wrap(pos.column, MoverInfo.DCOL[d], mazes.columns);
		return new Position(nrow, ncol);
	}
	
}