package y2019.day09_sensor_boost

import y2019.computer.IntcodeComputer

class Boost {
}


fun main() {
    val computer = IntcodeComputer(BOOST_PROGRAM)

    while (! computer.isProgramFinished()) {
        computer.tick()
    }
}


private const val BOOST_PROGRAM = "1102,34463338,34463338,63,1007,63,34463338,63,1005,63,53,1101,0,3,1000,109,988,209,12,9,1000,209,6,209,3,203,0,1008,1000,1,63,1005,63,65,1008,1000,2,63,1005,63,904,1008,1000,0,63,1005,63,58,4,25,104,0,99,4,0,104,0,99,4,17,104,0,99,0,0,1101,29,0,1010,1102,1,1,1021,1101,0,36,1002,1101,573,0,1026,1101,0,33,1012,1102,1,25,1004,1102,1,38,1000,1102,31,1,1003,1102,23,1,1006,1102,777,1,1028,1102,20,1,1011,1101,0,566,1027,1101,0,27,1009,1101,26,0,1005,1101,0,0,1020,1102,1,37,1014,1101,32,0,1001,1101,0,24,1007,1101,0,35,1018,1101,30,0,1017,1101,0,22,1008,1102,460,1,1023,1101,0,768,1029,1102,1,487,1024,1102,1,34,1013,1102,1,28,1015,1101,0,39,1019,1101,478,0,1025,1101,0,463,1022,1101,21,0,1016,109,9,1208,0,30,63,1005,63,201,1001,64,1,64,1105,1,203,4,187,1002,64,2,64,109,3,1201,-8,0,63,1008,63,24,63,1005,63,227,1001,64,1,64,1106,0,229,4,209,1002,64,2,64,109,-1,2108,32,-8,63,1005,63,245,1106,0,251,4,235,1001,64,1,64,1002,64,2,64,109,-11,2101,0,2,63,1008,63,35,63,1005,63,275,1001,64,1,64,1105,1,277,4,257,1002,64,2,64,109,3,2101,0,-1,63,1008,63,36,63,1005,63,303,4,283,1001,64,1,64,1106,0,303,1002,64,2,64,109,16,21108,40,40,-6,1005,1013,325,4,309,1001,64,1,64,1106,0,325,1002,64,2,64,109,-4,21102,41,1,-4,1008,1011,41,63,1005,63,351,4,331,1001,64,1,64,1105,1,351,1002,64,2,64,109,-15,2102,1,4,63,1008,63,24,63,1005,63,375,1001,64,1,64,1106,0,377,4,357,1002,64,2,64,109,6,1201,-2,0,63,1008,63,25,63,1005,63,403,4,383,1001,64,1,64,1106,0,403,1002,64,2,64,109,8,2102,1,-6,63,1008,63,22,63,1005,63,425,4,409,1106,0,429,1001,64,1,64,1002,64,2,64,109,-1,2108,27,-4,63,1005,63,447,4,435,1106,0,451,1001,64,1,64,1002,64,2,64,109,8,2105,1,2,1105,1,469,4,457,1001,64,1,64,1002,64,2,64,109,5,2105,1,-2,4,475,1001,64,1,64,1106,0,487,1002,64,2,64,109,-33,1202,7,1,63,1008,63,37,63,1005,63,507,1105,1,513,4,493,1001,64,1,64,1002,64,2,64,109,2,2107,25,10,63,1005,63,535,4,519,1001,64,1,64,1106,0,535,1002,64,2,64,109,30,21107,42,41,-9,1005,1016,551,1106,0,557,4,541,1001,64,1,64,1002,64,2,64,109,2,2106,0,0,1001,64,1,64,1105,1,575,4,563,1002,64,2,64,109,-19,1202,-7,1,63,1008,63,32,63,1005,63,601,4,581,1001,64,1,64,1105,1,601,1002,64,2,64,109,-2,1207,-1,27,63,1005,63,619,4,607,1106,0,623,1001,64,1,64,1002,64,2,64,109,2,21101,43,0,6,1008,1014,45,63,1005,63,647,1001,64,1,64,1106,0,649,4,629,1002,64,2,64,109,17,1205,-4,663,4,655,1106,0,667,1001,64,1,64,1002,64,2,64,109,4,1205,-9,683,1001,64,1,64,1106,0,685,4,673,1002,64,2,64,109,-17,21101,44,0,-2,1008,1010,44,63,1005,63,711,4,691,1001,64,1,64,1105,1,711,1002,64,2,64,109,1,21102,45,1,3,1008,1016,42,63,1005,63,735,1001,64,1,64,1105,1,737,4,717,1002,64,2,64,109,-9,1207,1,25,63,1005,63,753,1105,1,759,4,743,1001,64,1,64,1002,64,2,64,109,23,2106,0,1,4,765,1001,64,1,64,1106,0,777,1002,64,2,64,109,-3,1206,-3,789,1105,1,795,4,783,1001,64,1,64,1002,64,2,64,109,-13,2107,25,-4,63,1005,63,815,1001,64,1,64,1105,1,817,4,801,1002,64,2,64,109,-9,21108,46,44,10,1005,1012,833,1105,1,839,4,823,1001,64,1,64,1002,64,2,64,109,-4,1208,10,22,63,1005,63,857,4,845,1105,1,861,1001,64,1,64,1002,64,2,64,109,28,1206,-6,879,4,867,1001,64,1,64,1105,1,879,1002,64,2,64,109,-4,21107,47,48,-3,1005,1019,897,4,885,1105,1,901,1001,64,1,64,4,64,99,21102,27,1,1,21101,915,0,0,1106,0,922,21201,1,14615,1,204,1,99,109,3,1207,-2,3,63,1005,63,964,21201,-2,-1,1,21102,1,942,0,1106,0,922,22101,0,1,-1,21201,-2,-3,1,21101,957,0,0,1105,1,922,22201,1,-1,-2,1106,0,968,22101,0,-2,-2,109,-3,2105,1,0"


