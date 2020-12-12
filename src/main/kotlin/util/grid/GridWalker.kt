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
    fun turn(turns: Iterable<Turn>) {
        turns.forEach {
            direction = direction.turn(it)
        }
    }
    fun move(callback: (ScreenCoordinate) -> Unit = {}) {
        currentPosition = currentPosition.next(direction)
        callback.invoke(currentPosition)
    }

    fun move(vector: Vector) {
        currentPosition = currentPosition.next(vector)
    }

    fun move(distance: Int) {
        currentPosition = currentPosition.next(direction, distance)

    }

}
