package y2019.computer

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.TextCharacter
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration
import util.grid.ScreenCoordinate
import java.awt.Font

class LanternaScreen : ScreenOutputStream {

    private var initializing = true
    private var counter = 0

    private val screen : Screen = initScreen()

    override fun paint(coordinate: ScreenCoordinate, char: Char) {
        screen.setCharacter(coordinate.left, coordinate.top, TextCharacter(char))
        screen.refresh()
        if (initializing) {
            counter++
            if (counter == 1012) {
                initializing = false
            }
        } else {
            Thread.sleep(5L)
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
    val terminal: Terminal = terminalFactory.createTerminal()
    val screen: Screen = TerminalScreen(terminal)
    screen.startScreen()
    return screen

}