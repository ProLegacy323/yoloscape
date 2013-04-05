package server.model.objects;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;

import server.Server;
import server.util.Misc;
import server.world.ObjectHandler;

/**
* @author Killamess
* Basic door manipulation
*/
public class Doors {
	private static Doors singleton = null; // the permanent instance of the Door class
	private List<Doors> doors = new ArrayList<Doors>(); // the list of doors
	private File doorFile; // the doors.txt file which contains all the door data
	
	// checks to see if there is already an instance of this class
	public static Doors getSingleton() {
		if (singleton == null) {
			// if there isn't, create one
			singleton = new Doors("./data/doors.txt"); // load doors.txt
		}
		return singleton;
	}
	
	// constructor
	private Doors(String file){
		doorFile = new File(file);
	}
	
	// constructor
	private Doors(int door, int x, int y, int z, int face, int type, int open) {
		this.doorId = door;
		this.originalId = door;
		this.doorX = x;
		this.doorY = y;
		this.originalX = x;
		this.originalY = y;
		this.doorZ = z;
		this.originalFace = face;
		this.currentFace = face;
		this.type = type;
		this.open = open;
	}
	
	// a function to get doors
	private Doors getDoor(int id, int x, int y, int z) {
		// for every door in the list of doors
		for (Doors d : doors) {
			// if the door's ID is equal to the specified one
			if (d.doorId == id) {
				// if the doors position is equal to the specified one
				if (d.doorX == x && d.doorY == y && d.doorZ == z) {
					return d;
				}
			}
		}
		return null;
	}
	
	public boolean handleDoor(int id, int x, int y, int z) {
		// get the door
		Doors d = getDoor(id, x, y, z);
		
		// if there's no door, return false
		if (d == null) {
			return false;
		}
		int xAdjustment = 0, yAdjustment = 0; // the numbers to adjust the door position by
		if (d.type == 0) {
			if (d.open == 0) {
				if (d.originalFace == 0 && d.currentFace == 0) {
					xAdjustment = -1;
				} else if (d.originalFace == 1 && d.currentFace == 1) {
					yAdjustment = 1;
				} else if (d.originalFace == 2 && d.currentFace == 2) {
					xAdjustment = 1;
				} else if (d.originalFace == 3 && d.currentFace == 3) {
					yAdjustment = -1;
				}
			} else if (d.open == 1) {
				if (d.originalFace == 0 && d.currentFace == 0) {
					yAdjustment = 1;
				} else if (d.originalFace == 1 && d.currentFace == 1) {
					xAdjustment = -1;
				} else if (d.originalFace == 2 && d.currentFace == 2) {
					yAdjustment = -1;
				} else if (d.originalFace == 3 && d.currentFace == 3) {
					xAdjustment = 1;
				}
			}
		} else if (d.type == 9) {
			if (d.open == 0) {
				if (d.originalFace == 0 && d.currentFace == 0) {
					xAdjustment = 1;
				} else if (d.originalFace == 1 && d.currentFace == 1) {
					xAdjustment = 1;
				} else if (d.originalFace == 2 && d.currentFace == 2) {
					xAdjustment = -1;
				} else if (d.originalFace == 3 && d.currentFace == 3) {
					xAdjustment = -1;
				}
			} else if (d.open == 1) {
				if (d.originalFace == 0 && d.currentFace == 0) {
					xAdjustment = 1;
				} else if (d.originalFace == 1 && d.currentFace == 1) {
					xAdjustment = 1;
				} else if (d.originalFace == 2 && d.currentFace == 2) {
					xAdjustment = -1;
				} else if (d.originalFace == 3 && d.currentFace == 3) {
					xAdjustment = -1;
				}
			}
		}
		if (xAdjustment != 0 || yAdjustment != 0) {
			Objects o = new Objects(-1, d.doorX, d.doorY, d.doorZ, 0, d.type, 0);
			Server.objectHandler.placeObject(o);
		}
		if (d.doorX == d.originalX && d.doorY ==d.originalY) {
			d.doorX += xAdjustment;
			d.doorY += yAdjustment;
		} else {
			Objects o = new Objects(-1, d.doorX, d.doorY, d.doorZ, 0, d.type, 0);
			Server.objectHandler.placeObject(o);
			d.doorX = d.originalX;
			d.doorY = d.originalY;
		}
		if (d.doorId == d.originalId) {
			if (d.open == 0) {
				d.doorId += 1;
			} else if (d.open == 1) {
				d.doorId -= 1;
			}
		} else if (d.doorId != d.originalId) {
			if (d.open == 0) {
				d.doorId -= 1;
			} else if (d.open == 1) {
				d.doorId += 1;
			}
		}
		Server.objectHandler.placeObject(new Objects(d.doorId, d.doorX, d.doorY, d.doorZ, getNextFace(d), d.type, 0));
		return true;
	}
	
