package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAlternateEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.sim.ArmSim;

public class ArmSystem extends SubsystemBase {

    private final CANSparkMax motor;
    private final RelativeEncoder encoder;

    private final ArmSim sim;

    public ArmSystem() {
        motor = new CANSparkMax(RobotMap.ARM_MOTOR, CANSparkLowLevel.MotorType.kBrushless);
        encoder = motor.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, RobotMap.NEO_ENCODER_PPR);

        sim = new ArmSim(motor);
    }

    public double getPositionDegrees() {
        return encoder.getPosition() / 8.745 * 360;
    }

    public void rotate(double speed) {
        motor.set(speed);
    }

    public void stop() {
        motor.stopMotor();
    }

    @Override
    public void periodic() {
        sim.update();
    }
}
