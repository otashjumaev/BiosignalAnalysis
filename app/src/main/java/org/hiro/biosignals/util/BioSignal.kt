package org.hiro.biosignals.util

import kotlin.math.*

class BioSignal(val dataSet: MutableList<Float>) {
    private fun times(t: Boolean) = if (t) (dataSet.size - 100) / 25 + 1 else (dataSet.size / 100)

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

    private fun getElement(i: Int, j: Int, it: Int) = dataSet[i * it + j]

    fun calcIEMG(b: Boolean): ArrayList<Float> {
        val it = if (b) 25 else 100
        val result = arrayListOf<Float>()
        repeat(times(b)) { i ->
            var sum = 0f
            repeat(100) { j ->
                sum += abs(getElement(i, j, it))
            }
            result.add(sum)
        }
        return result
    }

    fun calcMAV(b: Boolean): ArrayList<Float> {
        val it = if (b) 100 else 25
        val result = arrayListOf<Float>()
        repeat(times(b)) { i ->
            var sum = 0f
            repeat(100) { j ->
                sum += abs(getElement(i, j, it))
            }
            result.add(sum / 100f)
        }
        return result
    }

    fun calcSSI(b: Boolean): ArrayList<Float> {
        val it = if (b) 100 else 25
        val result = arrayListOf<Float>()
        repeat(times(b)) { i ->
            var sum = 0f
            repeat(100) { j ->
                sum += abs(getElement(i, j, it)).pow(2)
            }
            result.add(sum)
        }
        return result
    }

    fun calcVAR(b: Boolean): ArrayList<Float> {
        val it = if (b) 100 else 25
        val result = arrayListOf<Float>()
        repeat(times(b)) { i ->
            val xAvg = dataSet.subList(i * 100, i * 100 + 100).sum() / 100f
            var sum = 0f
            repeat(100) { j ->
                sum += abs(getElement(i, j, it) - xAvg).pow(2)
            }
            result.add(sum / 100f)
        }
        return result
    }

    fun calcRMS(b: Boolean): ArrayList<Float> {
        val it = if (b) 100 else 25
        val result = arrayListOf<Float>()
        repeat(times(b)) { i ->
            var sum = 0f
            repeat(100) { j ->
                sum += getElement(i, j, it).pow(2)
            }
            result.add(sqrt(sum / 100f))
        }
        return result
    }

    fun calcWL(b: Boolean): ArrayList<Float> {
        val it = if (b) 100 else 25
        val result = arrayListOf<Float>()
        repeat(times(b)) { i ->
            var sum = 0f
            repeat(99) { j ->
                sum += abs(getElement(i, j + 1, it) - getElement(i, j, it))
            }
            result.add(sum)
        }
        return result
    }

    fun calcAAC(b: Boolean): ArrayList<Float> {
        val it = if (b) 100 else 25
        val result = arrayListOf<Float>()
        repeat(times(b)) { i ->
            var sum = 0f
            repeat(99) { j ->
                sum += abs(getElement(i, j + 1, it) - getElement(i, j, it))
            }
            result.add(sum / 100f)
        }
        return result
    }

    fun calcLOG(b: Boolean): ArrayList<Float> {
        val it = if (b) 100 else 25
        val result = arrayListOf<Float>()
        repeat(times(b)) { i ->
            var sum = 0f
            repeat(100) { j ->
                sum += ln(abs(getElement(i, j, it)))
            }
            result.add(exp(sum / 100f))
        }
        return result
    }

    fun selectByOrder(order: Int, b: Boolean): ArrayList<Float> = when (order) {
        0 -> calcIEMG(b)
        1 -> calcMAV(b)
        2 -> calcSSI(b)
        3 -> calcVAR(b)
        4 -> calcRMS(b)
        5 -> calcWL(b)
        6 -> calcAAC(b)
        7 -> calcLOG(b)
        else -> throw IllegalArgumentException("NO SUCH PROPERTY")
    }

    fun calcAvg(pos: Int, b: Boolean): Float {
        val d = selectByOrder(pos, b)
        return d.sum() / d.size.toFloat()
    }
}