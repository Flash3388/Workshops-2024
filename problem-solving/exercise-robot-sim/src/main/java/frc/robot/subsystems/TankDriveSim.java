package frc.robot.subsystems;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.ADXRS450_GyroSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import frc.robot.Constants;
import frc.robot.Conversions;

public class TankDriveSim {
    public static final int DRIVE_SIDE_MOTOR_COUNT = 4;
    public static final double WEIGHT_KG = 40;
    public static final double DRIVE_TRACK_WIDTH_M = 4;
    public static final double ROBOT_WIDTH_M = DRIVE_TRACK_WIDTH_M;
    public static final double ROBOT_LENGTH_M = 5;
    // for rectangle -> I = (bh^3) / 12, where b = width, h = length
    public static final double DRIVE_MOMENT_OF_INERTIA = (Math.pow(ROBOT_LENGTH_M, 3) * ROBOT_WIDTH_M) / 12;

    private final DriveSystem driveSystem;
    private final DifferentialDrivetrainSim sim;
    private final ADXRS450_GyroSim gyroSim;


    public TankDriveSim(DriveSystem driveSystem) {
        this.driveSystem = driveSystem;

        sim = new DifferentialDrivetrainSim(
                DCMotor.getCIM(DRIVE_SIDE_MOTOR_COUNT), // we use CIM motors in this example
                Constants.DRIVE_MOTOR_TO_WHEEL_GEAR_RATIO,
                DRIVE_MOMENT_OF_INERTIA,
                WEIGHT_KG,
                Constants.DRIVE_WHEEL_RADIUS_M,
                DRIVE_TRACK_WIDTH_M,
                null
        );

        gyroSim = new ADXRS450_GyroSim(driveSystem.gyro);
    }

    public void update() {
        sim.setInputs(
                driveSystem.motorL.get() * RobotController.getBatteryVoltage(),
                driveSystem.motorR.get() * RobotController.getBatteryVoltage()
        );

        // 0.02 is 20ms which is the average loop period in FRC
        sim.update(0.02);

        // update our sensors according to our physics calculations, so that they still function.
        gyroSim.setAngle(sim.getHeading().getDegrees());

        driveSystem.motorR.setSelectedSensorPosition(Conversions.driveSrxMetersToRawUnits(sim.getRightPositionMeters()));
        driveSystem.motorL.setSelectedSensorPosition(Conversions.driveSrxMetersToRawUnits(sim.getLeftPositionMeters()));
    }
}