/*
--- Day 9: Sensor Boost ---
You've just said goodbye to the rebooted rover and left Mars when you receive a faint distress signal coming from the asteroid belt.
It must be the Ceres monitoring station!

In order to lock on to the signal, you'll need to boost your sensors. The Elves send up the latest BOOST program - Basic Operation Of System Test.

While BOOST (your puzzle input) is capable of boosting your sensors, for tenuous safety reasons,
it refuses to do so until the computer it runs on passes some checks to demonstrate it is a complete Intcode computer.

Your existing Intcode computer is missing one key feature: it needs support for parameters in relative mode.

Parameters in mode 2, relative mode, behave very similarly to parameters in position mode: the parameter is interpreted as a position.
Like position mode, parameters in relative mode can be read from or written to.

The important difference is that relative mode parameters don't count from address 0.
Instead, they count from a value called the relative base. The relative base starts at 0.

The address a relative mode parameter refers to is itself plus the current relative base.
When the relative base is 0, relative mode parameters and position mode parameters with the same value refer to the same address.

For example, given a relative base of 50, a relative mode parameter of -7 refers to memory address 50 + -7 = 43.

The relative base is modified with the relative base offset instruction:

Opcode 9 adjusts the relative base by the value of its only parameter.
The relative base increases (or decreases, if the value is negative) by the value of the parameter.

For example, if the relative base is 2000, then after the instruction 109,19, the relative base would be 2019.
If the next instruction were 204,-34, then the value at address 1985 would be output.


Your Intcode computer will also need a few other capabilities:

The computer's available memory should be much larger than the initial program. Memory beyond the initial program starts with the value 0 and can be read or written like any other memory. (It is invalid to try to access memory at a negative address, though.)
The computer should have support for large numbers. Some instructions near the beginning of the BOOST program will verify this capability.
Here are some example programs that use these features:

109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99 takes no input and produces a copy of itself as output.
1102,34915192,34915192,7,4,7,99,0 should output a 16-digit number.
104,1125899906842624,99 should output the large number in the middle.
The BOOST program will ask for a single input; run it in test mode by providing it the value 1. It will perform a series of checks on each opcode, output any opcodes (and the associated parameter modes) that seem to be functioning incorrectly, and finally output a BOOST keycode.

Once your Intcode computer is fully functional, the BOOST program should report no malfunctioning opcodes when run in test mode; it should only output a single value, the BOOST keycode. What BOOST keycode does it produce?

To begin, get your puzzle input.

Answer:


You can also [Share] this puzzle.


 */