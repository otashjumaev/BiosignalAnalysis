package org.hiro.biosignals.util

import kotlin.math.*

class BioSignal(val dataSet: MutableList<Float>) {
    private val times = (dataSet.size - 100) / 25 + 1

    companion object {

        fun getProperties() = listOf(
            "IEMG" to "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
            "MAV" to "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
            "SSI" to "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
            "VAR" to "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
            "RMS" to "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
            "WL" to "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
            "AAC" to "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
            "LOG" to "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum"
        )

    }

    private fun getElement(i: Int, j: Int) = dataSet[i * 25 + j]

    fun calcIEMG(): ArrayList<Float> {
        val result = arrayListOf<Float>()
        repeat(times) { i ->
            var sum = 0f
            repeat(100) { j ->
                sum += abs(getElement(i, j))
            }
            result.add(sum)
        }
        return result
    }

    fun calcMAV(): ArrayList<Float> {
        val result = arrayListOf<Float>()
        repeat(times) { i ->
            var sum = 0f
            repeat(100) { j ->
                sum += abs(getElement(i, j))
            }
            result.add(sum / 100f)
        }
        return result
    }

    fun calcSSI(): ArrayList<Float> {
        val result = arrayListOf<Float>()
        repeat(times) { i ->
            var sum = 0f
            repeat(100) { j ->
                sum += abs(getElement(i, j)).pow(2)
            }
            result.add(sum)
        }
        return result
    }

    fun calcVAR(): ArrayList<Float> {
        val result = arrayListOf<Float>()
        repeat(times) { i ->
            val xAvg = dataSet.subList(i * 100, i * 100 + 100).sum() / 100f
            var sum = 0f
            repeat(100) { j ->
                sum += abs(getElement(i, j) - xAvg).pow(2)
            }
            result.add(sum / 100f)
        }
        return result
    }

    fun calcRMS(): ArrayList<Float> {
        val result = arrayListOf<Float>()
        repeat(times) { i ->
            var sum = 0f
            repeat(100) { j ->
                sum += getElement(i, j).pow(2)
            }
            result.add(sqrt(sum / 100f))
        }
        return result
    }

    fun calcWL(): ArrayList<Float> {
        val result = arrayListOf<Float>()
        repeat(times) { i ->
            var sum = 0f
            repeat(99) { j ->
                sum += abs(getElement(i, j + 1) - getElement(i, j))
            }
            result.add(sum)
        }
        return result
    }

    fun calcAAC(): ArrayList<Float> {
        val result = arrayListOf<Float>()
        repeat(times) { i ->
            var sum = 0f
            repeat(99) { j ->
                sum += abs(getElement(i, j + 1) - getElement(i, j))
            }
            result.add(sum / 100f)
        }
        return result
    }

    fun calcLOG(): ArrayList<Float> {
        val result = arrayListOf<Float>()
        repeat(times) { i ->
            var sum = 0f
            repeat(100) { j ->
                sum += ln(abs(getElement(i, j)))
            }
            result.add(exp(sum / 100f))
        }
        return result
    }

    fun selectByOrder(order: Int): ArrayList<Float> = when (order) {
        0 -> calcIEMG()
        1 -> calcMAV()
        2 -> calcSSI()
        3 -> calcVAR()
        4 -> calcRMS()
        5 -> calcWL()
        6 -> calcAAC()
        7 -> calcLOG()
        else -> throw IllegalArgumentException("NO SUCH PROPERTY")
    }

    fun calcAvg(pos: Int): Float {
        val d = selectByOrder(pos)
        return d.sum() / d.size.toFloat()
    }
}