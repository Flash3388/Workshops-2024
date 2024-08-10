package frc.robot;

public class SimSettings {

    public static final int ELEVATOR_MOTOR_COUNT = 1;
    public static final double ELEVATOR_DRUM_RADIUS_M = RobotMap.ELEVATOR_DRUM_RADIUS_M;
    public static final double ELEVATOR_MOTOR_TO_ELEVATOR_GEAR_RATIO = RobotMap.ELEVATOR_GEAR_RATIO;
    public static final double ELEVATOR_MIN_HEIGHT = RobotMap.ELEVATOR_MIN_HEIGHT;
    public static final double ELEVATOR_MAX_HEIGHT = RobotMap.ELEVATOR_MAX_HEIGHT;
    public static final double ELEVATOR_CARRIAGE_MASS = 3;
    public static final boolean ELEVATOR_SIMULATE_GRAVITY = true;
    public static final double ELEVATOR_STARTING_HEIGHT = ELEVATOR_MIN_HEIGHT;
    public static final double ELEVATOR_PHYSICAL_LIMIT_DIFF = 0.1;
}
