package spaceinvaders;

public class TunableParameters {
	public static int ReferenceSpeed;
	public static int ShipSpeed;
	public static int BackgroundSpeed;
	public static int AlienSpeed;
	public static int AlienSpeedMax;
	public static int AlienVerticalSpeed;
	public static int MissileSpeed = 3;
	public static int NumRowsDropToIncreaseAlienSpeed = 2;
	public static int AlienSpeedIncreaseIncrement = 1;
	public static int AlienScore = 10;
	public static int PercentChanceAlienFiresMissile = 1;
	public static int ShipExhaust = 200;
	public static int ShipFiringExhaustCost = 20;
	public static int ScoreDrawXCoordinate = 10;
	public static int ScoreDrawYCoordinate = 10;
	public static String ScoreDrawLabelText = "Score";
	public static int ExhaustDrawXCoordinate = 10;
	public static int ExhaustDrawYCoordinate = 560;
	public static String ExhaustDrawLabelText = "Exhaust";
	public static boolean Instrument = false;
	public static int FPSCalculationIntervalSeconds = 5;
	public static int FPSDrawXCoordinate = 700;
	public static int FPSDrawYCoordinate = 10;
	public static String FPSDrawLabelText = "FPS";
	public static int PaintFPSDrawXCoordinate = 700;
	public static int PaintFPSDrawYCoordinate = 20;
	public static String PaintFPSDrawLabelText = "PFPS";
	public static String PausedText = "Paused - press spacebar to play";
	public static int PausedXCoordinate = 250;
	public static int PausedYCoordinate = 300;
	public static int TargetFPS = 45;
	
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
			TunableParameters.AlienSpeedMax = AlienSpeed * 4;
			TunableParameters.AlienVerticalSpeed = newSpeed * 10;
	}
		
}
