package util.grid


open class GridWalker {
    var currentPosition: ScreenCoordinate = ORIGIN
    var direction: Direction = Direction.UP

    protected fun turnAndMove(turn: Turn, callback: (ScreenCoordinate) -> Unit = {}) {
        direction = direction.turn(turn)
        currentPosition = currentPosition.next(direction)
    }
}