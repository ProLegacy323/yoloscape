package server.model.players;

import java.util.Map;
import java.util.HashMap;

import server.model.players.Client;

/***
*@author Figglewatts
*Plays music based upon the coordinates of the player
*/

public class AreaMusic {
	
	public static Map<String, Integer> areaMusic = new HashMap<String, Integer>();
	
	public static void initialize() {
		areaMusic.put(new String("edgeville"), new Integer(49));
	}

	public static void loadAreaMusic(Client c) {
		// Edgeville
		if (c.absX >= 3067 && c.absY <= 3520 && c.absX <= 3103 && c.absY >= 3484) {
			c.getPA().frame74(areaMusic.get("edgeville"));
		}
	}
}