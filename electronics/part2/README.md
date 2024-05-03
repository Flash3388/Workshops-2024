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

#### Example - Led Blinking

The following code blinks a led connected to DIO port 0 every 1 second. This is done by changing the output from LOW to HIGH and then from HIGH to LOW.

```java
public class Robot extends TimedRobot {
  
  private DigitalOutput output;
  private double startTimeSeconds;

  @Override
  public void robotInit() {
    output = new DigitalOutput(0);
  }
  ...
  @Override
  public void teleopInit() {
    // start with LOW output (led off)
    output.set(false);
    startTimeSeconds = Timer.getFPGATimestamp();
  }

  @Override
  public void teleopPeriodic() {
    double now = Timer.getFPGATimestamp();
    if (now - startTimeSeconds >= 1) {
      // 1 second has passed since last change of voltage

      // get the current state of the output and reverse it.
      // so if it was HIGH, we now switch to LOW or vice-versa.
      boolean isHigh = output.get();
      output.set(!isHigh);

      // store the time of the change
      startTimeSeconds = now;
    }
  }
  ...
}
```

#### Digital Input Interrupts

Normally processors execute one set of instructions, one instruction at at time. However, sometimes certain situations occur which the processor must handle immediatly, like certain errors or problems in execution, or devices which require special attention. This is what interrupts are for. They are basically special requests to the processor to stop the current code execution and execute a specialized code which will handle the unique situations. At any given moment we may request the processor to do this by sending it an interrupt signal. And so interrupts are the corner-stone for many functionalities handled by modern computers. Of course, interrupts have their limitations and should be used for everything as it will cause the computer to freeze. But when used wisely, they are quite important.

One possible use is to send an interrupt when a certain digital signal is received over a digital port. This will allow us to quickly handle this signal as soon as it arrives. If we didn't use interrupts, we would've had to wait for the processor to finish what's its doing. Consider: `teleopPeriodic` is executed every 20ms by the processor. Without interrupts we would have to wait up to 20ms to handle a digital signal, so when it comes to time-sensitive tasks, we have a problems. But interrupts solve this as we are now able to react to changes in the digital signals almost immediatly.

The processor can be configured for two types of interrupts from digital signals:
- RISING interrupts occur when the digital signals changes from LOW to HIGH
- FALLING interrupts occur when digital signals changes from HIGH to LOW

We won't be writing interrupt handlers ourselves for the RoboRIO, as this functionality is hidden from users. But we will be using code which depends on them.

#### Digital Input Counters

Counters are specialized code devices which use digital input interrupts. They are quite versitile and are the basis for interacting with many devices. They allow us to count and measure digital pulses over the digital pins of the RoboRIO. Because they use interrupt handlers they can react to short and quick changes. For example, they can measure digital pulses in microsecond accuracy.

Counters have multiple operation modes, each can be used to working with different devices
- Two pulse mode: counts edges on two input ports, +1 for edge from one port and -1 for edge from the second port.
- Semi period mode: used to measure length of a pulse on a single input port.
- Pulse length mode: used to count based on the length of the pulse received +1 or -1 depending on the length.
- External direction mode: basically two pulse mode + an external port which indicates which channel should be +1 and which should be -1.

We will look at examples for two of these modes.

A two pulse mode counter will count the edges (RISING/FALLING) on two digital ports. It has an up port, whose edges are counted by adding +1 to the count and a down port, whose edges are counted by adding -1 to the count. This mode is useful when working with quadrature-encoded signals, like relative encoders.
```java
public class Robot extends TimedRobot {
  
  private DigitalInput inputUp;
  private DigitalInput inputDown;
  private Counter counter;

  @Override
  public void robotInit() {
    // create the ports
    inputUp = new DigitalInput(0);
    inputDown = new DigitalInput(1);

    counter = new Counter(Counter.Mode.kTwoPulse);
    counter.setUpSource(inputUp);
    counter.setDownSource(inputDown);
    counter.setUpSourceEdge(/*should count on rising edge*/ true, /*should count on falling edge*/ false);
    counter.setDownSourceEdge(/*should count on rising edge*/ true, /*should count on falling edge*/ false);
  }
  ...
  @Override
  public void teleopPeriodic() {
    // gets the edge counts
    // basically, based on our configuration, the count actual to amount of rising edges on channel 0 - amount of rising edges on channel 1.
    int count = counter.get();
  }
  ...
}
```

A semi period mode counter will count both the rising and falling edges on a single input port, as well as measure the length of this pulse. It can be used to read pulse-width modulated singles.
```java
public class Robot extends TimedRobot {
  
  private DigitalInput input;
  private Counter counter;

  @Override
  public void robotInit() {
    // create the ports
    input = new DigitalInput(0);

    counter = new Counter(Counter.Mode.kSemiperiod);
    counter.setUpSource(input);
    counter.setSemiPeriodMode(true);
  }
  ...
  @Override
  public void teleopPeriodic() {
    // returns the length of the last received pulse in seconds.
    double lengthSeconds = counter.getPeriod();
  }
  ...
}
```

#### Example - HC-SR04 Ultrasonic

