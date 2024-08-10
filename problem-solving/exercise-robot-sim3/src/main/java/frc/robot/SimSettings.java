package frc.robot;

public class SimSettings {

    public static final int ARM_MOTOR_COUNT = 1;
    public static final double ARM_MOTOR_TO_ARM_GEAR_RATIO = RobotMap.ARM_GEAR_RATIO;
    public static final double ARM_LENGTH = 1;
    public static final double ARM_MASS = 2;
    public static final double ARM_BOTTOM_MIN_ANGLE = Math.toRadians(RobotMap.ARM_MIN_ANGLE);
    public static final double ARM_BOTTOM_MAX_ANGLE = Math.toRadians(RobotMap.ARM_MAX_ANGLE);
    public static final double ARM_MOMENT_OF_INERTIA = (1 / 3.0) * ARM_MASS * ARM_LENGTH * ARM_LENGTH;
    public static final boolean ARM_SIMULATE_GRAVITY = true;
}
