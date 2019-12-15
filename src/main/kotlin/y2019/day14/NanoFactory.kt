package y2019.day14

import java.util.Deque
import java.util.LinkedList

data class Batch(val reaction: Reaction, val size: Quantity)
typealias Backlog = Deque<Batch>

fun emptyBacklog(): Deque<Pair<Reaction, Quantity>> = LinkedList()
typealias Ingredients = MutableMap<Chemical, Quantity>

const val INITIAL_AMOUNT_OF_ORE = 1_000_000_000_000

class Recipe() {

    val steps: Backlog = LinkedList()
    val ingredients : Ingredients = HashMap()

    fun addStep(batchSize: Long, reaction: Reaction) {
        steps.push(Batch(reaction, batchSize))
    }

    fun increaseNeededQuantity(quantity: Long, chemical: Chemical) {
        ingredients.computeIfPresent(chemical) { _, v -> v + quantity }
        ingredients.computeIfAbsent(chemical) { quantity }
    }

    fun neededQuantity(chemical: Chemical) = ingredients.getOrDefault(chemical, 0L)
}

class NanoFactory(private val knownReactions: Map<Chemical, Reaction>) {

    private val orderedChemicals: List<Chemical> = determineOrderInWhichChemicalsShouldBeProduced(knownReactions)


    fun canIngredientBeProduced(ingredientToProduce: Ingredient, cargoHold: CargoHold, backlog: Backlog = LinkedList(), ingredients: Ingredients = HashMap()): Pair<Boolean, Recipe> {
        val recipe = generateRecipe(ingredientToProduce, cargoHold, Recipe())
        if (cargoHold.hasSufficientOre(recipe.neededQuantity(ORE))) {
            return Pair(true, recipe)
        } else {
            return Pair(false, recipe)
        }
    }


    private fun generateRecipe(ingredient: Ingredient, cargoHold: CargoHold, recipe: Recipe): Recipe {

        val additionalNeededQuantity = determineAdditionalNeededQuantity(ingredient, cargoHold)

        if (additionalNeededQuantity > 0) {
            val reaction = knownReactions.getValue(ingredient.chemical)
            val batchSize = determineNumberOfNeededBatches(additionalNeededQuantity, reaction.output)
            recipe.addStep(batchSize, reaction)

            reaction.input.forEach {
                val additionalNeedForChemical = batchSize * it.quantity
                recipe.increaseNeededQuantity(additionalNeedForChemical, it.chemical)
            }
        }

        val nextChemicalToProduce = findNextIngredientToProduce(ingredient)

        return if (nextChemicalToProduce == null) {
            recipe
        } else {
            val nextQuantity = recipe.neededQuantity(nextChemicalToProduce)
            val nextIngredient = Ingredient(nextQuantity, nextChemicalToProduce)
            generateRecipe(nextIngredient, cargoHold, recipe)
        }

    }

    private fun determineAdditionalNeededQuantity(ingredient: Ingredient, cargoHold: CargoHold): Long {
        if (ingredient.chemical == FUEL) return ingredient.quantity
        val alreadyAvailableQuantity = cargoHold.availableQuantity(ingredient.chemical)
        val additionalNeededQuantity = ingredient.quantity - alreadyAvailableQuantity
        return maxOf(additionalNeededQuantity, 0)
    }

    private fun findNextIngredientToProduce(currentIngredient: Ingredient): Chemical? {
        val indexOfCurrentChemical = orderedChemicals.indexOf(currentIngredient.chemical)
        if (indexOfCurrentChemical == orderedChemicals.size - 1) {
            return null
        }
        return orderedChemicals[indexOfCurrentChemical + 1]
    }

}

fun canQuantityOfFuelBeProduced(quantity: Long, nanoFactory: NanoFactory, initialAmountOfOre: Long): Pair<Boolean, Recipe> {
    return canQuantityOfFuelBeProduced(quantity, nanoFactory, CargoHold(stock = mapOf(Pair(ORE, initialAmountOfOre))))
}

internal fun canQuantityOfFuelBeProduced(quantity: Long, nanoFactory: NanoFactory, cargoHold: CargoHold): Pair<Boolean, Recipe> {
    val ingredient = Ingredient(quantity, FUEL)
    return nanoFactory.canIngredientBeProduced(ingredient, cargoHold)
}


fun minimumAmountOfOreNeededForOneFuel(reactionDescriptions: String): Long {
    val reactions = reactionDescriptions.trimIndent().lines().map { parseReaction(it) }.toMutableList()

    val neededAmountOfChemicals: MutableMap<String, Long> = hashMapOf(Pair(FUEL, 1L))
    val reactionsPerOutputChemical: MutableMap<String, Reaction> = reactions.map { Pair(it.output.chemical, it) }.toMap().toMutableMap()

    while (reactionsPerOutputChemical.isNotEmpty()) {
        val chemical = findChemicalToProduce(reactionsPerOutputChemical)

        val (inputChemicals, output) = reactionsPerOutputChemical.getValue(chemical)
        val quantityToProduce = neededAmountOfChemicals.getValue(chemical)
        val numberOFBatchesToProduce = determineNumberOfNeededBatches(quantityToProduce, output)

        inputChemicals.forEach {
            val additionalNeedForChemical = numberOFBatchesToProduce * it.quantity
            neededAmountOfChemicals.computeIfPresent(it.chemical) { _, v -> v + additionalNeedForChemical }
            neededAmountOfChemicals.computeIfAbsent(it.chemical) {
                additionalNeedForChemical
            }
        }

        reactionsPerOutputChemical.remove(chemical)

    }

    return neededAmountOfChemicals.getValue(ORE)
}


