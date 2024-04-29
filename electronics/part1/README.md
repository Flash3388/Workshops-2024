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

> [!IMPORTANT]
> The circuits described here are Direct Current (DC) circuits, which are the ones we see in FRC

An electronic circuit is made up of a power source, devices and conductive wires connecting them all. One can describe the electricity in the circuit with:
- Voltage: the driving force of electricity. Higher voltage = more electricity flow. Voltage measures the charge difference between the positive and negative parts of the power source. It is measured in volts. $1 \ Volt = {1 \ Joule \over 1 \ Coulomb}$. Where $1 \ Coulomb = 6.24 * 10^{18} \ electrons$
- Current: the speed of the flow of electrons in the circuit. It is measured in Ampere. $1 \ Ampere = 1 \ Coulomb \over 1 \ Second$
- Resistance: the tendency of the material to constrict the flow of electrons. Higher resistance reduces the flow of electricity. Measured in Ohms. 1 Ohm = resistance in a circuit that has 1 Volt potential and 1 Ampere current.


_Ohm's law_ describes the relationship of these measurements: $Resistance = {Voltage \over Current}$.

There are several characteristics the define the behaviour of electricity in a circuit that we should know:

Most components of a circuit will have a certain resistance to them. When electrons flow through them, they will resist the flow. This causes a slow down of the current, and a loss of energy. This loss of energy is generally converted into heat, heating up the component and wires. Because of this loss of energy, the voltage of circuit than drops (as voltage is the measure of energy potential). We can calculate the voltage lost using _Ohm's law_: $voltage \ lost = {Resistance \ of \ component \over Current \ flowing \ through \ component}$.

![circuit split](resources/circuit-split-noresistance.png)

Electricity will prefer the path of least resistance. Meaning, if there are several paths to take from positive to negative, current will mostly flow through the path where the resistance is lowest. Since this path will have less resistance, the current through it will be higher, compared to if it had to take the path with the lamp in the way.

![circuit split with resistance](resources/circuit-split-resistance.png)

In this example, we have two paths with lamps on each path. If the lamps are the same, and the resistance is the same, the current will actually split between the paths equally. If one lamp has higher resistance, the current will split not equally, with a higher current going through the path with least resistance.

### Conventional Current

The fact that electrons flow from the _negatively charged_ part of the power supply to the _positively charged_ part was described much after electricity was discovered. Originally it was though that the flow  was from _+_ to _-_, and since atoms weren't all that understood, it wasn't clear what exactly occurs. Because of this, electronic circuits were originally designed with the flow being _+_ to _-_, this is called _Conventional Current_ and is the standard for circuit design.

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

![Voltage Comm Basic Circuit](resources/voltage-based-comm-basic.png)

The above circuit illustrates sending digital data over a 3rd wire. As normal we have the "+" and "-" connections, which create a circuit over which voltage and current flow. You will see that they are marked as _VCC_ and _GND_. Generally, we can think of the as equivelent for "+" and "-" though this may not necessarily be the case. In reality the _VCC_ has the _higher voltage_ (say 5V) while the _GND_ has the _lower voltage_ (say 0V), but whether they are the "+" or "-" is circuit-dependent. Of course, when we connect them together, we get a flow of current as in any circuit. Now for the third wire, we can use it to create another circuit connecting the _VCC_ and _GND_ to each other and thus get voltage through it. On the controller device, we connect the _VCC_ to this wire via a switch. When the switch is open, the circuit is open and there is no voltage on the wire; when it is closed, the circuit is closed and the voltage will be the same as _VCC_. On the reading device, we connect this wire and the _GND_. This leads to a circuit from _VCC_ on the controlling device, through the third wire, and back to the _GND_. If we place a voltemeter on the reading device between the third wire and _GND_, we get a measure of voltage on the wire. So the device can now read this voltage and determine its value.
This is not exactly accurate though, and there are many different circuit configurations, but it is the basic concept of the data transfer.

### Electronic Noise

**TODO**

### Signal Types

Generally, there are two types of signals that can be used to transmit data: analog and digital. This is true for many mediums, not just electricity, but these are the only ones used in electronics. We can use different circuits to generate these signals as a way to transfer data from one device to another.

#### Analog

Analog signals are continous time-varying signals constrained within a range. These signals use a medium property (i.e. voltage) to encode data. So at any given moment, this property (say voltage) represents a certain quantity, and is thus _analogous_ to this quantity.  

