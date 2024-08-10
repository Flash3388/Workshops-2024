package frc.robot.sim;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.simulation.DIOSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.SimSettings;

public class TurretSim {

    private final SparkMaxSim motor;

    private final TurretSimPlant sim;
    private final DIOSim minSwitchSim;
    private final DIOSim centerSwitchSim;
    private final DIOSim maxSwitchSim;

    private final Mechanism2d mechanism;
    private final MechanismLigament2d mechanismLigament;

    public TurretSim(CANSparkMax motor) {
        NetworkTable rootTable = NetworkTableInstance.getDefault().getTable("SimDebug").getSubTable("Turret");
        MotorShaft motorShaft = new MotorShaft(RobotMap.NEO_ENCODER_PPR, SimSettings.TURRET_MOTOR_TO_TURRET_GEAR_RATIO, 360);

        this.motor = new SparkMaxSim(
                rootTable,
                "Motor",
                motor,
                motorShaft,
                false,
                true);

        sim = new TurretSimPlant(
                rootTable,
                DCMotor.getNEO(SimSettings.TURRET_MOTOR_COUNT),
                SimSettings.TURRET_MOMENT_OF_INERTIA,
                SimSettings.TURRET_MOTOR_TO_TURRET_GEAR_RATIO,
                SimSettings.TURRET_MIN_ANGLE,
                SimSettings.TURRET_MAX_ANGLE,
                SimSettings.TURRET_PHYSICAL_LIMIT_DIFF,
                VecBuilder.fill(0, 0.001)
        );

        minSwitchSim = new DIOSim(RobotMap.TURRET_LEFT_SWITCH);
        minSwitchSim.setIsInput(true);
        minSwitchSim.setValue(true);
        centerSwitchSim = new DIOSim(RobotMap.TURRET_CENTER_SWITCH);
        centerSwitchSim.setIsInput(true);
        centerSwitchSim.setValue(true);
        maxSwitchSim = new DIOSim(RobotMap.TURRET_RIGHT_SWITCH);
        maxSwitchSim.setIsInput(true);
        maxSwitchSim.setValue(true);

        mechanism = new Mechanism2d(10, 10);
        MechanismRoot2d mechanismRoot = mechanism.getRoot("Turret", 5, 5);
        mechanismLigament = mechanismRoot.append(new MechanismLigament2d("Turret", 5, 90));
        SmartDashboard.putData("Turret-Mechanism", mechanism);
    }

    public void update() {
        double motorOutput = motor.updateOutput();
        sim.setInput(motorOutput);

        if (motorOutput == 0) {
            sim.setState(VecBuilder.fill(sim.getPosition(), 0));
        }

        sim.update(0.02);

        double position = sim.getPosition();
        motor.updateOdometry(position, sim.getVelocity());

        boolean atMin = sim.isAtMin();
        boolean atMax = sim.isAtMax();
        boolean atCenter = sim.isAtCenter();

        minSwitchSim.setValue(!atMin);
        maxSwitchSim.setValue(!atMax);
        centerSwitchSim.setValue(!atCenter);

        mechanismLigament.setAngle(90 - position);
    }
}
