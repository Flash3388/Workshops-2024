package frc.robot.sim;

public class MotorShaft {

    private final int encoderPpr;
    private final double gearRatio;
    private final double rotorToMechanism;

    public MotorShaft(int encoderPpr, double gearRatio, double rotorToMechanism) {
        this.encoderPpr = encoderPpr;
        this.gearRatio = gearRatio;
        this.rotorToMechanism = rotorToMechanism;
    }

    public int getEncoderPpr() {
        return encoderPpr;
    }

    public double getGearRatio() {
        return gearRatio;
    }

    public double getRotorToMechanism() {
        return rotorToMechanism;
    }

    public double velocityToMotorRotations(double velocity) {
        return velocity / rotorToMechanism * gearRatio;
    }

    public double distanceToMotorRotations(double distance) {
        return distance / rotorToMechanism * gearRatio;
    }
}
