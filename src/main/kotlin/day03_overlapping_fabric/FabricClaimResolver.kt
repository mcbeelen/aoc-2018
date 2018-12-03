package day03_overlapping_fabric

import kotlin.system.measureTimeMillis

class FabricClaimResolver {




    companion object {

        @JvmStatic
        fun main(args: Array<String>) {


            val measureTimeMillis = measureTimeMillis {

                val claims = DAY_03_INPUT.trimIndent().lines()
                        .map { parseClaim(it) }

                val fabric = Fabric(claims)

                println("Their are multipleClaims for ${fabric.countInchesOfOverlappingClaims()} inches")

            }

            println("Solved in ${measureTimeMillis}ms")

        }

    }

}