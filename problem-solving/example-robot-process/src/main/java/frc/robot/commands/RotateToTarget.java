package frc.robot.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSystem;

public class RotateToTarget extends Command {

    private DriveSystem driveSystem;
    private Rotation2d targetRotation;

    public RotateToTarget(DriveSystem driveSystem, Rotation2d targetRotation) {
        this.driveSystem = driveSystem;
        this.targetRotation = targetRotation;

        addRequirements(driveSystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double distance = driveSystem.getRotation().getDegrees() - targetRotation.getDegrees();
        double speed = 1;
        if (Math.abs(distance) < 180) {
            speed = distance / 180;
        }

        driveSystem.move(speed, -speed);
    }

    @Override
    public boolean isFinished() {
        return driveSystem.getRotation().getDegrees() == targetRotation.getDegrees();
    }

    @Override
    public void end(boolean interrupted) {
        driveSystem.stop();
    }
}