	private int getNextFace(Doors d) {
		int f = d.originalFace;
		if (d.type == 0) {
			if (d.open == 0) {
				if (d.originalFace == 0 && d.currentFace == 0) {
					f = 1;
				} else if (d.originalFace == 1 && d.currentFace == 1) {
					f = 2;
				} else if (d.originalFace == 2 && d.currentFace == 2) {
					f = 3;
				} else if (d.originalFace == 3 && d.currentFace == 3) {
					f = 0;
				} else if (d.originalFace != d.currentFace) {
					f = d.originalFace;
				}
			} else if (d.open == 1) {
				if (d.originalFace == 0 && d.currentFace == 0) {
					f = 3;
				} else if (d.originalFace == 1 && currentFace == 1) {
					f = 0;
				} else if (d.originalFace == 2 && currentFace == 2) {
					f = 1;
				} else if (d.originalFace == 3 && currentFace == 3) {
					f = 2;
				} else if (d.originalFace != d.currentFace) {
					f = d.originalFace;
				}
			}
		} else if (d.type == 9) {
			if (d.open == 0) {
				if (d.originalFace == 0 && d.currentFace == 0) {
					f = 3;
				} else if (d.originalFace == 1 && d.currentFace == 1) {
					f = 2;
				} else if (d.originalFace == 2 && d.currentFace == 2) {
					f = 1;
				} else if (d.originalFace == 3 && d.currentFace == 3) {
					f = 0;
				} else if (d.originalFace != currentFace) {
					f = d.originalFace;
				}
			} else if (d.open == 1) {
				if (d.originalFace == 0 && d.currentFace == 0)  {
					f = 3;
				} else if (d.originalFace == 1 && d.currentFace == 1) {
					f = 2;
				} else if (d.originalFace == 2 && d.currentFace == 2) {
					f = 1;
				} else if (d.originalFace == 3 && d.currentFace == 3) {
					f = 0;
				} else if (d.originalFace != d.currentFace) {
					f = d.originalFace;
				}
			} else if (d.open == 1) {
				if (d.originalFace == 0 && d.currentFace == 0) {
					f = 3;
				} else if (d.originalFace == 1 && d.currentFace == 1) {
					f = 0;
				} else if (d.originalFace == 2 && d.currentFace == 2) {
					f = 1;
				} else if (d.originalFace == 3 && d.currentFace == 3) {
					f = 2;
				} else if (d.originalFace != d.currentFace) {
					f = d.originalFace;
				}
			}
		}
		d.currentFace = f;
		return f;
	}
	
	public void load() {
		long start = System.currentTimeMillis();
		try {
			singleton.processLineByLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Misc.println("Loaded "+ doors.size() +" doors in "+ (System.currentTimeMillis() - start) + "ms.");
	}
	private final void processLineByLine() throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileReader(doorFile));
		try {
			while(scanner.hasNextLine()) {
				processLine(scanner.nextLine());
			}
		} finally {
			scanner.close();
		}
	}
	
	protected void processLine(String line) {
		Scanner scanner = new Scanner(line);
		scanner.useDelimiter(" ");
		try {
			while(scanner.hasNextLine()) {
				int id = Integer.parseInt(scanner.next());
				int x = Integer.parseInt(scanner.next());
				int y = Integer.parseInt(scanner.next());
				int f = Integer.parseInt(scanner.next());
				int z = Integer.parseInt(scanner.next());
				int t = Integer.parseInt(scanner.next());
				doors.add(new Doors(id, x, y, z, f, t, alreadyOpen(id)?1:0));
			}
		} finally {
			scanner.close();
		}
	}
	
	private boolean alreadyOpen(int id) {
		for (int i=0; i < openDoors.length; i++) {
			if (openDoors[i] == id) {
				return true;
			}
		}
		return false;
	}
	
	private int doorId;
	private int originalId;
	private int doorX;
	private int doorY;
	private int originalX;
	private int originalY;
	private int doorZ;
	private int originalFace;
	private int currentFace;
	private int type;
	private int open;
	
	private static int[] openDoors = {
		1504, 1514, 1517, 1520, 1531,
		1534, 2033, 2035, 2037, 2998,
		3271, 4468, 4697, 6101, 6103,
		6105, 6107, 6109, 6111, 6113,
		6115, 6976, 6978, 8696, 8819,
		10261, 10263, 10265, 11708, 11710,
		11712, 11715, 11994, 12445, 13002,
	};
}