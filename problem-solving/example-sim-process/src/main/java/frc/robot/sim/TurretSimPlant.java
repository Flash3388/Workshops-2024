package frc.robot.sim;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.simulation.LinearSystemSim;

public class TurretSimPlant extends LinearSystemSim<N2, N1, N2> {

    private static final double LIMIT_DAMAGE_MARGIN = 0.5;

    private final double minAngle;
    private final double maxAngle;
    private final double physicalLimitDifference;

    private final NetworkTableEntry entryMinLimit;
    private final NetworkTableEntry entryCenterPos;
    private final NetworkTableEntry entryMaxLimit;
    private final NetworkTableEntry entryLimitsExceeded;
    private final NetworkTableEntry entryLimitsExceededSticky;

    public TurretSimPlant(NetworkTable table,
                          DCMotor motor,
                          double JKgMetersSquared,
                          double gearing,
                          double minAngle,
                          double maxAngle,
                          double physicalLimitDifference,
                          Matrix<N2, N1> measurementStdDevs) {
        super(LinearSystemId.createDCMotorSystem(motor, JKgMetersSquared, gearing), measurementStdDevs);
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
        this.physicalLimitDifference = physicalLimitDifference;

        entryMinLimit = table.getEntry("MinLimit");
        entryCenterPos = table.getEntry("Centered");
        entryMaxLimit = table.getEntry("MaxLimit");
        entryLimitsExceeded = table.getEntry("LimitsExceeded");
        entryLimitsExceededSticky = table.getEntry("LimitsExceededSticky");
        entryLimitsExceededSticky.setBoolean(false);
    }

    public double getPosition() {
        return getOutput(0);
    }

    public double getVelocity() {
        return getOutput(1);
    }

    public boolean isAtMin() {
        double position = getPosition();
        return position <= minAngle;
    }

    public boolean isAtMax() {
        double position = getPosition();
        return position >= maxAngle;
    }

    public boolean isAtCenter() {
        double position = getPosition();
        return position <= 1 && position >= -1;
    }

    public boolean hasExceededLimits() {
        double position = getPosition();
        return position >= maxAngle + physicalLimitDifference - LIMIT_DAMAGE_MARGIN || position <= minAngle - physicalLimitDifference + LIMIT_DAMAGE_MARGIN;
    }

    @Override
    public void update(double dtSeconds) {
        super.update(dtSeconds);

        boolean atMin = isAtMin();
        boolean atMax = isAtMax();
        boolean atCenter = isAtCenter();

        entryMinLimit.setBoolean(atMin);
        entryCenterPos.setBoolean(atCenter);
        entryMaxLimit.setBoolean(atMax);

        entryLimitsExceeded.setBoolean(hasExceededLimits());
        entryLimitsExceededSticky.setBoolean(entryLimitsExceededSticky.getBoolean(false) || entryLimitsExceeded.getBoolean(false));
    }

    @Override
    protected Matrix<N2, N1> updateX(Matrix<N2, N1> currentXhat, Matrix<N1, N1> u, double dtSeconds) {
        Matrix<N2, N1> mat = super.updateX(currentXhat, u, dtSeconds);

        double position = mat.get(0, 0);
        if (isAtMinPhysicalEdge(position)) {
            return VecBuilder.fill(minAngle - physicalLimitDifference, 0);
        }
        if (isAtMaxPhysicalEdge(position)) {
            return VecBuilder.fill(maxAngle + physicalLimitDifference, 0);
        }

        return mat;
    }

    private boolean isAtMinPhysicalEdge(double position) {
        return position < minAngle - physicalLimitDifference;
    }

    private boolean isAtMaxPhysicalEdge(double position) {
        return position > maxAngle + physicalLimitDifference;
    }
}
