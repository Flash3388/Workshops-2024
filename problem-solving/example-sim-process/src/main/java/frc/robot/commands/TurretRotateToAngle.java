package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TurretSystem;

public class TurretRotateToAngle extends Command {

    private final TurretSystem turretSystem;
    private final double targetAngleDegrees;

    public TurretRotateToAngle(TurretSystem turretSystem, double targetAngleDegrees) {
        this.turretSystem = turretSystem;
        this.targetAngleDegrees = targetAngleDegrees;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double speed = (targetAngleDegrees - turretSystem.getAngleDegrees()) * 0.01;
        turretSystem.move(speed);
    }

    @Override
    public boolean isFinished() {
        return MathUtil.isNear(targetAngleDegrees, turretSystem.getAngleDegrees(), 1);
    }

    @Override
    public void end(boolean interrupted) {

    }
}
