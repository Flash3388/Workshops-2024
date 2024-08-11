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

public class ElevatorSim {

    private final SparkMaxSim motor;

    private final ElevatorSimPlant sim;
    private final DIOSim minSwitchSim;
    private final DIOSim maxSwitchSim;

    private final Mechanism2d mechanism;
    private final MechanismLigament2d mechanismLigament;

    public ElevatorSim(CANSparkMax motor) {
        NetworkTable rootTable = NetworkTableInstance.getDefault().getTable("SimDebug").getSubTable("Elevator");
        MotorShaft motorShaft = new MotorShaft(SimSettings.NEO_ENCODER_PPR, SimSettings.ELEVATOR_MOTOR_TO_ELEVATOR_GEAR_RATIO, SimSettings.ELEVATOR_DRUM_RADIUS_M);

        this.motor = new SparkMaxSim(
                rootTable,
                "Motor",
                motor,
                motorShaft,
                false,
                true);

        sim = new ElevatorSimPlant(
                rootTable,
                DCMotor.getNEO(SimSettings.ELEVATOR_MOTOR_COUNT),
                SimSettings.ELEVATOR_MOTOR_TO_ELEVATOR_GEAR_RATIO,
                SimSettings.ELEVATOR_CARRIAGE_MASS,
                SimSettings.ELEVATOR_DRUM_RADIUS_M,
                SimSettings.ELEVATOR_MIN_HEIGHT,
                SimSettings.ELEVATOR_MAX_HEIGHT,
                SimSettings.ELEVATOR_SIMULATE_GRAVITY,
                SimSettings.ELEVATOR_STARTING_HEIGHT,
                SimSettings.ELEVATOR_PHYSICAL_LIMIT_DIFF,
                VecBuilder.fill(0.00001)
        );

        minSwitchSim = new DIOSim(RobotMap.ELEVATOR_BOTTOM_SWITCH);
        minSwitchSim.setIsInput(true);
        minSwitchSim.setValue(true);
        maxSwitchSim = new DIOSim(RobotMap.ELEVATOR_TOP_SWITCH);
        maxSwitchSim.setIsInput(true);
        maxSwitchSim.setValue(true);

        mechanism = new Mechanism2d(2, 2);
        MechanismRoot2d mechanismRoot = mechanism.getRoot("Elevator", 0, 0);
        mechanismLigament = mechanismRoot.append(new MechanismLigament2d("Elevator", 0, 90));
        SmartDashboard.putData("Elevator-Mechanism", mechanism);
    }

    public void update() {
        double motorOutput = motor.updateOutput();
        sim.setInput(motorOutput);

        sim.update(0.02);

        double position = sim.getPosition();
        motor.updateOdometry(position, sim.getVelocity());

        boolean atMin = sim.isAtMin();
        boolean atMax = sim.isAtMax();

        minSwitchSim.setValue(atMin);
        maxSwitchSim.setValue(atMax);

        mechanismLigament.setLength(position);
    }
}
