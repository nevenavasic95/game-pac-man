package game.packman;

import java.util.Random;
import java.util.List;
public class GhostsCoach {

	Random random = new Random();
	public int[] decide(GameData data) {
		// TODO Auto-generated method stub
		int[] dirs = new int[4];
		for(int i=0; i<4; i++)
		{
			List<Integer> list = data.getPossibleDirs(data.ghostInfos[i].pos);
			list.remove(new Integer(MoverInfo.REV[data.ghostInfos[i].curDir]));
			dirs[i] = list.get(random.nextInt(list.size()));
		}
		
		
		return dirs;
	}

}
