## Control Loops

When controlling a specific system we generally wish to control a specific set of states of the system in an attempt to change the current state of the system to a very specific state. For arm systems this could be its angle (position), which we try to move such that it is at a specific angle. For an elevator system this could be its height and so forth. This need to exert control over the system can be simple or complex, depending on the system and what we aim to do with it. 

Control loops are mechanisms used to control the state of some system. It is a fundamental building block for many systems which require adjustment to a desired state: air conditioners need to operate such that the room temperature reaches a certain point, fans need to operate such that they reach a certain speed, elevators must reach the desired floors and so on.

### Terminology

Before moving on, we should cover some basic terminology used in the field of control systems:
- _System_: the mechanism we wish to control, composed from output devices (like motors) and sometimes feedback devices (sensors). In a control loop, we seek to control a certain property of the system, it can be its position, velocity or more.
- _Process Variable_: The current state of the property we wish to control in the system. It can be speed, position or more, depending on what we want to control. This value changes over time as the system moves, say its speed increases, or its position changes.
- _Set Point_: the state we wish the system we control to reach. It describes the same property as the _Process Variable_, but instead of the current state, this describes the wanted state.
- _Rise Time_: describes the time it takes the system to reach the general area of the wanted _Set Point_
- _Settling Time_: describes the time it takes the system to settle on the wanted _Set Point_ after the _Rise Time_.
- _Steady State_: describes the state at which the system has settled on a constant state, i.e. the state has stopped changing.
- _Gain_: a constant which affects the whole output from a mechanism. A gain of 0.1 will yield a 10% output for the system. Increasing or descrasing the gain changes how strong (how big the absolute value is) the output is.
- _Error_: the difference between the _set point_ and the _process variable_, $set \ point - process \ variable$.

### Basic Properties

A system generally has many properties to it, some of which we want to control. Which is the whole point of these control loop: to control the state of these properties. 
In an elevator, we may wish to control its height (position). For a shooter, we may wish to control its speed (velocity). These systems, of course, have other properties, some of which are
affected by our control loop, even if unintentionaly. In a shooter, we may only wish to control the velocity, but our control of velocity also affects positioning and acceleration as these properties are related.
So, normally, we would only wish to control a single property, but we would affect other related properties as well. More complex control may seek control these other properties as well.

Each system has an output (or outputs). These are the components of the system we can exert control over (typically motors in FRC). By making changing to the output of the system we can affect the state of the property we wish to control. If we wish to control the velocity of a wheel connected to a motor, we can increase the output of the motor to achieve heigher speeds.

Depending on the system, the output may or may not have a clear relationship with the actual property we wish to control. This relationship drastically affects what kind control loop we want. In FRC, when working with motor, we generally have control over the voltage provided to the motor. This voltage has a proportional relationship with the _free speed_ of the motor. So for a free spinning motor, we can describe the relationship as $speed = {output \ voltage * max \ free \ speed \over max \ voltage}$. The change in voltage also affects the current passing to the motor and thus its torque. So by controlling the voltage, we force changes to the motors' behaviour and can affect certain properties of a system. When working with an arm, the position of the arm is dependent on the orientation of the shaft connected to the motor. So rotating the motor, moves the shaft and thus the arm. 

This kind of relationship is essential for a control loop, without an ability to change the state of the system, we have no ability to control it. The more clear and direct the relationship, the easier the system is to control. More complex system with more outside variables are more difficult. Say a drive wheel has to contend with floor friction which is affected by the materials, weight of the robot and more. There is still a relationship between our control over voltage and the actions of the system (change in voltage increasing current and thus provides more torque to the system). So we can still control this system, but it is harder to understand the connection between the output we can control to how it affects the system.

We typically use a graph of the _Process Variable_ over time as a way to observe and describe the behaviour of our system. The graph below shows the process variable over time as we attempt to correct it to the setpoint.