fun maximumAmountOfFuelToProduce(reactionDescriptions: String): Long {
    val reactions = parseReactions(reactionDescriptions)
    val nanoFactory = NanoFactory(reactions)
    var cargoHold = defaultCargoHold()
    val amountOfOreNeededForOneFuel = minimumAmountOfOreNeededForOneFuel(reactionDescriptions)
    var stepSize = INITIAL_AMOUNT_OF_ORE / amountOfOreNeededForOneFuel

    while (stepSize > 0) {
        val (success, recipe) = canQuantityOfFuelBeProduced(stepSize, nanoFactory, cargoHold)
        if (success) {
            cargoHold = cargoHold.performAllProcesses(recipe.steps)
        } else {
            stepSize = reduceStepSize(stepSize)
        }
    }
    return cargoHold.availableQuantity(FUEL)
}


fun reduceStepSize(stepSize: Long) = if (stepSize == 1L) 0 else stepSize / 2
private fun determineNumberOfNeededBatches(quantityToProduce: Long, output: Ingredient): Long {
    var numberOFBatchesToProduce = quantityToProduce / output.quantity
    if (quantityToProduce % output.quantity > 0) {
        numberOFBatchesToProduce += 1
    }
    return numberOFBatchesToProduce
}


fun findChemicalToProduce(reactionsPerOutputChemical: Map<String, Reaction>): String {
    return reactionsPerOutputChemical.keys.first {
        isNoInputForAnotherChemical(it, reactionsPerOutputChemical)
    }
}


fun main() {
    println(minimumAmountOfOreNeededForOneFuel(NANO_REACTIONS))
    println(maximumAmountOfFuelToProduce(NANO_REACTIONS))
}


internal const val NANO_REACTIONS = """
1 HJDM, 1 BMPDP, 8 DRCX, 2 TCTBL, 1 KGWDJ, 16 BRLF, 2 LWPB, 7 KDFQ => 6 ZSPL
1 PVRCK, 3 RSLR, 4 JBZD => 6 LCHRC
10 FCBVC, 1 TSJSJ, 20 SQCQ => 9 PNQLP
1 MBVL => 6 TSZJ
1 HWGQF => 4 ZSLVH
1 TBDSC, 13 TSZJ => 1 HRZH
1 RSLR, 1 LJWM => 3 RSFJR
1 VMZFB => 2 MBVL
4 DSTHJ, 2 TSZJ, 13 MBVL => 4 ZWLGK
1 MKTZ, 18 RVFJB, 1 RSLR, 2 HRZH, 14 ZWLGK, 4 RJFTV => 1 ZCVL
6 KDFQ, 1 PNQLP, 1 HRZH => 9 DLPMH
1 DSVT, 22 DRCX, 18 RJFTV, 2 MKTZ, 13 FVZBX, 15 SLTNZ, 7 ZSLVH => 5 GWJC
2 JZSJ, 3 ZSLVH, 6 HNRXC => 8 RJFTV
1 TSZJ => 7 GFVG
5 VMZFB => 4 JBZD
1 PBFZ, 23 JBZD, 2 LJWM => 1 TSJSJ
7 ZPQD => 7 VMZFB
2 LCHRC => 8 PXHK
2 TSZJ, 1 KCXMF, 1 FKJGC => 6 HWGQF
4 PBFZ => 1 FCBVC
1 GMWHM, 4 JQBKW => 8 SQCQ
5 SHMP => 5 PVRCK
10 KCXMF => 3 DRCX
15 VMZFB, 2 RSFJR => 6 KDFQ
35 HNRXC => 2 CJLG
8 MKTZ, 1 FCBVC, 12 HJDM => 9 BRLF
171 ORE => 8 GMWHM
8 RVFJB, 3 CJLG, 9 SLTNZ => 3 LWPB
1 PXHK, 2 RSFJR => 3 FVZBX
1 CJLG, 1 HRZH, 10 MKTZ => 8 KGWDJ
1 RSFJR => 3 FKJGC
1 NXCZM, 31 FKJGC => 2 MKTZ
18 XLWBP => 6 MBLWL
22 HNRXC => 8 FTGK
3 KGWDJ, 1 MLBJ, 5 HJDM => 7 DSVT
9 KDFQ => 5 NXCZM
2 RVFJB, 4 LGDKL, 1 PXHK => 5 CVTR
1 RSFJR, 6 GMWHM, 20 TSJSJ => 9 LGDKL
5 KCXMF => 9 RBDP
6 GWJC, 16 ZCVL, 29 JZSJ, 1 ZSPL, 35 MBLWL, 30 BWFRH, 2 MSFDB, 13 BMPDP, 11 FTGK, 1 ZWLGK => 1 FUEL
6 GFVG, 2 TVQP => 8 HJDM
1 CJLG, 13 PBFZ => 6 JZSJ
3 CVTR => 3 BMPDP
16 FPKMV, 1 ZSLVH => 8 MSFDB
9 JBZD, 12 LCHRC => 8 TBDSC
133 ORE => 3 LJWM
107 ORE => 7 SHMP
1 KDFQ, 1 LJWM => 9 FPKMV
3 PXHK => 4 BWFRH
123 ORE => 4 JQBKW
2 FVZBX, 1 JZSJ => 8 XLWBP
117 ORE => 2 ZPQD
7 NXCZM => 7 HNRXC
1 MLBJ, 22 RSLR => 8 KCXMF
2 TBDSC => 8 RVFJB
1 KDFQ, 23 DSTHJ => 7 SLTNZ
3 RSFJR => 6 MLBJ
5 PVRCK, 2 SQCQ => 9 RSLR
1 LGDKL, 17 MBVL, 6 PNQLP => 5 TVQP
3 RBDP => 6 TCTBL
1 DLPMH, 1 GFVG, 3 MBVL => 2 DSTHJ
21 VMZFB, 2 LJWM => 1 PBFZ"""

