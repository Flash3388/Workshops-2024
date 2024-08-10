package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAlternateEncoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.sim.TurretSim;

public class TurretSystem extends SubsystemBase {

    private final CANSparkMax motor;
    private final RelativeEncoder encoder;

    private final DigitalInput leftSwitch;
    private final DigitalInput centerSwitch;
    private final DigitalInput rightSwitch;

    private final TurretSim sim;

    public TurretSystem() {
        motor = new CANSparkMax(RobotMap.TURRET_MOTOR, CANSparkLowLevel.MotorType.kBrushless);
        encoder = motor.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, RobotMap.NEO_ENCODER_PPR);

        leftSwitch = new DigitalInput(RobotMap.TURRET_LEFT_SWITCH);
        centerSwitch = new DigitalInput(RobotMap.TURRET_CENTER_SWITCH);
        rightSwitch = new DigitalInput(RobotMap.TURRET_RIGHT_SWITCH);

        sim = new TurretSim(motor);
    }

    public double getAngleDegrees() {
        return encoder.getPosition() * RobotMap.TURRET_GEAR_RATIO * 360;
    }

    public boolean isAtLeft() {
        return !leftSwitch.get();
    }

    public boolean isAtCenter() {
        return !centerSwitch.get();
    }

    public boolean isAtRight() {
        return rightSwitch.get();
    }

    public void move(double speed) {
        if (isAtRight() || isAtLeft()) {
            stop();
        } else {
            motor.set(speed);
        }
    }

    public void stop() {
        motor.stopMotor();
    }

    @Override
    public void periodic() {
        sim.update();

        SmartDashboard.putNumber("TurretOrientation", getAngleDegrees());
        SmartDashboard.putBoolean("TurretLeftSwitch", isAtLeft());
        SmartDashboard.putBoolean("TurretCenterSwitch", isAtCenter());
        SmartDashboard.putBoolean("TurretRightSwitch", isAtRight());
    }
}
