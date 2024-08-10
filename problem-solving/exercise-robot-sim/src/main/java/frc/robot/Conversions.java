package frc.robot;

public class Conversions {

    public static double driveSrxDistanceMeters(double rawUnits) {
         return rawUnits / (double) RobotMap.TALONSRX_MAG_ENCODER_PPR * RobotMap.DRIVE_WHEEL_CIRCUMFERENCE / RobotMap.DRIVE_MOTOR_TO_WHEEL_GEAR_RATIO;
    }

    public static double driveSrxMetersToRawUnits(double meters) {
        return meters * RobotMap.TALONSRX_MAG_ENCODER_PPR / RobotMap.DRIVE_WHEEL_CIRCUMFERENCE * RobotMap.DRIVE_MOTOR_TO_WHEEL_GEAR_RATIO;
    }
}
