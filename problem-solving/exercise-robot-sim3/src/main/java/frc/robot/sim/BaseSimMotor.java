package frc.robot.sim;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.RobotState;

public abstract class BaseSimMotor {

    protected final MotorShaft motorShaft;
    private final boolean inverted;
    private final boolean updateEncoder;

    private final NetworkTableEntry entryOutput;
    private final NetworkTableEntry entryOutputVoltage;
    private final NetworkTableEntry entryPosition;
    private final NetworkTableEntry entryPositionRaw;
    private final NetworkTableEntry entryLastPositionRaw;
    private final NetworkTableEntry entryVelocity;
    private final NetworkTableEntry entryVelocityRaw;

    public BaseSimMotor(NetworkTable rootTable, String name,
                        MotorShaft motorShaft,
                        boolean inverted, boolean updateEncoder) {
        this.motorShaft = motorShaft;
        this.inverted = inverted;
        this.updateEncoder = updateEncoder;

        NetworkTable table = rootTable.getSubTable(name);
        entryOutput = table.getEntry("Output");
        entryOutputVoltage = table.getEntry("OutputVoltage");
        entryPosition = table.getEntry("Position");
        entryPositionRaw = table.getEntry("PositionRaw");
        entryLastPositionRaw = table.getEntry("LastPositionRaw");
        entryVelocity = table.getEntry("Velocity");
        entryVelocityRaw = table.getEntry("VelocityRaw");

        entryLastPositionRaw.setDouble(0);
    }

    public double updateOutput() {
        double voltage = getMotorOutputVoltage();

        if (inverted) {
            voltage = -voltage;
        }
        if (isMotorInverted()) {
            voltage = -voltage;
        }

        if (RobotState.isDisabled()) {
            voltage = 0;
        }

        setAppliedOutput(voltage);

        entryOutput.setDouble(voltage / RobotController.getBatteryVoltage());
        entryOutputVoltage.setDouble(voltage);

        return voltage;
    }

    public void updateOdometry(double position, double velocity) {
        if (!updateEncoder) {
            return;
        }

        if (inverted) {
            position = -position;
            velocity = -velocity;
        }

        double revs = motorShaft.distanceToMotorRotations(position);
        double rpm = motorShaft.velocityToMotorRotations(velocity);

        double positionDifference = revs - entryLastPositionRaw.getDouble(0);
        setEncoderData(revs, positionDifference, rpm);

        entryLastPositionRaw.setDouble(revs);
        entryPosition.setDouble(position);
        entryPositionRaw.setDouble(revs);
        entryVelocity.setDouble(velocity);
        entryVelocityRaw.setDouble(rpm);
    }

    protected abstract double getMotorOutputVoltage();
    protected abstract boolean isMotorInverted();
    protected abstract void setAppliedOutput(double voltage);
    protected abstract void setEncoderData(double position, double positionChanged, double velocityRpm);

    public abstract void update();
}
