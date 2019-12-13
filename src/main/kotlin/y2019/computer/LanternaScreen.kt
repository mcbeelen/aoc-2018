package y2019.computer

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.TextCharacter
import com.googlecode.lanterna.TextColor.ANSI
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration
import util.grid.ScreenCoordinate
import y2019.day016_pong.BALL
import y2019.day016_pong.BLOCK
import y2019.day016_pong.PADDLE
import y2019.day016_pong.WALL
import java.awt.Font

class LanternaScreen : ScreenOutputStream {

    private var initializing = true
    private var counter = 0

    private val screen : Screen = initScreen()

    override fun paint(coordinate: ScreenCoordinate, char: Char) {
        val textCharacter = formatTextCharacter(char)
        screen.setCharacter(coordinate.left, coordinate.top, textCharacter)
        screen.refresh()
        if (initializing) {
            counter++
            if (counter == 1012) {
                initializing = false
//                for (i in 0 until 3000) { // Uncomment to allow enough time to attach ScreenRecorder
//                    Thread.sleep(5L)
//                }
            }
        } else {
            Thread.sleep(5L)
        }

    }

    private fun formatTextCharacter(char: Char): TextCharacter {
        return when (char) {
            BLOCK -> TextCharacter(char, ANSI.WHITE, ANSI.BLACK)
            WALL -> TextCharacter(char, ANSI.WHITE, ANSI.WHITE)
            PADDLE -> TextCharacter(char, ANSI.RED, ANSI.BLACK)
            BALL -> TextCharacter(char, ANSI.YELLOW, ANSI.BLACK)
            else -> TextCharacter(char, ANSI.BLACK, ANSI.BLACK)
        }
    }

    override fun stopScreen() {
        screen.stopScreen()
    }
}

private fun initScreen(): Screen {
    val terminalFactory = DefaultTerminalFactory()
    terminalFactory.setInitialTerminalSize(TerminalSize(44, 23))
    val font = Font("Courier New", Font.PLAIN, 24)
    terminalFactory.setTerminalEmulatorFontConfiguration(SwingTerminalFontConfiguration.newInstance(font))
    terminalFactory.setTerminalEmulatorTitle("Intcode Arcade: Brick")

    val terminal: Terminal = terminalFactory.createTerminal()

    val screen: Screen = TerminalScreen(terminal)

    screen.startScreen()
    return screen

}