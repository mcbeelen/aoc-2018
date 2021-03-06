package y2018.day13_cart_madness



const val CART_MADNESS_INPUT = """                     /---------------\
                     |       /-------+---------------------\
     /--------\      |       |       |   /-----------------+--------------------------------\
     |        |      |       |       |   |                 |      /-------------------------+------------------------------------------------\
  /--+--------+------+-------+-------+---+----\            |      |           /-------------+------------\                                   |
  |  |      /-+------+-------+\      |   |    |            |  /---+-----------+-------------+------------+-----------------------------------+\
  |  |      | |      |       ||      |   |    |            |  |   |           |             |            |                                   ||
  |  |/-----+-+------+-------++------+---+----+------------+--+---+-----------+----\        |            |    /--------------------\         ||
  |  ||     | |      |       ||      |   |    |            |  |   |/----------+----+----\   |            |  /-+--------------------+\        ||
  |  ||     | |      |       ||      |   |    |            |  |   ||          |    |  /-+---+------------+--+-+----------------\   ||        ||
  |  ||    /+-+------+-------++------+---+----+------------+--+---++----------+----+--+-+---+\           |  | |                |   ||        ||
  |  ||    || |      |       ||      |   |    |          /-+--+---++----------+----+--+-+---++-----------+--+-+------------\   |   ||        ||
  |  \+----++-/      |       ||      |   |    |  /-------+-+--+---++----------+----+--+-+---++-----------+--+-+---------\  |   |   ||        ||
  |   |    ||        |       ||      |   |/---+--+-------+-+--+---++----------+----+--+-+---++---\    /--+--+-+---------+--+---+---++--------++---\
  |   |/---++--------+-------++------+---++---+--+-------+-+--+---++----\ /---+----+--+-+---++---+----+--+--+-+---------+--+--\|   ||        ||   |
  |   ||   ||        |  /----++----\ | /-++---+--+-------+-+--+---++----+-+---+----+--+-+---++---+----+--+--+-+---------+--+--++--\||        ||   |
 /+---++---++\       |  |    ||    |/+-+-++---+--+-------+-+--+---++---\| |   |    |  | |   ||   |    |  |  | |         |  |  || /+++-----\  ||   |
 ||   ||/--+++-------+--+----++----+++-+-++---+--+-------+-+--+---++---++-+---+----+--+-+---++---+-\  |  |  | \---------+--+--++-++/|     |  ||   |
 ||   |||  |||       |  | /--++----+++-+-++---+--+-------+-+--+---++---++-+---+----+--+-+---++---+-+--+--+--+--\        |  |  || || |     |  ||   |
 ||   |||  |||       |  | |  ||    ||| | ||   |  |       | |  |   ||   || |   |    |  | |   ||   | |  |  |  |  |        |  |  || || |     |  ||   |
 ||   |||  |||       |/-+-+--++----+++-+-++--\|  |/------+-+--+---++---++-+---+----+--+-+---++---+-+--+--+--+--+--------+--+--++-++-+-----+<-++---+\
 ||   |||  |||       || | |  ||    ||| | ||  ||  ||      | |  |   ||   || |   |    |  | |   ||   |/+--+--+--+--+--\     |  |  || || |     |  ||   ||
 ||   |||  \++-------++-+-+--++----+++-+-++--++--++------+-+--+---++---++-+---+----+--+-+---+/   |||  |  |  |  |  |     |  |  || || |     |/-++---++-\
 ||   |||   ||       || | |  ||    ||| | ||  ||  ||      | |  |   ||   || |   |    |  | |   |    |||  |  |  |  |  |     |  |  || || |     || ||   || |
 ||   |||   ||   /---++-+-+--++----+++-+-++--++--++------+-+--+---++---++-+---+----+--+-+---+----+++--+\ |  |  |  |     |  |  || || |     || ||   || |
 \+---+++---+/   |   || | |  ||    ||| | |\--++--++------+-+--+---++---++-+---+----+--+-+---+----/||  || |  |  |  |     |  |  || || |     || ||   || |
  \---+++---+----+---++-+-+--++----+++-+-+---+/  ||      | |  |   ^|  /++-+---+----+--+-+---+---\ ||  || |  |  |  |     |  |  || || |     || ||   || |
      |||   |    |   || | |  ||    ||| | |   |   ||      | |  |   ||  ||| |   |    |  | |   |   | || /++-+--+--+--+-----+--+--++-++-+-----++-++--\|| |
      |||   |    |   || | |  ||   /+++-+-+---+---++------+-+--+--\||  ||| |   |    | /+-+-->+---+-++-+++-+--+--+--+-----+--+--++-++-+-----++-++-\||| |
      |||   |    |   || | |  ||   |||| | |   |   ||      | |  |  |||  ||| |   |    | || |   |   | || ||| |  |  |  |     |  |  || || |     || || |||| |
      |||   |    |   || | |  ||   |||| | |   |   \+------+-+--+--+++--+++-+---+----+-++-+---+---+-++-+++-+--+--+--+-----/  |  || || |     || || |||| |
      |||   |    |   || | |  ||   |||| | |/--+----+------+-+--+--+++--+++-+---+----+-++-+---+---+-++-+++-+--+--+--+-----\  |  || || |     || || |||| |
      ||| /-+----+---++-+-+--++---++++-+-++--+----+------+-+--+--+++--+++-+---+----+-++-+--\|   | || ||| |  |  |  |     |  |  || || |     || || |||| |
/-----+++-+-+----+---++-+-+--++---++++-+-++--+----+------+-+--+--+++--+++-+---+----+-++-+--++--\| || ||| |  |  |  |     |  |  || || |     || || |||| |
|     ||| | |    |   || | |  ||   |||| | ||  |    |      | |  |  |||  ||| |   |    | || |  ||  || || ||| |  |  |  |     |  |  || \+-+-----/| || |||| |
|     ||| | |    |   || | |  ||   |||| | ||  |    |      | |  |  |||  ||| |   |    | || |  ||  || || ||| |  |  |  |     |  |  ||  | |      | || |||| |
|     ||| | |    |/--++-+-+--++---++++-+-++-\|   /+------+-+--+--+++--+++-+---+----+-++-+--++--++-++-+++-+--+--+--+-----+--+--++--+-+----\ | || |||| |
|     |\+-+-+----++--++-+-+--++---++++-+-++-++---++------+-+--+--+++--++/ |   |    | || |  ||  || || ||| |  |  |  |     |  |  ||  | |    | | || |||| |
|  /--+-+-+-+----++--++-+-+--++---++++-+-++-++---++\     | |  | /+++--++--+-\ |    | || |  ||  || || ||| |  |  |  |     |  |  ||  | |    | | || |||| |
|  |  | | | |    ||  |\-+-+--++---++++-+-++-+/   |||  /--+-+--+-++++--++--+-+-+----+-++-+--++--++-++-+++-+--+--+--+-----+--+--++\ | |    | | || |||| |
|  |  | | | |    ||  |  | |  ||   |||| |/++-+----+++--+--+-+--+-++++--++--+-+-+----+-++-+--++--++-++-+++-+--+-\|  |     |  |  ||| | |    | | || |||| |
|  |  | | | |    ||  |  | |  ||   |||| |||| |    |||  |  | |  | ||||  ||  | | |    | || |  ||  || || ||| |  | ||  |     |  |  ||| | |    | | || |||| |
|  |  | | | |    ||  |  | |  ||   |||| |||| |    ||| /+--+-+--+-++++-\||  | | |    | || |  ||  || || ||| |  | ||  |     |  |  ||| | |    | | || |||| |
|  |  | | | |    ||  |  | |  ||   |||| |||| |    ||| ||  | |  | ||\+-+++--+-+-+----+-++-+--++--++-++-+++-+--+-++--+-----+--+--+++-+-+----+-+-/| |||| |
|  |  | | | |    ||  |  | |  ||  /++++-++++-+----+++-++--+-+--+-++-+-+++--+-+-+----+-++-+--++--++-++-+++-+--+-++--+\    |  |  ||| | |    | |  | |||| |
|  |  | | \-+----++--+--+-+--++--+++++-++++-+----+++-++--+-+--+-++-+-+++--+-+-+----+-++-+--/|  || \+-+++-+--+-++--/|    |  |  ||| | |    | |  | |||| |
|  |  | |   |    ||  |  \-+--++--++/|| |||| |/---+++-++--+-+--+-++-+-+++--+-+-+----+-++-+---+--++--+-+++-+--+-++---+----+--+--+++-+-+----+-+--+\|||| |
|  |  | |   |    ||  |    |  ||  || || |||| ||   ||| ||  | |  | || | |||  | |/+----+-++-+---+--++--+-+++-+--+-++---+---\|  |  ||| | |    | |  |||||| |
|  |  | |   |    ||  |    |/-++--++-++-++++-++---+++-++--+-+--+-++-+-+++--+-+++----+-++-+---+--++\ | ||| |  | ||   |   ||  |  ||| | |    | |  |||||| |
|  \--+-+---+----++--+----++-++--++-++-++++-++---++/ ||  |/+--+-++-+-+++--+-+++----+-++-+---+--+++-+-+++-+--+-++---+---++--+--+++-+-+\   | |  |||||| |
|     | |   |    ||/-+----++-++--++-++-++++\||   ||  ||  |||  | || | |||  | |||    | || | /-+--+++-+-+++-+>-+-++---+---++--+--+++-+-++---+-+--++++++\|
|     | |   |    ||| |    || ||  || || |||||||   ||  ||  |||  | || | |||  | |||/---+-++-+-+-+--+++-+-+++-+-\| ||   |   ||  |  ||| | ||   | |  ||||||||
|     | |   |    ||| |    || ||  || || |||||||   ||  ||  |||  | || | |||  | ||||   | || | | |  ||| | ||| | || ||   |   ||  |  ||| | ||   | |  ||||||||
|     | |   |    ||| |   /++-++--++-++-+++++++---++--++--+++--+-++-+-+++--+-++++---+-++-+-+-+--+++-+-+++-+-++-++---+---++-\|  ||| | ||   | |  ||||||||
|     |/+---+----+++-+---+++-++\ || || |||||||   ||  ||  |||  | || | |||  | ||\+---+-++-+-+-+--+++-+-+++-/ || ||   |   || ||  ||| | ||   | |  ||||||||
|     \++---+----+++-+---+++-+++-++-++-+++++++---++--++--+++--+-++-+-+++--+-++-+---/ || | | |  ||| | |||   || ||   |   || ||  ||| | ||   | |  ||||||||
|      ||   |    ||| |   ||| ||| \+-++-+++++++---++--++--+++--+-++-+-+++--+-++-+-----++-+-+-+--+++-+-+++---++-++---/   || ||  ||| | ||   | |  ||||||||
|/-----++---+----+++-+---+++-+++--+-++-+++++++---++--++--+++--+-++-+-+++--+-++-+-----++\| | |  ||| | |||   || ||       || ||/-+++-+-++---+\|  ||||||||
||     ||   |    ||| |   ||| |||  | || |||||||   ||  ||  |||/-+-++-+-+++--+-++-+-----++++-+-+--+++-+-+++---++-++--\   /++-+++-+++-+-++-\ |||  ||||||||
||     ||   |    ||| |   ||| ||| /+-++-+++++++---++--++--++++-+-++-+-+++--+-++-+-----++++-+-+--+++-+-+++---++-++--+---+++-+++-+++-+-++-+-+++\ ||||||||
||     ||   |    ||| |   ||| ||| || || |||||||   ||  ||  |||| | || | |||  | || |     |||| | |  ||| | ||v   |\-++--+---+++-+++-+++-+-/| | |||| ||||||||
||     ||   |    ||| |   ||| ||| || || |||||||   ||  |\--++++-+-++-+-+++--+-++-+-----++++-+-+--+++-+-+++---+--++--+---+++-+++-++/ |  | | |||| ||||||||
||     ||/--+----+++-+---+++-+++-++-++-+++++++---++--+---++++\\-++-+-+++--+-++-+-----++++-+-+--+++-+-+++---+--++--+---+++-+++-++--+--+-+-++++-/|||||||
||     |||  |    \++-+---+++-+++-++-++-+++++++---++--+---+++++--++-+-+++--+-++-+-----++++-+-+--+++-+-++/  /+--++--+---+++-+++-++--+--+-+-++++-\|||||||
||     |||  |     || |   ||| ||| |\-++-+++++++---++--+---+++++--+/ | |||  | || |     ||||/+-+--+++-+-++---++\ ||  |   ||| ||| ||  |  | | |||| ||||||||
||     |||  |     || | /-+++-+++-+--++-+++++++\  ||  |   |||||  |  | |||  | || |   /-++++++-+--+++-+-++---+++-++--+---+++-+++-++--+--+\| |||| ||||||||
||     |||/-+-----++-+-+-+++-+++-+--++-++++++++--++--+---+++++--+--+\|||  | || |   | |||||| |  ||| | |\---+++-++--+---+++-+++-++--+--+++-++++-++++/|||
||     |||| |     || | | ||| |^| |  || ||||||||  ||  |   |||||  |  |||||  | || |   | |\++++-+--+++-+-+----+++-++--+---+++-+++-+/  |  ||| |||| |||| |||
||     |||| |     || | | ||| ||| |  || ||||||||  || /+---+++++--+--+++++--+-++\\---+-+-++++-+--+++-+-+----+/| ||  |   ||| ||| |   |  ||| |||| |||| |||
||     |||| |     || | | ||| ||| |  \+-++++++++--++-++---+++++--+--++++/  | |\+----+-+-++++-+--+++-+-+----+-+-++--+---+/| ||| |   |  ||| |||| |||| |||
||     \+++-+-----++-+-+-+++-++/ |   | ||||||||  || ||   |||||  |  ||||   | | |    | | |||| |  ||| | |    | | ||  |   | | ||| |   |  ||| |||| |||| |||
||      ||| |     |\-+-+-+++-++--+---+-++++/|||  || ||   |||||  |  ||||   \-+-+----+-+-++++-+--+++-+-+----+-+-++--+---+-+-+++-/   |  ||| |||| |||| |||
||   /--+++-+-----+--+-+-+++-++--+---+-++++-+++\ || ||   |||||  |  ||||/----+-+----+-+-++++-+--+++-+\|    | | ||  |   | | |||     |  ||| |||| |||| |||
|\---+--+++-+-----+--+-+-+++-++--+---+-++++-++++-++-++---+++++--+--+++++----+-+----+-+-/||| |  ||| |||    | | ||  |   | | |||     |  ||| |||| |||| |||
|    |  ||| |     |  \-+-+++-++--+---/ |||| |||| || ||   |||||  |  |||||    | |    | \--+++-+--+++-+++----+-+-++--+---+-+-+++-----+--+++-++++-++/| |||
|    |  ||| |     |    | ||| ||  |     |||| |||| || ||   |||||  |  |||||    | |    |    ||| |  |||/+++----+-+-++--+---+\| |||     |  ||| |||| || | |||
|    |  ||| |     |    | ||| ||  |     ||\+-++++-++-++---+++++--+--+++++----+-+----+----+++-/  |||||||    | | ||  |   ||| |||     |  ||| ||v| || | |||
|    |  ||| |     |    | ||| ||  |     || |/++++-++-++---+++++--+--+++++----+-+->--+----+++----+++++++----+-+-++--+---+++-+++-----+--+++-++++-++\| |||
|    |  ||| |     |    | ||| ||  |     || |||\++-++-++---+++++--+--+++++----+-+----+----+++----+++++++----+-+-++--+---+++-+++-----+--+++-++++-+/|| |||
|    |  ||| |     |    | ||| ||  |     || ||| || || ||   |||||  |  \++++----+-+----+----/|| /--+++++++----+-+-++--+---+++-+++-----+\ ||| |||| | || |||
|    |  |||/+-----+----+-+++-++--+--\  || ||| || \+-++---+++++--+---++++----+-+----+-----++-+--+++++++----+-+-++--+---+++-+++-----++-+++-/||| | || |||
|    |  |||||     |    | ||| || /+--+--++-+++-++--+-++---+++++--+---++++----+-+----+-----++\|  |||||||    | | ||  |   ||| |||    /++-+++--+++-+-++\|||
|    |  |\+++-----+----+-+++-++-++--+--++-+++-++--+-++---++++/  |   ||||    | |    |     ||||  |||||||    | | ||  |   ||| |||    ||| |||  ||| | ||||||
|    |  | |||     |    | ||| \+-++--+--++-+++-++--+-++---++/|   |   ||||    | |    |     ||||  |||||||    | | |v  |   ||| |||    ||| |||  ||| | |||v||
|    |  | |||     |    | |||  | ||  |  || ||| ||  | \+---++-+---+---++++----+-/    |     ||||  |||||||    |/+-++--+---+++-+++----+++\|||  ||| | ||||||
|    |  | |||     |   /+-+++--+-++--+--++-+++-++--+--+---++-+---+---++++----+------+-----++++\ |||\+++----+++-++--+---+/| |||    |||||||  ||| | ||||||
|    |  | |||     |   || |||  | ||  |  || ||| ||  |  |   || |   |   ||||    |      |     ||||| ||| ||| /--+++-++--+---+\| |||    |||||||  ||| | ||||||
|    | /+-+++-----+\  || |||  | ||  |  || ||| ||  |  |   || |   \---++++----/      \-----+++++-+++-+++-+--+++-++--+---+++-+++----+++++/|  ||| | ||||||
|    | || |||     ||  || |||  | ||  |  || ||| ||  |  | /-++-+-------++++-----------------+++++-+++-+++-+-\||| ||  |   ||| |||    ||||| |  ||| | ||||||
|    |/++-+++-----++--++-+++--+-++--+--++-+++-++--+--+-+-++-+----\  ||||                 ||||| ||| ||| | |||| ||  |  /+++-+++----+++++-+--+++\| ||||||
|    |||| ||| /---++--++-+++--+-++--+--++-+++-++--+--+-+-++-+----+--++++-----------------+++++-+++-+++-+\|||| ||  |  |||| |||    ||||| |  ||||| ||||||
|    |||| |||/+---++--++-+++--+-++--+--++-+++-++--+--+-+-++-+----+--++++-----------------+++++-+++-+++-++++++\||  |  |||| |||    ||||| |  ||||| ||||||
|    |||| |||||   ||  || |||  | ||  |  || ||| ||  |  |/+-++-+----+--++++---\             ||||| ||| ||| |||||||||  |  |||| |||    ||||| |  ||||| ||||||
|    |||| |||||   ||  |\-+++--+-++>-+--++-+++-/|  |  ||| || \----+--++++---+-------------+++++-+++-+++-+++++++++--/  |||| |||    ||||| |  ||||| ||||||
|/---++++-+++++---++--+--+++--+-++--+--++-+++--+--+--+++-++------+--++++---+----------\  ||||| ||| ||| |||||||||     |||| |||    ||||| |  ||||| ||||||
||   |||| |||||   ||  |/-+++--+-++--+--++-+++--+--+--+++-++------+--++++---+\         |  ||||| ||| ||| |||||||||     |||| |||    ||||| |  ||||| ||||||
||   |||| |||||   ||  || |||  | ||  |  || |||  |  |  ||| ||      |  ||||   ||  /------+--+++++\||| ||| |||||||||     |\++-+++----+++++-/  ||||| ||||||
||   |||| \++++---++--++-+++--+-++--+--++-+++--+--+--+++-++------+--/|||   ||  |      |  ||||||||| ||| |||||||||     | || |||    |||||    ||||| ||||||
||   ||||  ||||   ||  || |||  | ||  |  || |||  |  |  ||| \+------+---+++---++--+------+--+++++++++-+++-+++++++++-----+-++-+/|    |||||    ||||| ||||||
||  /++++--++++---++--++-+++--+-++--+--++-+++--+--+--+++--+------+---+++---++--+------+--+++++++++-+++-+++++++++\    | || | |    |||||    ||||| ||||||
||  |||||  ||||   || /++-+++--+-++--+--++-+++--+--+--+++--+------+\  |||   ||  |      |  ||||||^|| ||| ||||||||||    | || | |    |||||    ||||| ||||||
||  |||||  ||||   || ||| |||  | \+--+--++-+++--+--+--+++--+------++--+++---++--+------+--++/|||||| ||| ||||||||||    | || | |    |||||    ||||| ||||||
||  ||\++--++++---++-+++-+++-<+--+--+--++-+++--+--+--+++--+------/|  |v|   ||  |      |  || |||||| ||| \+++++++++----+-/| | |    |||||    ||||| ||||||
|| /++-++--++++---++-+++-+++--+--+--+--++-+++--+--+--+++--+-------+--+++---++--+------+--++-++++++-+++\ |||||||||    |  | | |    \++++----+++++-++/|||
|| ||| ||  ||||   || ||| |||  |  |  |  || |||  |  |  |||  |       |  |||   ||  |      |  || |||||| ||\+-+++++++++----+--+-+-+-----++++----+++++-+/ |||
|| ||\-++--++++<--++-+++-+++--+--+--+--++-+++--/  |  |||  |       |  |||   ||  |      |  || |||||| || | |||||||||    |  | | |     ||||    ||||| |  |||
|| ||  || /++++---++-+++-+++--+--+--+--++-+++-----+--+++--+-------+--+++---++--+------+-\|| |||||| || | |||||||||    |  | | |     ||||    ||||| |  |||
|| ||  || |||||   || ||| |||  |  |  |  || |||     |  |||  |       |  ||\---++--+------+-+++-++++++-+/ | |||||||||    |  | | |     ||||    ||||| |  |||
|| ||  || |||||   || ||| |||  |  |  |  || |||     |  |||  |       |  ||    ||  |      | ||\-++++++-+--+-+++++++++----+--+-+-+-----++++----+++++-+--+/|
|| ||  || |||||   || ||| |||  |  |  |  || |||     |  |||  |       |  ||    ||  |      | ||  |||||| |  | |||||||||    |  | | |     ||||    ||||| |  | |
|| ||  || ||\++---++-+++-+++--/  |  |  |\-+++-----+--+++--+-------+--++----++--+------+-++--++++++-+--+-++++++/||    |  | | |     ||||    ||||| |  | |
|| ||  || || ||   || ||| \++-----+--+--+--+++-----+--+++--+-------+--++----++--+------+-++--++++++-+--+-++++++-++----+--+-/ |     ||||    ||||| |  | |
|| ||  || || ||   || |||  |\-----+--+--+--+++-----+--+++--+-------+--++----++--+------+-++--+++++/ |  | |||||| ||    |  |   |     ||||    ||||| |  | |
|| ||  |\-++-++---++-+++--+------+--+--+--+++-----+--+++--+-------+--++----++--+------+-++--+++++--/  | |||||| ||    |  |   |     ||||    ||||| |  | |
|| ||/-+--++-++--\|| |||  |      |  |  |  |||     |  |||  |       |  ||    ||  |      | ||  |||||     | |||||| ||    |  |   |     ||||    ||||| |  | |
|| |||/+--++-++--+++-+++--+\     |  |  |  |||     | /+++--+-------+--++----++--+------+-++--+++++-----+-++++++-++--\ |  |   |     ||||    ||||| |  | |
|| |||||  || ||  ||| |||  ||     |  |  |  |||     | ||||  |       |  ||    ||  \------+-++--++/||     | |||||| ||  | |  |   |     ||||    ||||| |  | |
|| |||||  || ||  ||| |||  \+-----+--+--+--+++-----+-++++--+-------+--++----++---------+-++--++-++-----+-++++++-/|  | |  |   \-----++++----/|||| |  | |
|| |||||  || ||  ||| |||   | /---+--+--+--+++-----+-++++--+-------+--++----++---------+-++--++-++-----+-++++++--+--+-+\ |         ||||     |||| |  | |
|| |||||  || ||  ||| |||   | |   |  |  \--+++-----+-++++--+-------+--++----++---------+-++--++-++-----+-++++++--+--+-++-+---------/|||     |||| |  | |
|| |||||  || ||  ||| \++---+-+---+--+-----+++-----+-++++--+-------/  ||    ||         | ||  || ^|     | ||||||  |  | || |          |||     |||| |  | |
|| |||||  || ||  |||  ||   | |   |  |     |||     \-++++--+----------++----++---------+-++--++-++-----+-++++++--+--+-++-+----------+++-----++++-+--/ |
|| |||||  || ||  |||  ||   | |   \--+-----+++-------++++--+----------++----++---------+-++--++-++-----+-++++++--+--+-++-+----------+++-----+/|| |    |
|| |||||  || ||  |||  ||   | |      |     |||       ||||/-+----------++----++---------+-++--++-++-----+-++++++--+--+-++-+----------+++\    | || |    |
|| |||||  || ||  |||  ||   | |      |     |\+-------+++++-+----------++----++---------+-++--++-++-----+-++++++--+--+-++-+----------++++----+-++-/    |
|| ||^||  |\-++--+++--++---+-+------/     | |       \++++-+----------++----++---------+-++--++-++-----+-++++++--+--/ || |          ||||    | ||      |
|| |||||  |  ||  |||  ||/--+-+------------+-+--------++++-+--------\ ||    ||         | ||  || ||     | ||||||  |    || |          ||||    | ||      |
|| |||||  |  ||  |||  |||  | |            | |        \+++-+--------+-/|    ||         | ||  || ||     | ||||||  |    || |          ||||    \-++------/
\+-+++++--+--++--+++--+++--+-+------------+-+---------+++-+--------+--+----++---------+-++--++-/|     | ||||||  |    || |          ||||      ||
 | |||||  |  ||  |||  |||  | |    /-------+-+-------\ ||| |        |  \----++---------+-++--++--/     | ||||||  |    || |          ||||      ||
 | |||||  |  ||  |\+--+++--+-+----+-------+-/       | ||| |        |       ||         | ||  ||        | ||||||  |    || |          ||||      ||
 \-+++++--+--++--+-+--+++--+-+----+-------+---------+-+++-+--------+-------++---------/ ||  ||        | ||||||  |    || |          ||||      ||
   |||||  |  ||  | |  |||  | |    |       \---------+-+++-+--------+-------++-----------++--++--------+-++++++--+----++-/          ||||      ||
   |||||  |  \+--+-+--+++--+-+----+-----------------+-+++-+--------+-------++-----------++--++--------+-+++++/  |    ||            ||||      ||
   |||||  |   |  | |  |||  | |    |                 | ||| \--------+-------++-----------++--++--------+-+++++---+----++------------++/|      ||
   |\+++--+---+--+-+--+++--+-+----+-----------------+-+++----------+-------++-----------++--++--------+-+++++---/    ||            || |      ||
   | |||  |   \--+-+--+++--+-+----+-----------------+-+++----------+-------++-----------++--++--------+-/||||        ||            || |      ||
   | |\+--+------+-+--+++--/ \----+-----------------+-+++----------+-------++-----------++--++--------+--++++--------+/            || |      ||
   | | |  |      | |  |||         |                 | \++----------+-------/|           |\--++--------+--+++/        |             || |      ||
   | | |  |      | |  |||         \-----------------/  ||          |        |           |   ||        |  |\+---------+-------------++-+------+/
   | | |  |      | |  |\+------------------------------++----------+--------/           |   ||        |  | |         |             || |      |
   | | |  |      | |  | |                              ||          |                    |   \+--------+--+-+---------+-------------/| |      |
   | | |  |      | |  | |                              ||          |                    |    |        |  | |         |              | |      |
   | | |  |      | |  \-+------------------------------++----------+--------------------+----/        |  | |         |              | |      |
   | | |  |      | |    \------------------------------++----------/                    |             |  | \---------+--------------/ |      |
   | \-+--+------/ |                                   \+-------------------------------+-------------+--/           |                |      |
   |   |  |        |                                    \-------------------------------+-------------+--------------+----------------/      |
   |   \--+--------/                                                                    |             |              \-----------------------/
   |      \-----------------------------------------------------------------------------/             |
   \--------------------------------------------------------------------------------------------------/                                               """





const val CART_MADNESS_EXAMPLE = """
/->-\
|   |  /----\
| /-+--+-\  |
| | |  | v  |
\-+-/  \-+--/
  \------/   """