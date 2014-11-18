
public class TunableParameters {
	public static int ReferenceSpeed;
	public static int ShipSpeed;
	public static int BackgroundSpeed;
	public static int AlienSpeed;
	public static int AlienVerticalSpeed;
	public static int MissileSpeed = 3;
	public static int AlienScore = 10;
	public static int PercentChanceAlienFiresMissile = 1;
	public static int ShipExhaust = 200;
	public static int ShipFiringExhaustCost = 20;
	
	public static int ScreenWidth = 800;
	public static int ScreenHeight = 600;
	
	static {
		SetReferenceSpeed(1);
	}
	
	public static void SetReferenceSpeed(int newSpeed) {
			TunableParameters.ReferenceSpeed = newSpeed;
			TunableParameters.ShipSpeed = newSpeed * 2;
			TunableParameters.BackgroundSpeed = newSpeed;
			TunableParameters.AlienSpeed = newSpeed * 2;
			TunableParameters.AlienVerticalSpeed = newSpeed * 10;
	}
		
}
