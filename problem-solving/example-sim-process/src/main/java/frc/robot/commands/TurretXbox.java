package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TurretSystem;

public class TurretXbox extends Command {

    private final TurretSystem turretSystem;
    private final XboxController xboxController;

    public TurretXbox(TurretSystem turretSystem, XboxController xboxController) {
        this.turretSystem = turretSystem;
        this.xboxController = xboxController;

        addRequirements(turretSystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double speed = xboxController.getRightX();
        turretSystem.move(speed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