![pv over time](https://github.com/Flash3388/Workshops-2024/assets/17641355/476e6151-4565-4fd7-a954-b86507bf186c)

We can divide the graph into a few parts:
- _rise time_ is the time it takes us to first reach the set point. We aim for this time to be as small as possible as to reach our goal fast.
- _settling time_ is the time it takes us to fix misses until we reach a steady value. We aim for this to be small as to become steady quickly.
- _steady state_ is a state at which the process variable is steady and mostly constant. This state is our wanted result, as long as it is around the set point. We can become steady with the process variable not being equal to the set point.

The value of the process variable and its measurement unit is dependent on the system and is not actually limited. For velocity we can use meter per second, or RPM. For position we can use inches or centimeter. It generally doesn't matter which,
as long as the values are consistent: all the values refer to the same state of the same system using the same measurement unit.

### Open Loop

Open loop control is the most basic form of control over a system. It can be considered as dumb or static, as it has no true knowledge of the real state of the system. Instead, this form of control makes an assumption about the operation of the system and its state to perform an action. This kind of control is also called feed-forward, as we feed the system before knowing anything about its state.

![open loop](https://github.com/Flash3388/Workshops-2024/assets/17641355/5d81dc51-d979-4f7f-a4b0-578f48b0e17f)

Such control loops depend on knowing (or discovering) the relationship between the output and the controlled state. By knowing the relationship, we can calculate ahead what output is necessary to reach a specific state.

In the flywheel (a wheel spining freely) we said the relationship is

$$speed = {output \ voltage * max \ free \ speed \over max \ voltage}$$

According to the specs of a _NEO 1.1_, this relationship is

$$speed = {output \ voltage * 5675 \over 12 \ V}$$

![neo 1.1 specs](https://github.com/Flash3388/Workshops-2024/assets/17641355/6e0ad831-aaa4-4be4-b902-bb73724e1751)

We can then flip the equation to calculate the output to get a desired speed

$$output \ voltage = {speed * 12 \over 5675}$$

So we know exactly how to calculate the output to get a desired speed of rotation. We can then just feed this value to a motor and thats it. However, in basic open loop control, we have no way to really verify that we are correct in real time. Because there is no form of feedback to indicate that we actually got the wanted speed. This makes this kind of control very limited for a lot of purposes, because it cannot account of variables that were not considered in the calculation. 

One such example with our flywheel is the internal friction of gearboxes attached to the motor. This friction will cause a loss of speed, and thus the resulting speed may not be the same as we exepcted. 

The following command illustrates a basic example of open loop control
```java
public class RotateMotor extends Command {

  private final WheelSystem system;
  private final double wantedRpm;
  private double output;

  public RotateMotor(WheelSystem system, double wantedRpm) {
    this.system = system;
    this.wantedRpm = wantedRpm;

    addRequirements(system);
  }

  @Override
  public void initialize() {
    output = wantedRpm / 5675.0;
  }

  @Override
  public void execute() {
    system.rotate(output);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    system.stop();
  }
} 
```

### Closed Loop

Closed loop control is generally seen as a smart type of control. It uses sensors (feedback source) to operate the system into a desired state, instead of just relying on calculation. This allows it to perform percise tasks that involve outside, unpredicted variables affecting our system. This control is also called _feedback_ because we rely on a feed source (sensor) and react acording to it.

![closed loop](https://github.com/Flash3388/Workshops-2024/assets/17641355/ac3ebe04-8232-462b-b3c6-0ed83794db6d)

The goal of feedback control can be simplified as _reaching an error which is as close to 0 as possible_. The error is described as the difference between the _set point_ and the _process variable_. It does so by periodically checking the sensor value to see what the current error is, and changes the output so to decrease that error. 

For feedback control to be possible at all, we must have some sort of relationship between our output control capability and the state we wish to control. We must also have the ability to measure the state we wish to control. This means a sensors that measures this state. Without it, we have no way to correct errors based on the sensor value and must resort to open loop control.

The following pseudo code describes the general look of a closed loop control
```java
currentState = getSensorValue();
while (!isStateReached(currentState)) {
  output = calculateNextOutput(currentState);
  system.setOutput(output);

  currentState = getSensorValue();
}
```

Because our robot loop runs every 20ms, that means the the control loop also operates every 20ms. This makes our control loop into Discrete time systems. Discrete time signals are a time series, meaning that not every time point has a value. This is the case for us because we run in a 20ms loop, so we can only query the sensor value every 20ms. This gives
us a time series with a value every 20ms. Our control loops are based around that, these loops run every 20ms (in our commands or periodic method in `Robot`) and only then can they check the errors and adjust 
outputs accordingly. This also means that we have a 20ms delay in our ability to react to changes, if something changes occurs between those calls, it will take us a few milliseconds to actually react to this change.

## PID

PID (Proportional, Integral, Derivative) is a closed control loop mechanism used by many real world systems to provide feedback control over the system. It is a pretty popular and well known mechanism, used by air conditioners, complex machinary, cars, and more.
It is composed from several different parts, each with different properties, but when well calibrated, they can produce pretty great results.

The general forumla is as follows:

$$output(t) = K_p * e(t) + K_i * \int_0^t e(T) \mathrm{d}T + K_d * \frac{d e(t)}{dt}$$

Where

$$e(t) = SetPoint - ProcessVariable(t)$$

This is a time based function, based on the $e(t)$ (error) function. For each time point, we get a different output as the state of the system changes with time. _Set Point_ is generally a constant value defined ahead of time as the wanted state of the machine. _Process Variable_ is the actual state of the system in a time point, which changes as time changes because our system moves and its state changes.

Our aim with PID is to drive the output such that we, in the end, reach an error which is as close to 0 as possible. This nets an $e(t)$ function which follows the behaviour below:

![e(t) wanted](https://github.com/Flash3388/Workshops-2024/assets/17641355/50ec2272-af8c-4367-b310-4d25339f8c8a)

Of course, in reality, we may not actually get this result, as it depends on the system and how we use PID, but this is an ideal behaviour.

The process variable, set point are basically the inputs of this formula, and the output they yield is the output we then pass on to our system to use as output. So a general pseudu code for use is:
```
while run control loop:
  output = calculate pid (current process variable, set point)
  set system output (output)
```

The actual meaning of the output value is dependent on what we decide. At its core, the output measurement unit is based on the input: if the process variable and set point describe an elevator position in meters, so will the output have such a value. However, the is a big discrepancy between the state we use and the output we use. In FRC, with motors, our output is generally either voltage or _PrecentVBus_, so we cannot use an output of meters really. To scale between the two, we use a set of _gains_ so that the output value is usuable as a voltage (or _PrecentVBus_). This scaling is configured in a calibration process which is different for each kind of system we work on.

The value of $e(t)$ can also be negative. This occurs when the value of the _process variable_ is bigger than the value of the _set point_. This is completely normal and can be seen in several situations. One case if when we start with a _process variable_ which is bigger than the _set point_. Consider an elevator which starts in the 3rd floor and needs to get to the 2nd floor, the height of the elevator is bigger than the destination. Another situation is when, during our control loop, we miss the _set point_, so the _process variable_ can become bigger than the _set point_ and we know have to correct the system back to the _set point_. In a case of a negative $e(t)$ we would get a negative output, which is fine, as it indicates we need to move in a different direction (say, down). It can also work well with our motors, as negative or positive signs indicate the direction of motion.

![pv above sp](https://github.com/Flash3388/Workshops-2024/assets/17641355/0bb88a27-5f77-4198-85ef-b5715f468b90)

![set point miss](https://github.com/Flash3388/Workshops-2024/assets/17641355/a5452814-6ce6-4622-ad71-0f14491ce9b7)

In our code, we work with a 20ms loop. That means we can only run the pid calculation every 20 ms. This creates a discrete time system rather than a continous one. So the formula above is normally implemented as such:

$$output(t) = K_p * e(t) + K_i * \sum_{T=0}^{t} e(T) + K_d * \frac{e(t) - e(t_{n-1})}{t - t_{n-1}}$$

This 20ms run period is actually important to note as it has several implications. First, it means that we can only make a change to the output once every 20ms. If some outside variable is affecting our system faster that 20ms, than we will be unable to respond appropriatly. So our system is less responsive, and can make fewer adjustments to the system. PID loops that run faster can produce better results as they can twick the output at a faster rate in response to sensor changes. 

### Proportional

$$Kp * e(t)$$

The porportional component of PID produces an output based on a porportional response to the $e(t)$ function. So for each time point, the output is proportional to the error of the system. This is the main driving force for our output. Because it is proportional, the larger the error - the bigger the output, the smaller the error - the smaller the output. This is a pretty logical response to errors and should be our first focus.

The meaning of the output of this components is tied directly to the values used in $e(t)$. If we work on a system's velocity in RPM, the output of $e(t)$ will be the error in RPM between the _set point_ and _process variable_ (say `2000 RPM`). This of course, is not a value we can use for a motor. So for that we have the _gain_.

![pv and p](https://github.com/Flash3388/Workshops-2024/assets/17641355/c0e1337c-a0e2-422f-86aa-b67bb7c44762)

$K_p$ is the _gain_ which controls the size of the output relative to the error. We use the gain to scale and configure our response to errors. The larger the gain, the larger the output will be in proportion to the error. So, say $e(t) = 2000$, with $K_p=0.0001$, we get an output of $0.0001 * 2000 = 0.2$. This is a more apropriate output to a motor (its between `-1` and `1`). 

You should also note that, as $e(t)$ decrease, so does our output, which means that the closer we get to the target, the smaller the output. This is a good thing, as it helps acheive a better accuracy, because there is a smaller chance we'll miss the _set point_ due to inertia. 

If the _set point_ is missed, the value of $e(t)$ becomes negative, as the _process variable_ is bigger than the _set point_. This will make our total output negative. This is useful, as it indicates that we need to move in the other direction in order to return to the _set point_. And it works well with our motors, as a negative value to the motor will indicate a rotation in the opposite direction. We should also remember that when we miss our _set point_, we have some inertia, and we must reverse the inertia in order to start moving in the other direction. 

The actual value we choose for $K_p$ is key in acheiving great results. 

When the value of $K_p$ is too low, it will cause a system to take much longer to reach the wanted system. Further more, when we have a small error, the output from this component may be so low that the system will not continue moving, getting us stuck at a state which is close to the _set point_ but not exactly at the _set point_. This is called a _steady state error_, i.e. the system is steady, but it is not at the _set point_

![steady state error](https://github.com/Flash3388/Workshops-2024/assets/17641355/36422e56-b491-4ca4-9fee-01f9ea6f8713)

When the value of $K_p$ is too high, it will cause a system to reach the _set point_ faster, but it will also make it miss the _set point_. Furthermore, when we do miss and the output turns negative in order to return back to the _set point_, we will likely miss the _set point_ again because the output is too high. This causes a pattern of oscillation around the _set point_. The oscillation may stabilize in time, but it may not, depending on the system and the $K_p$

![oscillation](https://github.com/Flash3388/Workshops-2024/assets/17641355/b2d86fd5-564f-42b7-b99a-c311ef1cad14)

So we must work to find the best $K_p$ for our system. And it really is system specific, due to a combination of the mechanics, the motors, what forces are at work and more. To find the best value
we perform a set of calibrations for the system, which we will discuss later.

### Integral

$$K_i * \int_0^t e(T) \mathrm{d}T$$

The integral component of PID produces an output based on an integral response to $e(t)$. an integral of a function is basically the area between the function and the `x` axis between two points. In the error function, this translates into the sum of all the errors between point `0` and the current time point. 

![area of error function](https://github.com/Flash3388/Workshops-2024/assets/17641355/5efceac3-1c69-4a3e-8dee-1e049efab494)

In essense, what it means is that the value of this component is basically the sum of all the errors from the start until the current time. As such, we can look at this component as acting on past errors of the system. For a discrete system we will represent this component as:

$$K_i * \sum_{T=0}^{t} e(T)$$

Implementations of PID generally implement the I component by using an accumulating variable, with the current error being added to the variable each iteration of the loop. So it is a basic sum of all the errors. Because of the past look at our system, we can essentialy use this component to compensate for accumulating errors in our system, and more specifically, steady-state errors. This is because steady state errors cause an ever increasing accumulation of errors for as long as the steady state error remains.

For a flywheel, with $SetPoint = 1000, Initial \ ProcessVariable = 0$, the output of the I component will be 0 at time point 1. At the next time point, with $ProcessVariable = 200$, the output of I will be `800`. At the next time point, with $ProcessVariable = 400$, the output will be `1400` and so on. As you can see, the longer we have an error, the larger I becomes. 

In a _process variable_ graph, the I value can be seen as the area between the function and the _set point_

![pv graph integral area](https://github.com/Flash3388/Workshops-2024/assets/17641355/48a7271f-68f2-4708-8445-53a2ddf40f3b)

It worth noting that this component can become negative, like $e(t)$. However, because it is the sum, when $e(t)$ becomes negative, the integral does not imediatly become negative, as it is a total sum of the errors. For it to be negative, the sum of errors when $e(t)$ is negative must be bigger than when $e(t)$ was positive. In other words, the area where the _process variable_ is above _set point_ must be bigger than the area where it was below. So, when we first miss the _set point_, the integral output is still positive, the more time we spend above the _set point_ the smaller the total error becomes as more negative errors are added until it becomes zero and starts becoming negative.

![pv and integral](https://github.com/Flash3388/Workshops-2024/assets/17641355/bbf35f91-c0ff-44aa-8257-9b5112710816)

$K_i$ is the _gain_ which controls the size of the output relative to the integral. We use the gain to scale and configure our response to errors. The larger the gain, the larger the output will be in proportion to the integral, same as with $K_p$ but operating with a different kind of output.

We can quite quickly see why a large $K_i$ can cause problems. Because of the large output of the integral (due to it being the accumulation of the errors), we end up with a pretty strong output over time. A big $K_i$ can easily cause us to miss the _set point_. More over, after missing the _set point_ we wont imediatly go back to it, as we first need to zero out the accumulated errors with negative errors. So, unlike with proportional output, in integral output, a miss of the _set point_ will cause a delay before actually attempting to fix the _set point_ with an output in the opposite direction. This is called windup. As a general rule, $K_i$ should be significantly smaller than $K_p$ and generally only used to deal with steady-state errors. 

Of course, even if we wish to use this for handling steady-state errors, we still end up with the accumulation of all errors. This makes windup harder to avoid, forcing us to use such a small $K_i$ that it barely has any effect on steady state errors. To solve this, most _PID_ controllers use an _IZone_. _IZone_ is defined as a range around the _set point_ at which the I component is actually used. As long as the error is bigger than this _IZone_, the error accumulator is set to `0`. When the error is within the zone, only than is error accumulated. This let's us define an area for handling steady-state errors without dragging old errors into the mix. The value of _IZone_ should use the same measurement units as the _process variable_. With a _set point_ at `5000`, and _IZone_ of `500` will start using I between `4500` and `5500`.

![izone use](https://github.com/Flash3388/Workshops-2024/assets/17641355/982581e7-8bda-4a11-961e-cf5a45c1e2c1)

Notice how in the graph above, inside the IZone, after the second miss we get a steady state error which is slowly being fixed thanks to our I component in the works.

### Derivative

$$K_d * \frac{d e(t)}{dt}$$

The derivative component of PID produces an output based on an derivative response to $e(t)$. A derivative of a function is a function which represents the slope of the original function, or in other words, it represents the rate of change of the original function. So for function $e(t)$, a derivative over time is $\frac{d e(t)}{dt}$, so its value for point _t_ is the rate at which $e(t)$ has changed over time. 

![slope of error point](https://github.com/Flash3388/Workshops-2024/assets/17641355/5a05df7c-f0d5-4661-b472-71380670782d)

In essence, this means that the value from this component is the rate of change of the error (its velocity) at a time point. For a discrete system we will represent this component as for time point _n_:

$$K_d * \frac{e(t_n) - e(t_{n-1})}{t_n - t_{n-1}}$$

Implementations of PID generally implement the D component as `currentError - lastError / runPeriod`. Because of this, we can look at this component as a future look at the system, as it basically references what is going to happen to the error, given its velocity. We can use it to slow down the response of the function, which makes it quite ideal to deal with oscillation, as oscillations have a relatively high velocity, and thus an apropriate D response.

The output from the D component is always going to be opposite to the outputs of P and I (in terms of sign). This is because it is the change rate of the error, so for a decreasing error, the D output will be negative, and for increasing error, the output will be positive. Due to this opposite sign, the D components basically decreases the absolute output from the P and I components, hence slowing the response down. 

![error + D](https://github.com/Flash3388/Workshops-2024/assets/17641355/40601dc8-7bdd-4a44-8e81-dc317af472a1)
![pv + D](https://github.com/Flash3388/Workshops-2024/assets/17641355/fbbb7233-24fa-459c-b781-645e99b9ac13)

Because the _set point_ is generally constant, the D response can actually be seen as the negative rate of change of the _process variable_. So for a position _process variable_, D will be the negative velocity. For a velocity _process variable_, D will be the negative acceleration.

$K_d$ is the _gain_ which controls the size of the output relative to the derivative. We use the gain to scale and configure our response to errors. The larger the gain, the larger the output will be in proportion to the derivative, same as with $K_p$ and $K_i$ but operating with a different kind of output. 

A large $K_d$ will slow down the entire response of the system as it will negate the output from the rest of the system. In addition, the D component is quite sensitive to sensor noises, which is a common occurance. Sensor noises cause jump in the _process variable_, so the D sees an increased rate of change and thus produces a larger output. So D should be used sparingly with a small $K_d$. $K_d$ should be significantly smaller than $K_p$ and only used to deal with oscillations or to slow down strong responses from P.

With a properly configured $K_d$, a $K_p$ which produces oscillations can be smoothed out over time

![oscillation fixes](https://github.com/Flash3388/Workshops-2024/assets/17641355/aa383575-fb8a-4532-af73-82fc7d54eaa9)

The D component is also very susceptible to sensor noises. Sensor noise is common, and creates jumps in the _process variable_ over time. These jumps cause the velocity of the error to increase significantly at the point of noise, due to the rapid changes of the _process variable_. As such, the D output will increase violently in response to these noises, making it difficult to use. If sensor noises are a serious problem, avoid using D at all, or minimize the _gain_ as much as possible.

![noisy pv](https://github.com/Flash3388/Workshops-2024/assets/17641355/3e58b9cf-6e56-45ba-b549-d148e5634747)

The jumps in the _process variable_ function graph are noises which change the value of the sensor back and forth.

### Putting it all Together

On their own, I and D are pretty useless, and P doesn't provide the best response usually. The entire point of PID is utilizing the different components together to acheive an ideal response. But it is not a must, some systems can use only P, only P + D, only P + I, or all of P + I + D. The choise is ours. The choise of what to use should generally be done during the calibration after seeing what kind of response each component provides for the system. 

A general PID implementation will follow the following pseudo code
```
double pid(setPoint, processVariable):
  error = setPoint - processVariable
  totalError += error * period
  velocityOfError = (error - lastError) / period
  lastError = error

  return kp * error + ki * totalError + kd * velocityOfError
```

The choice of _gains_ is paramount here, as it will change the overall behaviour of the system and the PID use. The process of finding the _gains_ is called _tuning_, and we will discuss it later.

We sometimes also have to make a trade between speed and accuracy. We usually want to reach the _set point_ quickly, but sometimes we end up with a steady-state error. Now, we could fix the error with the I component, but this requires a properly calibrated I, as a low value will take a while to correct the error, and a high value can cause windups. So another option is to also be okay with this error. For certain situations, we don't need to be exactly on the _set point_, but rather its enough to get close to it and as such it would be faster to reach our destination. The consideration of when and how margin depends on our system and needs.

An important part of PID is also its ability to handle unexpected (or expected) external forces, temporary or not. A sudden outside effect will introduce an error, but PID will work to correct that error. Consider a shooter shooting balls. When the ball passes through the shooter, it creates friction and a loss of speed with the _process variable_ dropping. This will be fixed though, with the velocity returning to the _set point_ again thanks to PID output correction. So if we have to fire multiple balls one after another at a specific speed, PID will keep us at that speed.

![external force](https://github.com/Flash3388/Workshops-2024/assets/17641355/ef4fb5fb-f11d-4c6c-a8c2-304792776f19)

### WPILib's PID Controller

WPILib provides a ready for use `PIDController` which implements a pretty basic PID for use in our systems. We can integrate it for use with a command to control a system. Consider a tank drive looking to drive 3 meters forward on the floor. We can create a command to use PID to accomplish this task. For this to be possible, our system must have a sensor to measure the distance passed on the floor (usually an encoder which measures the rotation of the drive wheels). 

```java
public class DriveDistance extends Command {

  private static final double KP = 0.05;
  private static final double KI = 0.0001;
  private static final double KD = 0; // a zero KD means that the D output is zeroed out and not used. This gives as a PI controller.
  private static final double IZONE = 0.1;

  private final DriveSystem system;
  private final PIDController controller;

  public DriveDistance(DriveSystem system, double distanceToDriveMeters) {
    this.system = system;
    // initialize the controller, with our gains and wanted set point.
    this.controller = new PIDController(KP, KI, KD);
    this.controller.setSetpoint(distanceToDriveMeters); // configure setpoint
    this.controller.setIZone(IZONE); // configure izone

    addRequirements(system);
  }

  @Override
  public void initialize() {
    // reset the accumulated error data.
    // this is necessary so that if we run this command multiple times, it wouldn't run with accumulated error data which affects the I and D outputs.
    controller.reset();
  }

  @Override
  public void execute() {
    double processVariable = system.getDistancePassedMeters(); // get the sensor data
    double output = controller.calculate(processVariable); // calculate output for the motor with the PID equation
    system.move(output);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    system.stop();
  }
}
```

The use is quite straight-forward, we simply configure it and just need to use `calculate` to get the output for the motor and pass it on.

The only thing we haven't touch is `isFinished`. As we know, `isFinished` determines when the command will stop, so the question is: _when should we stop the command?_
There isn't one specific answer, as it depends on which system we work with and what we are looking to do with it. In this example, we probably want to stop when we've drived exactly 3 meters, as to arrive at our exact destination.

Normally we would want to stop when $e(t) = 0$, with a possibility for margin of error if accuracy isn't essential. However, observing just the error or the process variable isn't actually good enough, as consider the following motion graph:

![stopping pv](https://github.com/Flash3388/Workshops-2024/assets/17641355/67a98c78-2eb6-4ca6-a100-4691f7edad27)

Notice how $e(t)$ reaches 0 at several moments, but it continues moving. This is due to several misses. If we were to stop at one of those points with the misses, we would miss our _set point_ due to the inertia of the system. In order to know when we've actually settelled on, we should also take a look at the velocity of the $e(t)$. When this velocity is close to zero, than we have minimal inertia and will thus stop on the _set point_ as wanted. So our stop condition should be something like $e(t) < margin; \frac{de(t)}{dt} < margin$. `PIDController` actually provides us with some utility for checking this condition:

```java
public class DriveDistance extends Command {

  private static final double KP = 0.05;
  private static final double KI = 0.0001;
  private static final double KD = 0; // a zero KD means that the D output is zeroed out and not used. This gives as a PI controller.
  private static final double IZONE = 0.1;
  private static final double POSITION_ERROR = 0.1;
  private static final double VELOCITY_ERROR = 0.01;

  private final DriveSystem system;
  private final PIDController controller;

  public DriveDistance(DriveSystem system, double distanceToDriveMeters) {
    this.system = system;
    // initialize the controller, with our gains and wanted set point.
    this.controller = new PIDController(KP, KI, KD);
    this.controller.setSetpoint(distanceToDriveMeters); // configure setpoint
    this.controller.setIZone(IZONE); // configure izone

    // configure error margin
    this.controller.setTolerance(POSITION_ERROR, VELOCITY_ERROR);

    addRequirements(system);
  }

  @Override
  public void initialize() {
    // reset the accumulated error data.
    // this is necessary so that if we run this command multiple times, it wouldn't run with accumulated error data which affects the I and D outputs.
    controller.reset();
  }

  @Override
  public void execute() {
    double processVariable = system.getDistancePassedMeters(); // get the sensor data
    double output = controller.calculate(processVariable); // calculate output for the motor with the PID equation
    system.move(output);
  }

  @Override
  public boolean isFinished() {
    return pid.atSetpoint();
  }

  @Override
  public void end(boolean interrupted) {
    system.stop();
  }
}
```

`atSetpoint` will return `true` when both the error and error velocity are below the configured margin, indicative as settling on the _set point_.

### Constant Output

For some systems, we will find ourselves required to apply continous output for proper function even after reaching the _set point_. Take a basic elevator, gravity will attempt to pull the elevator down. To prevent the elevator from dropping the motor must be set with an output that applies enough torque to cancel out the gravitational force. This means that we cannot simply stop our control loop and motor, because the elevator will lose its position and the entire effort becomes pointless. There are many types of systems and usages where we would need to apply continous output to keep the system at the _set point_ for as long as we need. 

The problem with feedback control, is that it works upon the error. When we reach the _set point_ and $e(t) = 0$ the proportional output will be basically 0, and thus our output will likely be minimal. This means that nothing is going to keep our system at the _set point_ if there is an external force applied to it. This can be a pretty big problem for systems that require a continous output. We may use the I component to keep some output by depending on the error accumulated before we reached the _set point_, but it's not really consistent. In reality, what will happen is that the external force will move the system and introduce a new error to the system which the control loop will notice and correct. So we'll get stuck in a loop of reaching the _set point_, losing the _set point_ and returning to it. Depending on the system and our _gains_, this main be unnoticable because our control loop will react after a max of 20ms and can be quite quick to zero out the error.

![error fixing](https://github.com/Flash3388/Workshops-2024/assets/17641355/bcad236e-dcb9-410d-a58b-861266c42432)

Other kinds of systems that require constant output are:
- arm: an arm usually has to fight gravity which attempts to pull it back down
- flywheel: for a flywheel to keep its speed, the motor must continue rotating. Stopping the motor will cause a loss of inertia due to internal friction.

For a constant output, the finish condition (in `isFinished`) will normally not depend on the actual state of the system any longer, as it depends on when we actually want to stop _being_ at the _set point_. This could be dependent on time, or a different state or another system. What the condition is will change between our systems, usage and robots. 

### Changing Set Point

Occasionally we may encounter control loops which need to handle the possibility of the _set point_ changing while they run. Such situations will usually be shown in commands that have their _set point_ changed during execution. This is entirely legitimate, as things can change and a descision to change the set point can occur. 

Consider a drone, it uses PID to keep itself stable in the air, but it also receives order to move forward or backward. It cannot start a new command to handle these as stopping the current command will cause the control loop to stop and it will lose stabilization and fall. So instead it has to keep the same loop running (the same command) and simply modify the _set point_ instead. 

The change is _set point_ can have quite a big influence on our control loop, as at the moment of change, $e(t)$ will jump in value according to the new _set point_, and as such, the different components will react to this sudden change.

![set point change](https://github.com/Flash3388/Workshops-2024/assets/17641355/8c9c30c1-354e-4f8d-a77f-2a7e5eb9eb58)

The P component will react pretty normally, as it just looks at the current $e(t)$. The I component will carry on all the accumulated errors so far into the motion to the next _set point_, this can influence the response, mostly by causing a windup due to the sudden additions of new errors. The D component will react the most violently, as essential, at the point of _set point_ change, its output will be magnified significantly depending on the new _set point_. We can look at it as following

$$\frac{e_{postChange}(t) - e_{preChange}(t)}{period} = \frac{(SetPoint_2 - ProcessVariable_2) - (SetPoint_1 - ProcessVariable_1)}{period}$$

If the _process variable_ hasn't really changed over the time period, so that $ProcessVariable_1 = ProcessVariable_2$, we get

$$\frac{SetPoint_2 -SetPoint_1}{period}$$

So our output at the point of change can get quite violent, and unlike normally, the output is in the direction of the new _set point_, so for a larger _set point_ we get a positive D output. This drive the change pretty high at first. However for subsequent runs, this is less noticable. Though, interestingly, if the _set point_ is continously changing, the D output will keep pushing us towards the changing _set points_ pretty quickly. So, with a properly configured $K_d$, they can be quite useful for tackling changing _set points_, but it should be done careful as the huge spike in output can cause easy misses or potentially damage the system. 

### Tuning

_Tuning_ is the process of discovering the ideal _gains_ for our control loops. Because each system is different mechanically, each system will require its own unique set of _gains_ tailored for it. So this process will have to be done for each system. Even if its the same system, but it has gone through changes since our last _tuning_, we will be required to tune the system again. So you should get quite comfortable with the idea of having to tune things again and again. It is also quite an involved process, requiring quite some time.

Before we can tune, we must first try to understand what is considered and ideal response. Normally we would seek to get a quite and fast reach to the _set point_, such that _rise time_ and _settling time_ are as small as possible, with as little misses or oscillation as possible. It should also be clear that we won't be able to acheive an ideal response always, so our aim is to get the best we can from the system. It should also be noted that different systems respond differently and have limits, so how good we get the response is always relative.

The following examples show case acceptable responses

![ideal response 1](https://github.com/Flash3388/Workshops-2024/assets/17641355/58b0e2db-687e-48c5-9af3-08e5d4ac1aa6)

![ideal response 2](https://github.com/Flash3388/Workshops-2024/assets/17641355/a188399e-fc52-4465-b985-397d768a97b2)

![ideal response 3](https://github.com/Flash3388/Workshops-2024/assets/17641355/9edcd7d2-bece-43f9-b986-43aa9338253a)

There are several different approaches to this, but we will be taking the most basic approach for now. In order to tune, we must observe the state of the machine in real time and be able to modify the _gains_ we we do so. To do this, we will be using the _shuffleboard_ to place information and view this information. We'll be using the _test_ mode for this one, as its really basic and can allow us to quickly write the PID loop code into it and be able to test it without needing to run any commands.

```java
OurSystem system; // initialized in robotInit
PIDController pidcontroller;

@Override
public void testInit() {
  // initialize the controller for use with zero gains
  pidcontroller = new PIDController(0, 0, 0);
  // place it on the shuffleboard with a key we can find later
  SmartDashboard.putData("PID", pidcontroller);
}

@Override
public void testPeriodic() {
  // get the process variable and display it on the shuffleboard
  double processVariable = system.getSensorData();
  SmartDashboard.putNumber("ProcessVariable", processVariable);

  // calculate the output. This time the set point isn't provided here, but rather it will be taken automatically from the value on the shuffleboard
  double output = pidcontroller.calculate(processVariable);
  SmartDashboard.putNumber("PIDOutput", output);

  // set the output
  system.setOutput(output);
}
```

By using the `Robot` instead of command, we can have a skeleton code which runs always, this makes it easier to work with instead of having to modify and mess with commands. This code is also temporary and should be deleted later on. The use of the _test_ mode is because _teleop_ and _autonomous_ are likely used to run different things, as they have in competition use, which _test_ mode doesn't have. Run the code and configure your shuffleboard display to follow something like this:

![shuffleboard display](https://github.com/Flash3388/Workshops-2024/assets/17641355/5bb56a0d-c646-456b-b66f-5bfdb5ec936c)

You can see we have a view of the _process variable_ and pid output in graphs, plus control over the _gains_, _IZone_ and the _set point_. I recommend using sliders for the _gains_, they make the process a bit easier.

To perform the tuning we will follow these steps:
1. Set $K_p, K_i, K_d, IZone$ to 0.
2. Pick and set a _set point_. Should be a basic, easy to reach _set point_ at first.
3. Increase $K_p$ slowly until the _process variable_ graph starts to oscillate. When it starts, decrease $K_p$ slowly until it stops.
4. Once the $K_p$ produces a stable response, disable the robot and reset the _process variable_. Then enable the robot again and see that $K_p$ still produces a stable response.
5. If the system hasn't reached the _set point_, and is stuck with a steady-state error, increase $K_i$ slowly until the system has reached the _set point_ has stabilized. Repeat step 4 to see that the addition of I hasn't introduced new problems. Reduce $K_i$ if windup has occured.
6. If the system is moving too fast, or still has a few oscillations, slowly increase $K_d$ until the oscillations have smoothed out and the system speed has dampaned. Repeat step 4 to see that the addition of D hasn't introduced new problems.
7. Twick around with the _gains_ to see if the response can improved any further.
8. Test different _set points_ and see that the system responds properly.

![shuffleboard example](https://github.com/Flash3388/Workshops-2024/assets/17641355/43c3c170-7417-4ffa-a36a-940c512e7cb0)

Checkout the _further reading_ section for tuning simulation to try out tuning different systems. Although this are simulations, they should show pretty well how systems respond to the different components.

## Feed Forward

Feed forward control is an open control loop, meaning it has no reliance on feedback data, but rather on calculation and the known dynamics of the system to drive the output to the wanted _set point_. This approach may seem less useful than feedback control, however, that is not true at all. There quite a few situations where _feed forward_ is very useful and even necessary. Interestingly, feed-forward output is generally constant over time (or at least over _set point_).

There are many kinds of feed-forward equations, each useful for different situations. We will be observing a simple, 2 step equation here.

$$K_s * Sign(SetPoint) + K_v * SetPoint$$

![pv feedforward](https://github.com/Flash3388/Workshops-2024/assets/17641355/ec5aaf6b-1ffa-42a5-882b-de8ea86568b0)

Unlike feedback control, feedforward control is generally more linear due to the constant ouput. And it lacks the ability to fix itself, so the output must be calculated to be precise as misses won't be corrected at all. This simplified the situation and tuning any gains, but also significantly limits the control ability to handle external unexpected forces.

![pv feedforward miss](https://github.com/Flash3388/Workshops-2024/assets/17641355/09f3e7ff-5a71-4296-a6ec-33aa33399c16)

### Static

$$K_s * Sign(SetPoint)$$

The static component of the feed-forward apply a static output value. That is, once this value is chosen, it is not changed at all. Though it is possible to play around with the sign of the output to change its direction. The value of this component is directly from the $K_s$ _gain_. This makes it quite unique, as usually the _gain_ is a modifier of the output, but that is not the case here. As such, this gain's value should correlate to our output values (-1 to 1 for motors). 

Because of the static nature of this output, it doesn't have many uses, as static outputs would generally only be helpful for reaching a specific _set point_. However, it can be quite useful for applying a constant ouput, for combating constant external forces, something that feedback control isn't really good at. So, for example, with an elevator, we can use a static feedforward to keep the elevator in place, by configuring the output to provide enough torque to keep the elevator in place.

### Set Point Proportional

$$K_v * SetPoint$$

Another more useful option for feed-forward is actually an output which is proportional to the _set point_. This is great as it provides some flexibility for the output, and thus enables this form of control to actually be capable of driving a system output on its own. For system dynamics with proportional relationship between the output and the controlled state, this is quite perfect.

Actualy, driving a flywheel to a set velocity can be done with _feed forward_ on its own, as, like we've seen, the output voltage and the motor speed are proportional to each other. A properly configured $K_v$ is required for this work though, and actually, this gain is basically the relationship function between the two. Find it is a matter of simply trial and error.

### WPILib's FeedForward

WPILib actually features multiple feed forward equations in different classes. We will be using the `SimpleMotorFeedForward` here, which provides the kind of feed forward we just discussed. These classes don't have much to them as feed-forward is not as complex as pid.

`SimpleMotorFeedForward` is actually intended to use voltage as its output, and should be used as such. This makes $K_s$ as a voltage value and $K_v$ measured as volts per measurement unit (volts per rpm in out case). For proper use of this class, use these measurement units for consisent and proper use. 

The following command uses feedforward to rotate a flywheel at a given speed.
```java
public class RotateAtSpeed extends Command {

  private static final double KS = 0.05;
  private static final double KV = 0.0001;

  private final DriveSystem system;
  private final double setPoint;
  private final SimpleMotorFeedForward controller;

  public DriveDistance(DriveSystem system, double speedRpm) {
    this.system = system;
    this.setPoint = speedRpm;
    this.controller = new SimpleMotorFeedForward(KS, KV, 0);

    addRequirements(system);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    double output = controller.calculate(setPoint);
    system.rotate(output / 12.0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    system.stop();
  }
}
```

## Combining Feedback and Feed-Forward

Feedback and Feedforward may seem as opposites, but they are actually quite complamantry to each other. Each can be used on its own, but combining them produce superior results. Combining them is a simple matter of summing their outputs before passing the output to the system. The big question will be when to combine them and how to calibrate the _gains_ for each as a result.

The following command uses both PID and feedforward to rotate a flywheel to a given velocity.
```java
public class RotateAtSpeed extends Command {

  private static final double KP = 0.05;
  private static final double KI = 0.0001;
  private static final double KD = 0; // a zero KD means that the D output is zeroed out and not used. This gives as a PI controller.
  private static final double IZONE = 0.1;
  private static final double KS = 0.05;
  private static final double KV = 0.0001;

  private final DriveSystem system;
  private final PIDController controller;
  private final SimpleMotorFeedForward feedforward;

  public DriveDistance(DriveSystem system, double speedRpm) {
    this.system = system;
    this.controller = new PIDController(KP, KI, KD);
    this.controller.setIZone(IZONE);
    this.controller.setSetpoint(speedRpm);
    this.feedforward = new SimpleMotorFeedForward(KS, KV, 0);

    addRequirements(system);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    double processVariable = system.getSpeedRpm();
    double output = controller.calculate(processVariable) + feedforward.calculate(controller.getSetpoint()) / 12.0;
    system.rotate(output);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    system.stop();
  }
}
```

As a rule of thumb, feedforward control should be combined with PID when
- A constant output is needed to keep the system at the _set point_. Feed forward is excelent for such uses, as it provides a constant output when PID cannot (because it doesn't look at the error).
- For control loops controlling velocity. 

### Velocity Loops

Velocity loops have a few problems when working strictly with PID. This is due to PID's response for changing $e(t)$ around misses of the _set point_. When the _set point_ is missed, the output changes sign. For velocity control, this is not ideal, because it requests the motor to rotate at a reverse direction, causing several problems:
- changing rotation direction while there is still inertia causes current spikes and overheating, which is bad for the motor
- the change in rotation direction causes a quite strong loss of speed, leading to aggressive misses and oscillations

![pv + output](https://github.com/Flash3388/Workshops-2024/assets/17641355/2ad08bfd-8093-4dff-be37-31424b804595)

This does not mean it won't work at all, as changing rotation direction will slow down the rotation until the motor rotates in the opposite direction, but it is too aggressive, causing repeated misses.

For velocity loops to work better, an introduction of feedforward is essential. Using feedforward we can actually drive most of the output in a constant value and use the PID for smaller adjustments. As long as the PID output is smaller than the feedforward output, the total output will not change direction (sign), it will simply decrease or increase. This is a much better option.

![feedback and feedforward](https://github.com/Flash3388/Workshops-2024/assets/17641355/84ca6eb5-928b-4837-9112-88620528447d)

### PIDF

Most PID controller actually provide integrated feedforward output in the form of another part to the equation. This new component, F, is basically a _set point proportional_ feed forward. 

$$output(t) = K_p * e(t) + K_i * \int_0^t e(T) \mathrm{d}T + K_d * \frac{d e(t)}{dt} + K_f * SetPoint$$

This simplifies our code by allowing us to use just a simple combined controller. Though WPI's `PIDController` does not support this, but we will see others that do in the future.

## Examples

**TODO**

## Motor Safety

The subject of motor safety is always important, but with control loops its even more essential, as the direct control of the motor is in the hands of our code, not us. As such we will have to take several precautionary steps to ensure we don't cause any damage.

To understand what to avoid, lets first discuss what would damage a motor. Generally, the biggest danger we will likely have to face is over-current, and mechanical damage. 

Mechanical damage can normally be caused by either the system operated by the motor hitting something so that it is damaged itself or damanges something else; or be caused by too much force applied to the motor shaft, which breaks it. DC motors are capable of quite a bit of torque and speed. This means that the mechanisms they control can be quite fast and hit things fast. A drive chassis moving quickly can collide with things causing damage. A motor picking up heavy objects, or fighting external forces, can apply a lot of torque, enough to even break metal over time. So an arm carrying a heavy load may lead the motor to apply enough torque to break the shaft. Or if the arm moves too fast it may hit another part of the robot.

The torque motors apply is proportional to how much torque they have to fight against to move. The more they are resisted, the more torque they apply. For high torque, the motor requires more current to be supplied. So when a motor strugles to move, it will be drawing a lot of current. This causes drastic increases in temperature and could lead to insulating materials getting burned. This will be avident by smoke coming of the motor. This can also develop into a fire.

So there are quite a lot of dangers when working with motors. But we can try and put safeguards in place to try and prevent these from happening. 

First of all, we should always keep an eye on the robot, this will allow us to notice if something dangerous may be occuring. Say a robot accelerates too fast, we can notice this and hit the disabled button on the DriverStation. So always keep an eye on the robot and an hand on the DriverStation's disabled button (or the enter key). This goes doubly for any autonomous code, as it won't stop unless we forcibly stop the robot, which is why it is essential for control loops.

We can also apply motion limits on the motor. This can be done in software, by querying a sensor value and using it to limit the motion of the system. For example, use an encoder to limit the angle of an arm, or the height of an elevator. For this to work though, we need to have conditions in place around any code that operates the system, usually in commands or subsystem methods. Limit switches and especially hardware limit switches are good for this too. Hardware limit switches are connected directly to the motor controller which applies the limit without intervention of our code, which is good as there is less chance for mistakes. 

Smart motor controllers can also be configured with a current limit. This limits the amount of current passing to the motor and thus limiting the chance of overheating.

## Further Reading

- [FRC Docs - Control System Basics](https://docs.wpilib.org/en/stable/docs/software/advanced-controls/introduction/control-system-basics.html)
- [FRC Docs - Intro to PID](https://docs.wpilib.org/en/stable/docs/software/advanced-controls/introduction/introduction-to-pid.html#introduction-to-pid)
- [FRC Docs - PIDController](https://docs.wpilib.org/en/stable/docs/software/advanced-controls/controllers/pidcontroller.html)
- [FRC Docs - Common Control Loop Tuning Problems](https://docs.wpilib.org/en/stable/docs/software/advanced-controls/introduction/common-control-issues.html)
- [FRC Docs - DC Motor FeedForward](https://docs.wpilib.org/en/stable/docs/software/advanced-controls/introduction/introduction-to-feedforward.html)
- [Omega Engineering - PID](https://www.wevolver.com/article/pid-loops-a-comprehensive-guide-to-understanding-and-implementation)
- [I Explained](https://medium.com/luos/pid-the-i-as-in-integral-4390c71db12e)
- [D Explained(https://medium.com/luos/pid-d-as-in-derivative-6a022a82a62e)

It is also highly recommended to try out the tuning simulations in the FRC docs:
- [Tutorial](https://docs.wpilib.org/en/stable/docs/software/advanced-controls/introduction/tutorial-intro.html)
- [Flywheel Velocity](https://docs.wpilib.org/en/stable/docs/software/advanced-controls/introduction/tuning-flywheel.html)
- [Turrent Position](https://docs.wpilib.org/en/stable/docs/software/advanced-controls/introduction/tuning-turret.html)
- [Vertical Arm Position](https://docs.wpilib.org/en/stable/docs/software/advanced-controls/introduction/tuning-vertical-arm.html)
