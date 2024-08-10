package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.MoveElevatorToHeight;
import frc.robot.subsystems.ElevatorSystem;

public class Robot extends TimedRobot {

    private ElevatorSystem elevatorSystem;
    private XboxController controller;

    @Override
    public void robotInit() {
        elevatorSystem = new ElevatorSystem();
        controller = new XboxController(0);

        new JoystickButton(controller, XboxController.Button.kY.value)
                .onTrue(new MoveElevatorToHeight(elevatorSystem, 2));
        new JoystickButton(controller, XboxController.Button.kX.value)
                .onTrue(new MoveElevatorToHeight(elevatorSystem, 0.5));
        new JoystickButton(controller, XboxController.Button.kB.value)
                .onTrue(new MoveElevatorToHeight(elevatorSystem, 1));
        new JoystickButton(controller, XboxController.Button.kA.value)
                .onTrue(new MoveElevatorToHeight(elevatorSystem, 0));
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
