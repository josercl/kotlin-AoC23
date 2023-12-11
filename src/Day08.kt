import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = readInput("Day08")
//    part1(input).println()
    part2(input).println()
}

fun part1(input: List<String>): Int {
    val (directions, instructions) = parseInput(input)

    return calculateSteps(directions, instructions, "AAA") { it == "ZZZ" }
}

fun part2(input: List<String>): Any {
    val (directions, instructions) = parseInput(input)

    var steps = 0
    var currentNodes = instructions.keys.filter { it.endsWith('A') }
    var currentInstruction: Char
    var index = 0
    var zNodes = 0

    /*while (zNodes != currentNodes.size) {
        currentInstruction = directions[index]
        index = (index + 1) % directions.length
        currentNodes = currentNodes.map { currentNode ->
            when (currentInstruction) {
                'L' -> instructions[currentNode]!!.first
                else -> instructions[currentNode]!!.second
            }
        }
        zNodes = currentNodes.count { it.endsWith('Z') }
        steps += 1
    }*/
    return currentNodes.map { start ->
        calculateSteps(directions, instructions, start) { it.endsWith('Z') }
    }.lcm()
}

private fun List<Int>.lcm(): Int = reduce(::lcm)

private fun lcm(a: Int, b: Int): Int {
    if (a == 0 || b == 0) {
        return 0
    }
    val absNumber1: Int = abs(a)
    val absNumber2: Int = abs(b)
    val absHigherNumber = max(absNumber1, absNumber2)
    val absLowerNumber = min(absNumber1, absNumber2)
    var lcm = absHigherNumber
    while (lcm % absLowerNumber != 0) {
        lcm += absHigherNumber
    }
    return lcm
}

private fun parseInput(input: List<String>): Pair<String, Map<String, Pair<String, String>>> {
    val directions = input.first()

    val instructions = input.drop(2).associate { line ->
        val (node, lr) = line.split(" = ")
        val (left, right) = lr.drop(1).dropLast(1).split(", ")
        node to (left to right)
    }

    return directions to instructions
}

private fun calculateSteps(
    directions: String,
    instructions: Map<String, Pair<String, String>>,
    startNode: String,
    endNode: (String) -> Boolean
): Int {
    var steps = 0
    var currentNode = startNode
    var currentInstruction: Char
    var index = 0
    while (!endNode(currentNode)) {
        currentInstruction = directions[index]
        index = (index + 1) % directions.length
        currentNode = when (currentInstruction) {
            'L' -> instructions[currentNode]!!.first
            else -> instructions[currentNode]!!.second
        }
        steps += 1
    }
    return steps
}
