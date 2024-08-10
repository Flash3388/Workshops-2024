package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSystem;

public class MoveElevatorToHeight extends Command {

    private static final double RESIST_GRAVITY_SPEED = 0.5;
    private static final double TOLERANCE = 0.1;

    private final ElevatorSystem elevatorSystem;
    private double targetHeightMeters;

    public MoveElevatorToHeight(ElevatorSystem elevatorSystem, double targetHeightMeters) {
        this.elevatorSystem = elevatorSystem;

        addRequirements(elevatorSystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double currentHeight = elevatorSystem.getHeightMeters();
        double speed = (targetHeightMeters - currentHeight) * 0.1;
        elevatorSystem.move(speed + RESIST_GRAVITY_SPEED);
    }

    @Override
    public boolean isFinished() {
        double currentHeight = elevatorSystem.getHeightMeters();
        return MathUtil.isNear(targetHeightMeters, TOLERANCE, currentHeight);
    }

    @Override
    public void end(boolean interrupted) {
        elevatorSystem.stop();
    }
}
