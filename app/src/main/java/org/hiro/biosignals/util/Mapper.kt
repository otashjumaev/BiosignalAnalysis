package org.hiro.biosignals.util

import kotlin.math.pow

object Mapper {
    fun doubleToComplex(array: MutableList<Float>): Array<Complex> {
        val size = findPowerOf2(array.size)
        val arr = Array(size) { Complex(0.0, 0.0) }
        array.forEachIndexed { index, fl ->
            arr[index] = Complex(fl.toDouble(), 0.0)
        }
        return arr
    }

    fun listToArray(array: MutableList<Float>): DoubleArray {
        val size = findPowerOf2(array.size)
        val arr = DoubleArray(size) { 0.0 }
        array.forEachIndexed { index, fl ->
            arr[index] = fl.toDouble()
        }
        return arr
    }


    private fun findPowerOf2(n: Int): Int {
        var num = n
        var s = 0
        while (num > 0) {
            num /= 2
            s++
        }
        return 2.toDouble().pow(s.toDouble()).toInt()
    }

}