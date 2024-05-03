In 28.4.2024 We met for the second part of the Electronics Workshop. This part takes the theory we saw in the first part and shows the practical usage of it with robot code and actual devices.

Presentations used:
- [Tom's Slideshow](https://docs.google.com/presentation/d/1-kQRPxuOjtqOZDmWHgj9rgo71iQC9VG4_2SRn7aQ0mo/edit?usp=sharing) - From Slide 66 until the end

Now that we've seen the theory of how electricty works and how it may be used to communicate, let's see how to write code with RoboRIO to actually make it happen.

## RoboRIO I/O Ports

Since the RoboRIO was made specifically for the FRC, it comes with a variety of useful ports for controlling and communicating with different devices.

![RoboRIO Ports](https://github.com/Flash3388/Workshops-2024/assets/17641355/ec03cba7-6ae3-43d3-88fa-95be03bdf01a)

We'll go over these ports one by one and see how we can use them in code.

### Digital I/O

The Digital Input/Output (DIO) ports are a row of 10 seperate ports in the side of the RoboRIO. They are used for generating and reading digital signals. Each of these ports is numbered (0-9), and this number will be used in our code to access the specific port.

![DIO Ports Row](https://github.com/Flash3388/Workshops-2024/assets/17641355/1afa366c-4624-44ba-904f-d947b7c354be)

Each port in the row is made up of 3 seperate pins: 
- The signal pin (top) for the digital signal 
- The VCC pin (second) for 5V power connection
- The GND pin (bottom) for Ground connection

![Port pins](https://github.com/Flash3388/Workshops-2024/assets/17641355/e86dd32c-a0f0-4036-b092-fc8f4882e04c)

Normally the signal pin is enough, since all we care about is the signal. But the VCC and GND pins allows us to provide power to the devices connected to the signal pin. We'll see more about it later. The VCC and GND ports are all the same and are connected amongst each other throughout all the ports. Only the signal pins are seperate.

Each of the pins can be configured to either output **or** input. So it can be used to either transmit a signal or receive a signal, but not both. In output mode, it will transmit a `5V` for HIGH and less than `0.8V` for LOW. In input mode, it expects a voltage of `2V` and above for HIGH and `0.8V` and below for LOW. This is called TTL level. It does matter, as some devices can only receive a HIGH voltage of up to `3.3V`, so connecting it to the RoboRIO pin may cause it damage. Same goes the other way around, if the device sends a `2V` and higher for HIGH, it will be read fine. But if it transmits `1.5V` for HIGH, the RoboRIO won't be able to recognize it as HIGH.

Each of the signal pins have what is called a _Pull-Up Resistor_. Such resistors are _very high resistance_ resistors which are connected in a circuit between the pin and GND, thus creating a circuit with high resistance. When nothing else is connected to the pin, this circuit remains the only possible path and thus voltage goes through it, because of this, the voltage measure on the pin measures as HIGH. So when nothing is connected to the pin, its state will be read as HIGH. This is important to remember. 

You may ask as to why do this? It is actually quite ingineous. Consider: you have a switch connected to the pin. When the switch is pressed, it transmits a HIGH signal. This switch is important, as it indicates that our, say, arm as reached its limit. Now, what happens when the wire accidentally gets cut (which does happen). Well, in that case we no longer have the safety sensor for our arm. But we won't actually know that, because there is no real indication. But because of the pull-up resistor, the value of the pin is now back to HIGH. So, we read that it is HIGH and stop the motor because we think the arm is at its limit. This protects our arm now that the sensor is inoperable. Some boards have also pull-down resistor, and one can switch between it and the pull-up resistor.

Accessing the pins via code is pretty straight-forward. However, note that a port can only be configured once for a specific use. 

```java
public class Robot extends TimedRobot {
  
  private DigitalInput input;

  @Override
  public void robotInit() {
    // this configures the port to input functionality
    // 1 refers to port number 1
    input = new DigitalInput(1);
  }
  ...
  @Override
  public void teleopPeriodic() {
    boolean isHigh = input.get();
    if (isHigh) {
      // the voltage level is HIGH on the pin
    } else {
      // the voltage level is LOW on the pin
    }
  }
  ...
}
```

```java
public class Robot extends TimedRobot {
  
  private DigitalOutput output;

  @Override
  public void robotInit() {
    // this configures the port to output functionality
    // 1 refers to port number 1
    output = new DigitalOutput(1);
  }
  ...
  @Override
  public void teleopPeriodic() {
    // this set the output to LOW voltage level. "true" will be to set it to HIGH voltage level
    output.set(false);

    // this sends a pulse of length of 10^-5 seconds (10 microseconds). So the pin will be HIGH for 10 microseconds and then back to LOW
    output.pulse(1e-5);
  }
  ...
}
```

#### Example - Limit Switch

![Microswitch Wiring](https://github.com/Flash3388/Workshops-2024/assets/17641355/51176f66-2782-404a-b089-32a43aaaa480)

Consider, a simple microswitch connected to one of our DIO ports. A basic microswitch has two configurations:
- Normally Open: in normally open mode, when the switch is not pressed, the circuit is open. And due to the pull-up resistor, the voltage will be HIGH on the pin. When it is pressed, the circuit is closed with resistance and thus the voltage level becomes LOW.
- Normally Closed: in normally closed mode, when the switch is not pressed, the circuit is closed with resistance so the voltage is LOW. When it is pressed, the circuit is open and voltage becomes high.

In the image above, we see a normally-open configuration. The code below uses this switch as a limit to a motor.

```java
public class Robot extends TimedRobot {
  
  private DigitalInput input;
  private WPI_TalonSRX motor;

  @Override
  public void robotInit() {
    input = new DigitalInput(1);
    motor = new WPI_TalonSRX(0);
  }
  ...
  @Override
  public void teleopPeriodic() {
    boolean isHigh = input.get();
    if (isHigh) {
      // the voltage level is HIGH on the pin
      // so in normally-open, this means the switch isn't pressed
      // so run the motor
      motor.set(0.5);
    } else {
      // the voltage level is LOW on the pin
      // so in normally-open, this means the switch is pressed.
      // so stop the motor
      motor.set(0);
    }
  }
  ...
}
```
