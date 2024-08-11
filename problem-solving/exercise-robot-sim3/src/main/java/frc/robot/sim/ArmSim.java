package frc.robot.sim;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.SimSettings;

public class ArmSim {

    private static final double MECHANISM_BASE_POS = 10;
    private static final double MECHANISM_ARM_LENGTH = 5;

    private final SparkMaxSim motor;

    private final SingleJointedArmSim sim;

    private final Mechanism2d mechanism;
    private final MechanismLigament2d mechanismLigament;

    public ArmSim(CANSparkMax motor) {
        NetworkTable rootTable = NetworkTableInstance.getDefault().getTable("SimDebug").getSubTable("Arm");
        MotorShaft motorShaft = new MotorShaft(RobotMap.NEO_ENCODER_PPR, SimSettings.ARM_MOTOR_TO_ARM_GEAR_RATIO, 360);

        this.motor = new SparkMaxSim(
                rootTable,
                "Motor",
                motor,
                motorShaft,
                false,
                true);

        sim = new SingleJointedArmSim(
                DCMotor.getNEO(SimSettings.ARM_MOTOR_COUNT),
                SimSettings.ARM_MOTOR_TO_ARM_GEAR_RATIO,
                SimSettings.ARM_MOMENT_OF_INERTIA,
                SimSettings.ARM_LENGTH,
                SimSettings.ARM_BOTTOM_MIN_ANGLE,
                SimSettings.ARM_BOTTOM_MAX_ANGLE,
                SimSettings.ARM_SIMULATE_GRAVITY,
                0,
                VecBuilder.fill(0.001)
        );

        mechanism = new Mechanism2d(20, 20);
        MechanismRoot2d mechanismRoot = mechanism.getRoot("Arm", MECHANISM_BASE_POS, 0);
        mechanismLigament = mechanismRoot.append(new MechanismLigament2d("Arm", MECHANISM_ARM_LENGTH, 0));
        SmartDashboard.putData("Arm-Mechanism", mechanism);
    }

    public boolean isAtCollectingPosition() {
        return isAtPosition(RobotMap.ARM_COLLECT_POSITION);
    }

    public boolean isAtShootingPosition() {
        return isAtPosition(RobotMap.ARM_SHOOT_POSITION);
    }

    public void overrideCurrentPosition(double position) {
        sim.setState(Math.toRadians(position), 0);
        mechanismLigament.setAngle(position);
    }

    public void update() {
        double motorOutput = motor.updateOutput();
        sim.setInput(motorOutput);

        sim.update(0.02);

        double position = Math.toDegrees(sim.getAngleRads());
        motor.updateOdometry(sim.getAngleRads(), sim.getVelocityRadPerSec());

        mechanismLigament.setAngle(position);
    }

    private boolean isAtPosition(double targetPositionDegrees) {
        double position = Math.toDegrees(sim.getAngleRads());
        return MathUtil.isNear(targetPositionDegrees, position, 5);
    }
}
