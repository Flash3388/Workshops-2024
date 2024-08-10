package frc.robot;

public class SimSettings {


    // turret
    public static final int TURRET_MOTOR_COUNT = 1;
    public static final double TURRET_CYLINDER_RADIUS_M = 0.5;
    public static final double TURRET_MASS = 5;
    public static final double TURRET_MOMENT_OF_INERTIA = 0.5 * TURRET_MASS * TURRET_CYLINDER_RADIUS_M;
    public static final double TURRET_MOTOR_TO_TURRET_GEAR_RATIO = RobotMap.TURRET_GEAR_RATIO;
    public static final double TURRET_MIN_ANGLE = RobotMap.TURRET_MIN_ANGLE;
    public static final double TURRET_MAX_ANGLE = RobotMap.TURRET_MAX_ANGLE;
    public static final double TURRET_PHYSICAL_LIMIT_DIFF = 2;
}