![analog signal](https://github.com/Flash3388/Workshops-2024/assets/17641355/0bf5570c-6add-43b0-b036-d898c79b0d80)

For a voltage-driven system, we can, for example, use the voltage to represent a certain sensor info, like temperature. So the signal of voltage (over time) will represent the temperature at any given moment. 

One sensor may define a range of 0 degrees C to 20C. To encode this into the signal, we simply convert the temperature reading into voltage and set the voltage on the line to it. If we have a range of voltage between 0V and 5V, the conversion would be `voltage = temperature / 20 * 5`. This changes between sensors and devices.

To provide this control over voltage we can use a variable resistor. With such a resistor, we can change the amount of resistance and thus change the voltage over the line, because the voltage lost to resistance is `V = I*R`, so assuming a constant current, the change will `R` will increase or decrease the voltage drop, changing the voltage on the line (after the resistor).

![analog output circuit basic](https://github.com/Flash3388/Workshops-2024/assets/17641355/2b6c99e4-5ce0-4b29-8402-027df2f1079f)
![Tinkercad Analog Signal](https://github.com/Flash3388/Workshops-2024/assets/17641355/ff633e45-a01f-4f50-bdb6-6c6ca242b301)

At their core, electronic circuits are analog. As they used varying voltages to create different behaviours. Another analog data is audio information: AM (Aplitude Modulation) transmitions, for example, change the amplitude of the signal to encode specific audio information.

![audio signal](https://github.com/Flash3388/Workshops-2024/assets/17641355/e74ff14a-caaf-4226-afcc-b11ef3fa7b2c)

Although mathematically, 0-5 range has an infinite amount of numbers, realistically, it is a finite range. This is due to limitations in the ability to adjust the voltage and the ability to read the voltage. The _Arduino Uno_ analog pins, for instance, has a resolution of `4.9 mV`. So if the voltage changes by, say, `3 mV`, it will not be able to detect this. How many values we can play with is determined by the resolution of the components which make up the circuit.

Noise is another issue for analog signals, as a small noise causing a change of `10 mV` can change the reading we get from the voltage. Depending on the resolution we use and the strength of noise, this can dramatically affect our reading. In our earlier example of a temperature analog signal, a change of `0.25V` (${20 \over 5}$) will change our temperature reading by `1C`.

#### Digital

Digital signals are a sequence of discrete values. Binary signals, for example, can only be either `0` or `1`. The values are not continous to one another in the medium, so for a binary signal we switch between `0V` for a value of `0` and jump to `5V` for a value of `1`.

![digital binary signal](https://github.com/Flash3388/Workshops-2024/assets/17641355/0d02a0a1-948f-4dd3-9017-19507499c907)

For a voltage-drive system, we can use the voltage to represent `0` or `1` by changing the voltage between `LOW` (close to `0V`) and `HIGH` (close to `5V`). This is considered a _logical signal_ and is used to sent logic data (in binary). Of course this means we have only two value options at any given moment, so we can only report a _boolean_ at a moment, unlike analog signals which have a range of possible values.

![digital output circuit basic](resources/voltage-based-comm-basic.png)
![Tinkercad Digital Signal](https://github.com/Flash3388/Workshops-2024/assets/17641355/9f277f53-3895-4fa1-97af-3eff1f606680)

To control the voltage, we can use either a switch to close the circuit (for _HIGH_ voltage) and open it (for _LOW_ voltage), or we can use one _high-resistance_ resistor (for _LOW_ voltage) and _low-resistance_ resistor (for _HIGH_ voltage).

Processors and computers are considered digital systems, as they represent data internally with digital signals. This is evident by the use of binary numbers in processors.

Binary digital signals, are usually represent in a range of voltage. That is, instead of `1` being exactly `5V`, it is actually a range (like `2V - 5V`), and any voltage within this range will be considered `1`. Same for `0` values, being a range (like `0V - 2V`). `TTL` represents specs for such voltage levels:
- `2V` - `5V` is considered _HIGH_ voltage, i.e. `1`
- `0V` - `0.8V` is considered _LOW_ voltage, i.e. `0`
- The range in the middle (`0.8V` - `2V`) is ignored, keeping the last value (if was `0` and voltage is now `1V`, value stays as `0`).

There are other configurations used, not always with `5V` voltage level. `3.3V` is also popular.

Unlike analog signals, digital signals are generally less susceptible to noise, thanks to massive difference of voltage between each value. For _TTL_, we need a change of around `1.2V` to switch from _LOW_ to _HIGH_. 

#### Analog vs Digital

Analog and digital signals both have their advantages and disadvantages and are thus used in different circuits depending on what is wanted.

 Analog    | Digital (binary)
------------------------------------------|----------------------
Range of values. Dependent on voltage level and resolution | A set of discrete values. 0, 1 for binary
Susceptible to small noises | Requires a big noise to be affected
Circuit is a bit more complex to control and read, but still simple | Simple and easy circuit
Integrates well with circuitry, but problematic with digital components | Integrates well with analog and digital, but has limited use in analog circuits

As mentioned in the table, digital components (processors/computers) have a difficult time working with analog signals, as they do not understand them natively. For that, we have special conversion ciruits which converted between the two domains. _Analog-Digital Converters_ (ADC) convert analog signals to digital. _Digital-Analog Converters_ (DAC) convert digital signals to analog. These components are described by their resolution, which explains how sensitive is the circuit to read/sending different analog signals. 12 bit resolution indicate a range of values from 0 to $2^{12}$ (4096). This means that the smallest change the circuit can work with is `1.22 mV` (${5V \over 4096}$).

#### Pulse-Width Modulation (PWM)

_PWM_ is a type of digital signal which has special characteristics when used in an analog circuits. It is generated by changing the voltage to a _HIGH_ voltage for a certain amount of time (also called length), and then returning the voltage to _LOW_. This is called a _pulse_. By changing the length of the pulse, we can create a changing signal.

![PWM signal](https://github.com/Flash3388/Workshops-2024/assets/17641355/61cd0d7c-f29e-4528-8fe8-cf8b01c84813)

PWM signals are characterized by several properties:
- _Period_ defines a set amount of time. Inside this time frame, we can play with the length of the pulse. When the period ends, we switch to the next pulse.
- _High Time_ defines the amount of time in a period that the signal is of a _HIGH_ voltage.
- _Duty Cycle_ defines the precentage of the _Period_ of _High Time_ $Duty Cycle = {High Time \over Period}$. 
- _Frequency_ is defined as ${1 \over Period}$ and is measured in `Hz` (1 `Hz` = a period of 1 second).

![Varying Duty Cycles](https://github.com/Flash3388/Workshops-2024/assets/17641355/f9e76bb5-6fdb-408e-bab6-73d5f5bad2e4)

PWM signals have some interesting properties and advantages over analog signals. For example, they can be easily generated by digital devices, as they do not require a _DAC_, the voltage is either _HIGH_ or _LOW_. Normally a dedicated clock is used to automatically generate this signal. The clock will be configured for a specific _Frequency_ and can be told to create signals of varying _Duty Cycle_. 

One use can be to transfer values from 0->100 (represented by the _Duty Cycle_, as 0% to 100%). But actually PWM is noramlly used due to what effects it can cIf we reate in an analog circuit.

Consider motor controllers: When connected to the PDP, they receive 12V voltage. To control DC motors, they need to output a varying voltage depending on the wanted speed (6V for 50% speed). So how can they control this output? One way is using a variable resistor, which causes a voltage drop from 12V to the wanted voltage. However, this is not perfect, as resistance generates heat, and at currents of 40A (which isn't weird for a motor), the heat generated is quite high. Moreover, it causes a huge loss of energy because of this heat. So its not very efficent.

But what about PWM? We can connect the power supply from the PDP through a special switch. The output from the switch goes to the motor. When we supply _HIGH_ voltage through the PWM wire, the switch closes and pass voltage and current to the motor. When the voltage is _LOW_, the switch is open and nothing goes through. So what happens with a PWM duty cycle of 50%? The switch is closed half of the time, transferring 12V, and open the other half, transferring 0V. If the PWM frequency is high, this occurs quickly, turning the motor to full power and then back off. This leads to the voltage averaging at 6V and gives an end result of 50% velocity. Because there is little resistance, we don't loss much energy. This is the basis for PWM motor control.

![PWM Motor Controller - Basic](https://github.com/Flash3388/Workshops-2024/assets/17641355/97bd8303-65f2-49dd-9280-dfe0a3f5f8e2)

### Sending Multiple Bits

When connecting two digital components (say computers) we sometimes need to transmit a lot of information between them. For example, when uploading an image to a server. Images are big, they are generally measured in the Kilo-Byte (kB) range ($1 \ kB = 1000 Bytes = 8000 Bits$). How can we transmit such a huge quantity of information? 

#### Serial

Consider: one wire with a digital signal can, at any given moment, be either `0` or `1`. But what if we change the signal after a few moments to a different value? So we can actually send several values, one after another.

![multiple bits on wire](https://github.com/Flash3388/Workshops-2024/assets/17641355/a11ee4b3-5bd0-4e59-84fa-415b2687751b)

The graph above shows the state of voltage on a wire throughout time. Every 1 second we change the voltage to indicate a different value. The reader can read every one second to read each bit at a time. After several seconds we have a few bytes of information accumulated. Though, with this speed we would only read 60 bits per second, which is very slow. But computers are fast nowadays, so we can speed up the bit writing to something like 1 bit per 1 millisecond, or even faster.

This is what's called serial communication, where bits are written one by one to the wire (one after another = in series). This integrates quite well with computer memory, as we can simply send the bits as they are stored in memory to a second computer, which can then store it in its memory directly.

This method is quite popular for data transfer. Protocols like USB and PCI/e are all based on serial communications, and can reach speeds of 1 Giga-Bits per second (1 billion bits per second).

![Serial Communication](https://github.com/Flash3388/Workshops-2024/assets/17641355/414f443f-8623-434c-ae0d-1103a7793675)

#### Parallel

Instead of transferring bits 1 by 1 on a wire, parallel communication uses multiple wires, one to transfer each bit. So with 8 wires, we can transfer a full byte at once.

![Parallel Communication](https://github.com/Flash3388/Workshops-2024/assets/17641355/3407e4d0-fd7e-42bf-be61-a1eb800966b4)

### Communication Bus

#### I2C

#### SPI

#### CANBus
