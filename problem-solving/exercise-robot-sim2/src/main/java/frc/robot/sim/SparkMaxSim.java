package frc.robot.sim;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.jni.CANSparkMaxJNI;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.RobotController;

import java.lang.reflect.Field;

public class SparkMaxSim extends BaseSimMotor {

    private final CANSparkMax motor;

    private final long handle;
    private final RelativeEncoder encoder;

    public SparkMaxSim(NetworkTable rootTable, String name,
                       CANSparkMax motor, MotorShaft motorShaft,
                       boolean inverted, boolean updateEncoder) {
        super(rootTable, name, motorShaft, inverted, updateEncoder);
        this.motor = motor;

        handle = getHandle(motor);

        if (updateEncoder) {
            // will through if user misconfigured the encoder
            encoder = motor.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, motorShaft.getEncoderPpr());
            encoder.setPosition(0);
        } else {
            encoder = null;
        }
    }

    public SparkMaxSim(NetworkTable rootTable, String name,
                       CANSparkMax motor, MotorShaft motorShaft,
                       boolean inverted) {
        this(rootTable, name, motor, motorShaft, inverted, false);
    }

    @Override
    protected double getMotorOutputVoltage() {
        return motor.get() * RobotController.getBatteryVoltage();
    }

    @Override
    protected boolean isMotorInverted() {
        return motor.getInverted();
    }

    @Override
    protected void setAppliedOutput(double voltage) {
        CANSparkMaxJNI.c_SparkMax_SetSimAppliedOutput(handle, (float) voltage);
    }

    @Override
    protected void setEncoderData(double position, double positionChanged, double velocityRpm) {
        double encoderPos = encoder.getPosition();
        encoder.setPosition(encoderPos + positionChanged);

        CANSparkMaxJNI.c_SparkMax_SetSimAltEncoderVelocity(handle, (float) velocityRpm);
    }

    @Override
    public void update() {

    }

    private static long getHandle(CANSparkMax motor) {
        try {
            Field field = ReflectionHelper.getField(motor.getClass(), "sparkMaxHandle");
            field.setAccessible(true);
            return (long) field.get(motor);
        } catch (IllegalAccessException | NumberFormatException e) {
            throw new Error("unable to access spark handle", e);
        }
    }
}
