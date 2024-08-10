package frc.robot;

import edu.wpi.first.math.util.Units;

public class Constants {

    @SuppressWarnings("PointlessArithmeticExpression")
    public static final double DRIVE_MOTOR_TO_WHEEL_GEAR_RATIO = 8.4 / 1; // driver / driven
    public static final double DRIVE_WHEEL_RADIUS_M = Units.inchesToMeters(2.5);
    public static final double DRIVE_WHEEL_CIRCUMFERENCE = 2 * Math.PI * DRIVE_WHEEL_RADIUS_M;

    public static final int TALONSRX_MAG_ENCODER_PPR = 4096;
}
