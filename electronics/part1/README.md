In 24.4.2024 We met for the first part of the Electronics Workshop. This part is mostly theoratical, with some practical application of building a control system.

Presentations used:
- [Tom's Slideshow](https://docs.google.com/presentation/d/1-kQRPxuOjtqOZDmWHgj9rgo71iQC9VG4_2SRn7aQ0mo/edit?usp=sharing) - until Slide 66
- [Agam's Slideshow](https://docs.google.com/presentation/d/1dC1GV0fGQC_a8P3s0F6tjUUg_HeyRWFK/edit?usp=sharing&ouid=103040962347246613558&rtpof=true&sd=true)

Electronics plays quite a significant part in our robot, as any physical actions we do depends on one component or another. In code, outputs to the robot are transmited and executed via some electronics. 

In this part of the workshop we will look into the basics of electricity, the structure and devices that make up the Control System and the theory of communication via electronics.

## Electricity

Although not understood at first, electricity is, at its core, electrons. These subatomic particles are energy, and can be used to provide power to components. Unlike certain other particles, electrons can move through different materials, which means we can make them do things. Materials which allow electrons to move around freely through them are called conductive materials.

In nature, balance is everything. One commonly known fact, seen in many movies, is that in space, if one opens an airlock that holds an atmosphere on one side and vacuum on the other, the air will rush out into the vacuum. This is because the air is compressed densely on one side, but on the other there is no air, and as such, things are unbalanced. To balance things out, the air rushes out into the vacuum. 
The same is true for other particles. Say we have material that is positively charged, meaning its atoms lack electrons. and a material that is negatively charged, meaning its atoms have too many electrons, we create an imbalance. If we connect the two materials with a conductive material, the electrons will rush from the negative to the positive materials, seeking to balance the materials. They will stop when the material's charge is balanced. 

![electrons in coductor](resources/electrons-in-coductor.png)

Batteries and other power sources do exactly that. You may notice that they are marked with “plus” and “minus” signs, corresponding to the positive and negative ends of the power source. As long as there is a charge imbalance, with a route between the positively and negatively charged materials and the connecting material allows electrons to flow through it, we will have a flow of electricity.

With the ability to make electrons move around, we can force them to do things. If we place a lamp connected to the conductive material between “plus” and “minus” of the battery, the electrons will be forced to pass through said lamp. The lamp can use the electrons passing through as sources of energy to create light. 

![basic circuit](resources/circuit-basic.png)

These are the basics of electronic circuits. We can create many different circuits, and force electrons to do much: calculations, information storage, heat, light and so much more. All built around the principles we just discussed. Of course there is much more to electricity, but these are the basics.

## Electronic Circuits

An electronic circuit is made up of a power source, devices and conductive wires connecting them all. One can describe the electricity in the circuit with:
- Voltage: the driving force of electricity. Higher voltage = more electricity flow. Voltage measures the charge difference between the positive and negative parts of the power source. It is measured in volts. 1 Volt = 1 Joule / 1 Coulomb.
- Current: the speed of the flow of electrons in the circuit. It is measured in Ampere. 1 Ampere = 1 Coulomb / 1 Second
- Resistance: the tendency of the material to constrict the flow of electrons. Higher resistance reduces the flow of electricity. Measured in Ohms. 1 Ohm = resistance in a circuit that has 1 Volt potential and 1 Ampere current.


Ohm's law describes the relationship of these measurements: Resistance = Voltage / Current.

There are several characteristics the define the behaviour of electricity in a circuit that we should know:

![circuit split](resources/circuit-split-noresistance.png)

Electricity will prefer the path of least resistance. Meaning, if there are several paths to take from positive to negative, current will mostly flow through the path where the resistance is lowest. Since this path will have less resistance, the current through it will be higher, compared to if it had to take the path with the lamp in the way.

![circuit split with resistance](resources/circuit-split-resistance.png)

In this example, we have two paths with lamps on each path. If the lamps are the same, and the resistance is the same, the current will actually split between the paths equally. If one lamp has higher resistance, the current will split not equally, with a higher current going through the path with least resistance.

## FRC Control System

The control system is the set of components which are used to control and operate the robot systems. These include a few must-have components, and other components which connect to them and differ between specific robots. All the components are wired together for communication with each other and power supply.

![Control System - REV](resources/frc-control-system-layout-rev.png)

### Power Distribution

The power distribution component connects to the robot battery and supplies power to all other components of the robot. It is a must-have component, but it comes in several variants. 

The PDH (Power Distribution Hub) is the REV-made version and is newer, while PDP (Power Distribution Panel) is the CTRE-made version. Both perform the same function, but they do have some differences, like the cable terminals on the PDH being easier to work with.

![PDH](resources/power-distribution-hub.png)

The PDH is made up of two dozen power terminals for different components with different power supply:
- 20 High Current (40A) terminals for motors
- 4 Lower Current (20A) terminals for other components

This allows connecting and powering many different items with it. Which is necessary, as it is responsible for providing power to everything on the robot. It is similar to the breakout board of a home, controlling and stabilising the power supply to all outlets. Each terminal is protected by a single fuse and operates separately from all others, so if one terminal does not work, the others will. The fuses help protect connected components from burning due to a high current. 

The power itself is provided by a standard FRC battery connected via a special terminal to the board. This power is then routed to all other terminals.

The board also has a CANBus connection to allow the robot to communicate with the board, query sensors and check for problems.

### The Brain

The so-called brain of the robot, which provides processing and control over component functionality is the RoboRIO - a specially made Single Board Computer from National Instruments.

![RoboRIO](resources/roborio.png)

It may be small, but it is a computer nonetheless. It contains most components one might find in a normal PC (like a central processor, volatile and non volatile memory and such) but with a few extra parts for its specific purpose. It is not exceptionally powerful either, but it is capable enough to do its intended job.

Being a computer, it runs a variant of a Real Time Linux operating system and as such, provides similar capabilities seen in most operating systems, making it able to run more complex programs (like the Java Virtual Machine).

It has a set of I/O ports (easily spotted on the image) that enable it to electronically communicate with different robot components (like motor controllers or sensors). GPIO, Analog Input, I2C and CANBus are some of the ports featured on it, with associated hardware to allow operating them. For example, it can use its PWM ports to control rudimentary motor controllers.

The RoboRIO is powered from the power distribution board, from a 12V 10A port.
It also has the CANBus connected with the PDP/PDH to communicate with it.  The same goes with other main components.

### DC Motors

Motors are our primary motion generators for the robot. They take in electrical power and create a rotational motion. This motion can then be mechanically converted to do many different things.

There are many different motos that we can use. Each has different characteristics and performance. So choose your motos based on what they are used for.

DC Motors comes in two forms: brushed and brushless. Brushed motors are easy to operate, but have shorter life span, lower torque and speed. Brushless motors are harder to control (generally requiring a more complex algorithm and a hall effect sensor), but they generate more torque and speed.

Motors need electric power to work and thus are connected to the PDH/PDP for 12V 20A-40A (depending on the motor). However, because we need to control them, they are not connected directly to PDP/PDH, but rather via a motor controller. This controller allows the roboRIO to control the power and direction of the motor rotation.

![NEO V1.1](resources/neo1_1.png)

### Motor Controllers

Motor controllers are speciality devices intended for the control of electric motors. They can be very basic - only controlling the power and direction of the motor; or very sophisticated - capable of running special control algorithms for smart motion. Each motor controller is capable of controlling a single motor only.

Generally, in FRC, one can deduce the capability of the motor controller by its communication interface: basic ones use a PWM input, only capable of transferring power and direction requests. The complex ones use CANBus, which can transfer any kind or amount of data wanted. PWM controllers can be connected directly to the PWM ports of the RoboRIO, while CANBus ones can be daisy-chained to the other CANBus components.

The type or amount of controllers is selected depending on the specific robot, as they should match the requirements of the robot itself. Some robots have dozens of motors and as such need a matching amount of motor controllers.

![TalonSRX](resources/talonsrx.jpg)

Each motor controller has 2 power inputs (GND, V+) and 2 power outputs (M-, M+) for brushed motors or 3 power outputs (A, B, C) for brushless. The inputs are connected to a PDH/PDP high power port (12V 40A/30A dependent on motor). The outputs connect to the motor.

Most smart controllers also have a port for connecting sensors. Generally this is optional. However, for brushless motors, the encoder connection is a must. Connecting the sensors to the motor controller allows the motor controller to run algorithms with the sensors internally, instead of requiring us to run one on the roboRIO. This includes PID loops, and automatic limit switch stopping.

To control the motor controller (and thus the motor), the device has some data input/output ports. Most modern FRC controllers communicate over CANBus, so they have CANBus connectors. Older ones use a PWM input. Thanks to the flexibility of CANBus communication, we can also query sensor information from the motor controller.

## Electronic Communication

**TODO: ELECTRONICS COMMUNICATION**

When two devices exist in the same electronic circuit, how can they communicate? How does one device makes the other do something?
This question is essential for the operations of many devices. Take our motors and their controllers: how can the RoboRio tell them to operate in a specific manner? After all, in our code we are the ones to request the motor to rotate, and we specifiy both speed and direction.
The same goes for our sensors: how can we get information from them?

In this section, we will learn how this is done.

### Basics of Data Transfer

We've learned that in electronic circuits, there are 3 measures:
- Voltage
- Current
- Resistance 

Assume that one device controls the voltage over a wire, while a second device "reads" this voltage. In such a circuit, we then have the ability to say something by adjusting the voltage to certain levels. And this is relatively easy to do. By placing a variable resistor on the wire we can change the resistance and thus also the voltage. 
This is the base to Voltage-Driven control, where the voltage level is encoded with specific data.
This play with the voltage level is simple to perform. What actually occurs is that we change the energy of the electrons and this can easily be read. And because electrons are fast, the "reader" will see the changes almost instantly. This works fine for short distances.