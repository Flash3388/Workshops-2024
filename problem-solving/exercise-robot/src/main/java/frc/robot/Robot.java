package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.DriveXbox;
import frc.robot.subsystems.DriveSystem;

public class Robot extends TimedRobot {

    private DriveSystem driveSystem;
    private XboxController xboxController;

    @Override
    public void robotInit() {
        driveSystem = new DriveSystem();
        xboxController = new XboxController(0);

        driveSystem.setDefaultCommand(new DriveXbox(driveSystem, xboxController));
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
