
public class TunableParameters {
	public static int ReferenceSpeed = 1;
	public static int ShipSpeed = TunableParameters.ReferenceSpeed * 10;
	public static int BackgroundSpeed = TunableParameters.ReferenceSpeed;
	public static int AlienSpeed = TunableParameters.ReferenceSpeed * 2;
	
	public static int ScreenWidth = 800;
	public static int ScreenHeight = 600;
	
	
	public static void SetReferenceSpeed(int newSpeed) {
			TunableParameters.ReferenceSpeed = newSpeed;
			TunableParameters.ShipSpeed = newSpeed * 10;
			TunableParameters.BackgroundSpeed = newSpeed;
			TunableParameters.AlienSpeed = newSpeed * 2;
	}
}
