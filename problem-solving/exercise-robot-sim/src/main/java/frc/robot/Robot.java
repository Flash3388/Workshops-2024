package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveDistanceStraight;
import frc.robot.commands.RotateToTarget;
import frc.robot.subsystems.DriveSystem;

public class Robot extends TimedRobot {

    // this is a simulation code for a robot
    // with a basic tank drive.
    // we have one subsystem: the tank drive, which has a few autonomous commands
    // during autonomous we are attempting to run a sequence of commands
    // to make the robot:
    // - drive straight 3 meters forward
    // - rotate to 255 degrees
    // - drive straight 1 meters forward
    // - rotate to 45 degrees
    // - drive straight 1 meter backward
    //
    // but there are a few bugs in the code.
    // fix the code so the autonomous sequence succeeds

    private DriveSystem driveSystem;

    @Override
    public void robotInit() {
        driveSystem = new DriveSystem();
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {

    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void teleopPeriodic() {

    }

    @Override
    public void autonomousInit() {
        Command command1 = new DriveDistanceStraight(driveSystem, 3);
        Command command2 = new RotateToTarget(driveSystem, Rotation2d.fromDegrees(255));
        Command command3 = new DriveDistanceStraight(driveSystem, 1);
        Command command4 = new RotateToTarget(driveSystem, Rotation2d.fromDegrees(45));
        Command command5 = new DriveDistanceStraight(driveSystem, -1);

        Command auto = Commands.sequence(
                command1,
                command2,
                command3,
                command4,
                command5
        );
        auto.schedule();
    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void autonomousExit() {

    }

    @Override
    public void testInit() {

    }

    @Override
    public void testPeriodic() {

    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }
}
