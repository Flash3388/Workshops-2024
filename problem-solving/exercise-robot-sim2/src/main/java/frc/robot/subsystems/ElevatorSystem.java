package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAlternateEncoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.sim.ElevatorSim;

public class ElevatorSystem extends SubsystemBase {

    private final CANSparkMax motor;
    private final RelativeEncoder encoder;

    private final DigitalInput bottomSwitch;
    private final DigitalInput topSwitch;

    private final ElevatorSim sim;

    public ElevatorSystem() {
        motor = new CANSparkMax(RobotMap.ELEVATOR_MOTOR, CANSparkLowLevel.MotorType.kBrushless);
        encoder = motor.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, RobotMap.NEO_ENCODER_PPR);

        bottomSwitch = new DigitalInput(RobotMap.ELEVATOR_BOTTOM_SWITCH);
        topSwitch = new DigitalInput(RobotMap.ELEVATOR_TOP_SWITCH);

        sim = new ElevatorSim(motor);
    }

    public double getHeightMeters() {
        return encoder.getPosition() / RobotMap.ELEVATOR_GEAR_RATIO * RobotMap.ELEVATOR_DRUM_RADIUS_M;
    }

    public boolean isAtBottom() {
        return !bottomSwitch.get();
    }

    public boolean isAtTop() {
        return !topSwitch.get();
    }

    public void move(double speed) {
        if ((isAtTop() && speed < 0) || (isAtBottom() && speed > 0)) {
            motor.set(speed);
        } else {
            stop();
        }
    }

    public void stop() {
        motor.stopMotor();
    }

    @Override
    public void periodic() {
        sim.update();

        SmartDashboard.putNumber("ElevatorHeight", getHeightMeters());
        SmartDashboard.putBoolean("ElevatorBottomSwitch", isAtBottom());
        SmartDashboard.putBoolean("ElevatorTopSwitch", isAtTop());
    }
}
