package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSystem;

public class DriveDistanceStraight extends Command {

    private DriveSystem driveSystem;
    private double distanceToDriveMeters;

    private double startPosition;

    public DriveDistanceStraight(DriveSystem driveSystem, double distanceToDriveMeters) {
        this.driveSystem = driveSystem;
        this.distanceToDriveMeters = distanceToDriveMeters;
    }

    @Override
    public void initialize() {
        startPosition = driveSystem.getRightDistancePassedMeters();
    }

    @Override
    public void execute() {
        double position = driveSystem.getLeftDistancePassedMeters();
        double distancePassed = position - startPosition;

        double speed = 1 - (distancePassed / distanceToDriveMeters);
        if (speed < 0.1) {
            speed = 0.1;
        }

        driveSystem.move(speed, speed);
    }

    @Override
    public boolean isFinished() {
        double position = driveSystem.getRightDistancePassedMeters();
        double distancePassed = position - startPosition;
        return distancePassed >= distanceToDriveMeters;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
