package y2019.day14

data class CargoHold(val stock: Map<Chemical, Quantity>) {
    fun availableQuantity(chemical: Chemical): Long = stock.getOrDefault(chemical, 0L)
    fun performAllProcesses(backlog: Backlog): CargoHold {
        val newStock: MutableMap<Chemical, Quantity> = HashMap(stock)
        while (backlog.isNotEmpty()) {
            val (reaction, numberOfBatches) = backlog.pop()
            reaction.input.forEach {
                newStock.computeIfPresent(it.chemical) { _, availableQuantity -> availableQuantity - it.quantity * numberOfBatches }
            }
            val producedQuantity = reaction.output.quantity * numberOfBatches
            newStock.computeIfPresent(reaction.output.chemical) { _, availableQuantity -> availableQuantity + producedQuantity }
            newStock.putIfAbsent(reaction.output.chemical, producedQuantity)
        }
        return CargoHold(newStock)
    }

    fun hasSufficientOre(quantity: Quantity) = stock.getValue(ORE) >= quantity
}

fun defaultCargoHold(): CargoHold = buildCargoHold(INITIAL_AMOUNT_OF_ORE)

fun buildCargoHold(amountOfOre: Long) = CargoHold(mapOf(Pair(ORE, amountOfOre)))