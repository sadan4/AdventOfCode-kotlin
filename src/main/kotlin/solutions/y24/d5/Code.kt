package zip.sadan.solutions.y24.d5

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.collections.list.middle
import zip.sadan.util.collections.list.pop
import zip.sadan.util.collections.list.popHead
import zip.sadan.util.collections.map.getOrPutVal
import zip.sadan.util.collections.pair.map
import zip.sadan.util.collections.pair.toPair
import zip.sadan.util.debug.Solved

private typealias Rules = Map<Int, Set<Int>>

class Code : Solution<String>() {
    override val year: Number = 24
    override val day: Number = 5

    private fun String.parse(): Pair<Rules, List<List<Int>>> {
        val (rulesStr, updatesStr) = this.split("\n\n");
        val rules = HashMap<Int, HashSet<Int>>();
        rulesStr
            .split("\n")
            .map {
                // (\d+)\|(\d+)
                // If $1 is to be printed, then it must be printed sometime before $2, If $2 is also going to be printed
                it
                    .split("|")
                    .toPair()
                    .map {
                        it.toInt()
                    }
            }
            .forEach { (num, isBefore) ->
                rules
                    .getOrPutVal(num, HashSet())
                    .add(isBefore)
            };
        val updates = updatesStr
            .split("\n")
            .map {
                it
                    .split(",")
                    .map {
                        it.toInt()
                    }
            }
        return rules to updates;
    }

    private fun List<Int>.isInCorrectOrder(rules: Rules): Boolean {
        val seen = HashSet<Int>();
        val left = this.toMutableList();
        while (left.isNotEmpty()) {
            val cur = left.popHead();
            val cantBeBefore = rules[cur] ?: emptySet() // no rules for this number;
            if ((cantBeBefore intersect seen).isNotEmpty()) {
                return false
            }
            seen.add(cur)
        }
        return true;
    }

    private fun List<Int>.fixOrder(rules: Rules): List<Int> {
        val seen = HashSet<Int>();
        val left = this.toMutableList();
        val finalList = ArrayList<Int>();
        while (left.isNotEmpty()) {
            val cur = left.popHead();
            val cantBeAfter = rules[cur] ?: emptySet();
            // this shouldn't infinite loop because all should be solveable
            val haveToBeBeforeThis = left
                .filter {
                    cur in (rules[it] ?: emptySet())
                }
                .toMutableList();
            if (haveToBeBeforeThis.isNotEmpty()) {
                left.add(0, cur)
                while (haveToBeBeforeThis.isNotEmpty()) {
                    val toMove = haveToBeBeforeThis.pop();
                    left.removeAll(listOf(toMove))
                    left.add(0, toMove)
                }
                continue;
            }
            if ((cantBeAfter intersect seen).isNotEmpty()) {
                error("List should be possible, this should never happen due to the previous function");
            }
            finalList.add(cur);
        }
        if(finalList.size != this.size) {
            error("fixed list does not have the same size as the input list")
        }
        return finalList;
    }

    @Solved("4462")
    @UseFile("./input.txt")
    override fun part1(input: String): Any? {
        val (rules, updates) = input.parse();
        return updates
            .filter {
                val guh = it.isInCorrectOrder(rules)
                return@filter guh;
            }
            .sumOf {
                it.middle()
            }
    }

    @Solved("6767")
    @UseFile("./input.txt")
    override fun part2(input: String): Any? {
        val (rules, updates) = input.parse();

        return updates
            .filter {
                !it.isInCorrectOrder(rules);
            }
            .map {
                it.fixOrder(rules)
            }
            .sumOf {
                it.middle()
            }
    }
}