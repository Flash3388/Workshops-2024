package frc.robot.sim;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.NumericalIntegration;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.simulation.LinearSystemSim;

public class ElevatorSimPlant extends LinearSystemSim<N2, N1, N1> {

    private static final double LIMIT_DAMAGE_MARGIN = 0.05;

    private final double minHeight;
    private final double maxHeight;
    private final boolean simulateGravity;
    private final double physicalLimitDifference;

    private final NetworkTableEntry entryMinLimit;
    private final NetworkTableEntry entryMaxLimit;
    private final NetworkTableEntry entryLimitsExceeded;
    private final NetworkTableEntry entryLimitsExceededSticky;

    public ElevatorSimPlant(NetworkTable table,
                            DCMotor gearbox,
                            double gearing,
                            double carriageMassKg,
                            double drumRadiusMeters,
                            double minHeightMeters,
                            double maxHeightMeters,
                            boolean simulateGravity,
                            double startingHeightMeters,
                            double physicalLimitDifference,
                            Matrix<N1, N1> measurementStdDevs) {
        super(LinearSystemId.createElevatorSystem(gearbox, carriageMassKg, drumRadiusMeters, gearing), measurementStdDevs);
        this.minHeight = minHeightMeters;
        this.maxHeight = maxHeightMeters;
        this.simulateGravity = simulateGravity;
        this.physicalLimitDifference = physicalLimitDifference;

        setState(VecBuilder.fill(startingHeightMeters, 0));

        entryMinLimit = table.getEntry("MinLimit");
        entryMaxLimit = table.getEntry("MaxLimit");
        entryLimitsExceeded = table.getEntry("LimitsExceeded");
        entryLimitsExceededSticky = table.getEntry("LimitsExceededSticky");
        entryLimitsExceededSticky.setBoolean(false);
    }

    public double getPosition() {
        return getOutput(0);
    }

    public double getVelocity() {
        return m_x.get(1, 0);
    }

    public boolean isAtMin() {
        double position = getPosition();
        return position <= minHeight;
    }

    public boolean isAtMax() {
        double position = getPosition();
        return position >= maxHeight;
    }

    public boolean hasExceededLimits() {
        double position = getPosition();
        return position >= maxHeight + physicalLimitDifference - LIMIT_DAMAGE_MARGIN;
    }

    @Override
    public void update(double dtSeconds) {
        super.update(dtSeconds);

        boolean atMin = isAtMin();
        boolean atMax = isAtMax();

        entryMinLimit.setBoolean(atMin);
        entryMaxLimit.setBoolean(atMax);

        entryLimitsExceeded.setBoolean(hasExceededLimits());
        entryLimitsExceededSticky.setBoolean(entryLimitsExceededSticky.getBoolean(false) || entryLimitsExceeded.getBoolean(false));
    }

    @Override
    protected Matrix<N2, N1> updateX(Matrix<N2, N1> currentXhat, Matrix<N1, N1> u, double dtSeconds) {
        var mat =
                NumericalIntegration.rkdp(
                        (x, _u) -> {
                            Matrix<N2, N1> xdot = m_plant.getA().times(x).plus(m_plant.getB().times(_u));
                            if (simulateGravity) {
                                xdot = xdot.plus(VecBuilder.fill(0, -9.8));
                            }
                            return xdot;
                        },
                        currentXhat,
                        u,
                        dtSeconds);


        double position = mat.get(0, 0);
        if (isAtMinPhysicalEdge(position)) {
            return VecBuilder.fill(minHeight - physicalLimitDifference, 0);
        }
        if (isAtMaxPhysicalEdge(position)) {
            return VecBuilder.fill(maxHeight + physicalLimitDifference, 0);
        }

        return mat;
    }

    private boolean isAtMinPhysicalEdge(double position) {
        return position < minHeight - physicalLimitDifference;
    }

    private boolean isAtMaxPhysicalEdge(double position) {
        return position > maxHeight + physicalLimitDifference;
    }
}
