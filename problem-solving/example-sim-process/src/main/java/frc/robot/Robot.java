package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.TurretRotateToAngle;
import frc.robot.commands.TurretXbox;
import frc.robot.subsystems.TurretSystem;

public class Robot extends TimedRobot {

    private TurretSystem turretSystem;
    private XboxController controller;

    private TurretXbox turretXboxCommand;

    @Override
    public void robotInit() {
        turretSystem = new TurretSystem();
        controller = new XboxController(0);

        CommandScheduler.getInstance().onCommandInitialize((command)-> {
            System.out.println("Command initialized: " + command.getName());
        });
        CommandScheduler.getInstance().onCommandFinish((command)-> {
            System.out.println("Command finished: " + command.getName());
        });
        CommandScheduler.getInstance().onCommandInterrupt((command)-> {
            System.out.println("Command interrupt: " + command.getName());
        });

        turretXboxCommand = new TurretXbox(turretSystem, controller);
        turretSystem.setDefaultCommand(turretXboxCommand);

        new JoystickButton(controller, XboxController.Button.kY.value)
                .onTrue(new TurretRotateToAngle(turretSystem, 0));
        new JoystickButton(controller, XboxController.Button.kX.value)
                .onTrue(new TurretRotateToAngle(turretSystem, RobotMap.TURRET_MIN_ANGLE));
        new JoystickButton(controller, XboxController.Button.kB.value)
                .onTrue(new TurretRotateToAngle(turretSystem, RobotMap.TURRET_MAX_ANGLE));
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
