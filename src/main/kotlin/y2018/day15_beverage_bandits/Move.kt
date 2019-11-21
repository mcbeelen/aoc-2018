package y2018.day15_beverage_bandits

import util.grid.search.Edge

class Move(origin: BattleCoordinate, destination: BattleCoordinate, distance : Int = 1) :
        Edge<BattleCoordinate>(origin, destination, distance)
