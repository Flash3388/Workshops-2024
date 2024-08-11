package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSystem;

public class MoveElevatorToHeight extends Command {

    private static final double RESIST_GRAVITY_SPEED = 0.5;
    private static final double TOLERANCE = 0.1;

    private final ElevatorSystem elevatorSystem;
    private double targetHeightMeters;

    public MoveElevatorToHeight(ElevatorSystem elevatorSystem, double targetHeightMeters) {
        this.elevatorSystem = elevatorSystem;
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("ArmAtHeight", false);
    }

    @Override
    public void execute() {
        double currentHeight = elevatorSystem.getHeightMeters();
        double speed = (currentHeight - targetHeightMeters) * 0.1;
        elevatorSystem.move(speed + RESIST_GRAVITY_SPEED);

        if (MathUtil.isNear(targetHeightMeters, currentHeight, TOLERANCE)) {
            SmartDashboard.putBoolean("ArmAtHeight", true);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        elevatorSystem.stop();
    }
}
