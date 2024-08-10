package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.ArmCommand;
import frc.robot.subsystems.ArmSystem;

public class Robot extends TimedRobot {

    // this code implements robot simulation code.
    // the robot only has one system: the arm.
    // and a single command: arm command.
    // the arm command runs always and is responsible for
    // move an arm to a position and holding it there.
    // pressing on buttons changes the target position of the arm,
    // by telling it to the arm command, then the arm command moves the
    // arm to this new position and holds it there.
    // but it has problems

    private ArmSystem armSystem;
    private ArmCommand armCommand;
    private XboxController controller;

    @Override
    public void robotInit() {
        armSystem = new ArmSystem();
        armCommand = new ArmCommand(armSystem);
        armSystem.setDefaultCommand(armCommand);

        controller = new XboxController(0);

        new POVButton(controller, 0)
                .onTrue(Commands.run(()-> armCommand.setTarget(0)));
        new POVButton(controller, 180)
                .onTrue(Commands.run(()-> armCommand.setTarget(180)));
        new POVButton(controller, 90)
                .onTrue(Commands.run(()-> armCommand.setTarget(90)));
        new POVButton(controller, 270)
                .onTrue(Commands.run(()-> armCommand.setTarget(45)));
        new JoystickButton(controller, XboxController.Button.kA.value)
                .onTrue(Commands.run(()-> armCommand.stopMoving()));
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
