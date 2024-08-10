package frc.robot.commands;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSystem;

public class ArmCommand extends Command {

    // this command implements PID and FeedForward control for the arm.
    // it is meant to run always.
    // at any given moment, this command has a specific destination.
    // if the destination is valid, the command will move the arm to that
    // position using PID and try to keep it in place. The feedfowrad
    // helps the PID control the arm.
    // if the target is not valid, the arm doesn't move.

    private static final double KP = 0.0005;
    private static final double KI = 0;
    private static final double KD = 0.0005;
    private static final double IZONE = 0;
    private static final double KS = 0;
    private static final double KG = 0.3141;
    private static final double KV = 0;
    private static final double POSITION_TOLERANCE = 3;
    private static final double VELOCITY_TOLERANCE = 2;

    private final ArmSystem armSystem;

    private final PIDController pidController;
    private final ArmFeedforward feedForward;

    private double newTargetDegrees;

    private double currentTargetDegrees;
    private boolean currentTargetValid;

    public ArmCommand(ArmSystem armSystem) {
        this.armSystem = armSystem;

        pidController = new PIDController(KP, KI, KD);
        pidController.setIZone(IZONE);
        pidController.setTolerance(POSITION_TOLERANCE, VELOCITY_TOLERANCE);
        SmartDashboard.putData("ArmCommandPID", pidController);

        feedForward = new ArmFeedforward(KS, KG, KV);

        addRequirements(armSystem);
    }

    @Override
    public void initialize() {
        pidController.reset();
        currentTargetDegrees = Double.POSITIVE_INFINITY;
        currentTargetValid = false;
    }

    @Override
    public void execute() {
        // if we got a new target, we prepare to move to it.
        if (currentTargetDegrees != newTargetDegrees) {
            currentTargetDegrees = newTargetDegrees;
            currentTargetValid = currentTargetDegrees == Double.POSITIVE_INFINITY;

            pidController.reset();
        }

        if (currentTargetValid) {
            double positionDegrees = armSystem.getPositionDegrees();
            double pidOutput = pidController.calculate(positionDegrees, newTargetDegrees);
            double feedForwardOutput = feedForward.calculate(Math.toRadians(positionDegrees), 0);
            double output = pidOutput - feedForwardOutput;

            armSystem.rotate(output);
        } else {
            armSystem.stop();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        armSystem.stop();
    }

    public void setTarget(double newTargetDegrees) {
        // save a target for the arm to move to
        this.newTargetDegrees = newTargetDegrees;
    }

    public void stopMoving() {
        // save a target that indicates that the robot shouldn't move
        this.newTargetDegrees = Double.POSITIVE_INFINITY;
    }

    public boolean isAtPosition() {
        return !currentTargetValid || pidController.atSetpoint();
    }
}
