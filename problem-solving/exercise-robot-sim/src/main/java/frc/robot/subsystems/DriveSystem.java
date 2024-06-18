package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Conversions;
import frc.robot.Robot;

public class DriveSystem extends SubsystemBase {

    WPI_TalonSRX motorR;
    WPI_TalonSRX motorL;
    ADXRS450_Gyro gyro;

    private final Field2d field;
    private final TankDriveSim sim;
    private final DifferentialDriveOdometry odometry;

    public DriveSystem() {
        motorR = new WPI_TalonSRX(0);
        motorL = new WPI_TalonSRX(0);
        gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);

        field = new Field2d();
        SmartDashboard.putData("Field", field);

        if (Robot.isSimulation()) {
            sim = new TankDriveSim(this);
        } else {
            sim = null;
        }

        odometry = new DifferentialDriveOdometry(
                getRotation(),
                getLeftDistancePassedMeters(),
                getRightDistancePassedMeters());
    }

    public Rotation2d getRotation() {
        return new Rotation2d(gyro.getAngle());
    }

    public double getRightDistancePassedMeters() {
        return Conversions.driveSrxDistanceMeters(motorR.getSelectedSensorPosition());
    }

    public double getLeftDistancePassedMeters() {
        return Conversions.driveSrxDistanceMeters(motorL.getSelectedSensorPosition());
    }

    public void move(double right, double left) {
        motorL.set(right);
        motorR.set(left);
    }

    public void stop() {
        motorR.stopMotor();
        motorL.stopMotor();
    }

    @Override
    public void periodic() {
        if (Robot.isSimulation()) {
            sim.update();
        }

        odometry.update(
                getRotation(),
                getLeftDistancePassedMeters(),
                getRightDistancePassedMeters());

        field.setRobotPose(odometry.getPoseMeters());

        SmartDashboard.putNumber("Drive Left Distance", getLeftDistancePassedMeters());
        SmartDashboard.putNumber("Drive Right Distance", getRightDistancePassedMeters());
        SmartDashboard.putNumber("Drive Yaw", gyro.getAngle());
    }
}
