package frc.robot;

public class RobotMap {

    public static final int NEO_ENCODER_PPR = 42;

    public static final int TURRET_MOTOR = 7;
    public static final int TURRET_CENTER_SWITCH = 0;
    public static final int TURRET_RIGHT_SWITCH = 1;
    public static final int TURRET_LEFT_SWITCH = 2;
    public static final double TURRET_GEAR_RATIO = 32.0 / 5.0; // 32 : 5 (driver/driven)
    public static final double TURRET_MIN_ANGLE = -45;
    public static final double TURRET_MAX_ANGLE = 45;
}
