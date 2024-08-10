package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class DriveSystem extends SubsystemBase {

    private WPI_VictorSPX leftFront;
    private WPI_TalonSRX leftBack;
    private WPI_VictorSPX rightFront;
    private WPI_VictorSPX rightBack;

    public DriveSystem() {
        leftBack = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_BACK);
        rightFront = new WPI_VictorSPX(RobotMap.DRIVE_RIGHT_FRONT);
        rightBack = new WPI_VictorSPX(RobotMap.DRIVE_RIGHT_BACK);

        rightFront.setInverted(true);

        leftBack.follow(leftFront);
        rightBack.follow(rightFront);
    }

    public void drive(double leftSpeed, double rightSpeed) {
        leftFront.set(leftSpeed);
        rightFront.set(rightSpeed);
    }

    public void stop() {
        leftFront.stopMotor();
        leftBack.stopMotor();
        rightFront.stopMotor();
        rightBack.stopMotor();
    }

    @Override
    public void periodic() {

    }
}
