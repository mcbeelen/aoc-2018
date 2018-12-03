package day03_overlapping_fabric

import kotlin.system.measureTimeMillis

class FabricClaimResolver {


    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            val claims = DAY_03_INPUT.trimIndent().lines().map { parseClaim(it) }

            val fabric = Fabric(claims)
            // partOne(fabric)

            partTwo(fabric)

        }


        private fun partOne(fabric: Fabric) {
            val measureTimeMillis = measureTimeMillis {
                println("Their are multipleClaims for ${fabric.countInchesOfOverlappingClaims()} inches")
            }
            println("Solved part one in ${measureTimeMillis}ms")
        }


        private fun partTwo(fabric: Fabric) {
            val measureTimeMillis = measureTimeMillis {
                println("The non overlapping claim has id ${fabric.findIdOfNonOverlappingClaim()}")
            }
            println("Solved part two in ${measureTimeMillis}ms")
        }

    }

}