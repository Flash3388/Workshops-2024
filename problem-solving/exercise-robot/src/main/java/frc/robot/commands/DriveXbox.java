package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSystem;

public class DriveXbox extends Command {

    private final DriveSystem driveSystem;
    private final XboxController xboxController;

    public DriveXbox(DriveSystem driveSystem, XboxController xboxController) {
        this.driveSystem = driveSystem;
        this.xboxController = xboxController;

        addRequirements(driveSystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double left = xboxController.getLeftX();
        double right = xboxController.getRightY();
        driveSystem.drive(left, right);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        driveSystem.stop();
    }
}
