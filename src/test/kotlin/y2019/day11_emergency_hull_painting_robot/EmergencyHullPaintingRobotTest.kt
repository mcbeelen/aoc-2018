package y2019.day11_emergency_hull_painting_robot

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import util.grid.Direction.DOWN
import util.grid.Direction.LEFT
import util.grid.Direction.UP
import util.grid.ORIGIN
import util.grid.at
import y2019.day11_emergency_hull_painting_robot.PanelColor.BLACK

class EmergencyHullPaintingRobotTest {
    @Test
    fun name() {
        val robot = EmergencyHullPaintingRobot(BLACK)

        assertThat(robot.scanPanel(), equalTo(BLACK))
        assertThat(robot.direction, equalTo(UP))
        assertThat(robot.currentPosition, equalTo(ORIGIN))

        robot.turnLeft()
        assertThat(robot.direction, equalTo(LEFT))
        assertThat(robot.currentPosition, equalTo(at(-1,0)))

        robot.turnLeft()

        assertThat(robot.direction, equalTo(DOWN))
        assertThat(robot.currentPosition, equalTo(at(-1,1)))

        robot.turnRight()

        assertThat(robot.direction, equalTo(LEFT))
        assertThat(robot.currentPosition, equalTo(at(-2,1)))

        assertThat(robot.getNumberOfPiantedPanels(), equalTo(3))
    }

    @Test
    fun verifyPartOne() {
        assertThat(countNumberOfPaintedPanels(), equalTo(1985))
    }


}