import util.ConfigReader;

/**
* @author Figglewatts
* A class to keep track of certain client variables by reading them from a .cfg file
*/
public class Config {
	// the config reader which will be used to get the values
	private static ConfigReader cReader = new ConfigReader("./data/config.cfg");
	
	public static void ReadConfig() {
		System.out.println("Reading config file");
		try {
			// read the config file and assign to all variables here
			MIDI_PACKING_ENABLED = GetBooleanFromString((String)cReader.getProperty("MIDI_PACKING"));
		} catch (Exception e) {
			System.out.println("ERROR: misconfigured Config.cfg");
			e.printStackTrace();
		}
	}
	
	public static void ShowConfigInfo() {
		if (MIDI_PACKING_ENABLED == true) {
			System.out.println("MIDI packing is enabled, disable unless you know what you are doing. Edit /data/config.cfg to disable.");
		}
	}
	
	private static boolean GetBooleanFromString(String input) {
		System.out.println("Getting boolean from string: " + input + ".");
		input = input.trim();
		if (input.equalsIgnoreCase("true"))
		{
			System.out.println("Returning true.");
			return true;
		}
		else
		{
			System.out.println("Returning false.");
			return false;
		}
	}
	
	public static boolean MIDI_PACKING_ENABLED;
}