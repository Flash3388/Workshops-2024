package frc.robot;

public class Conversions {

    public static double driveSrxDistanceMeters(double rawUnits) {
         return rawUnits / (double) Constants.TALONSRX_MAG_ENCODER_PPR * Constants.DRIVE_WHEEL_CIRCUMFERENCE / Constants.DRIVE_MOTOR_TO_WHEEL_GEAR_RATIO;
    }

    public static double driveSrxMetersToRawUnits(double meters) {
        return meters * Constants.TALONSRX_MAG_ENCODER_PPR / Constants.DRIVE_WHEEL_CIRCUMFERENCE * Constants.DRIVE_MOTOR_TO_WHEEL_GEAR_RATIO;
    }
}
