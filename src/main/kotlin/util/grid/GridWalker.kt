package util.grid


open class GridWalker {
    var currentPosition: ScreenCoordinate = ORIGIN
    var direction: Direction = Direction.UP

    protected fun turnAndMove(turn: Turn, callback: (ScreenCoordinate) -> Unit = {}) {
        turn(turn)
        move(callback)
    }

    fun turn(turn: Turn) {
        direction = direction.turn(turn)
    }
    fun move(callback: (ScreenCoordinate) -> Unit = {}) {
        currentPosition = currentPosition.next(direction)
        callback.invoke(currentPosition)
    }

}