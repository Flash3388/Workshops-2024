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
    // - rotate to 90 degrees
    // - drive straight 1 meters forward
    // - rotate to 315 degrees
    // - drive straight 1 meter backward
    //
    // the robot then should follow this path
    //
    //
    //
    //             /_____
    //            /     |
    //                  |
    //                  |
    //
    // Starting at position (x: 0, y: 0, rotation: 0),
    // the robot should reach the following positions on the field:
    // - after first command (3, 0, 0)
    // - after second command (3, 0, 90)
    // - after third command (3, 1, 90)
    // - after forth command (3, 1, 315)
    // - after fifth command (2.29289, 1.70711, 315)
    //
    // but there are a few bugs in the code.
    // fix the code so the autonomous sequence succeeds.
    // do not re-write the code or add unnecessary things. the current
    // code is your base code, simply fix it so that it works. So adding
    // PID is not part of this exercise.
    //
    // remember to use the field object on the simulation gui to see
    // the robot in motion. Open it under NetworkTables->SmartDashboard->Field

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
