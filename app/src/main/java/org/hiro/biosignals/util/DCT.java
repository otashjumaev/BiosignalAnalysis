package org.hiro.biosignals.util;

import java.util.Objects;


//public class DCT {
//
//    /**
//     * Computes the unscaled DCT type II on the specified array, returning a new array.
//     * The array length can be any value, starting from zero. The returned array has the same length.
//     * <p>For the formula, see <a href="https://en.wikipedia.org/wiki/Discrete_cosine_transform#DCT-II">
//     * Wikipedia: Discrete cosine transform - DCT-II</a>.</p>
//     *
//     * @param vector the vector of numbers to transform
//     * @return an array representing the DCT of the given vector
//     * @throws NullPointerException if the array is {@code null}
//     */
//    public static double[] transform(double[] vector) {
//        Objects.requireNonNull(vector);
//        double[] result = new double[vector.length];
//        double factor = Math.PI / vector.length;
//        for (int i = 0; i < vector.length; i++) {
//            double sum = 0;
//            for (int j = 0; j < vector.length; j++)
//                sum += vector[j] * Math.cos((j + 0.5) * i * factor);
//            result[i] = sum;
//        }
//        return result;
//    }
//
//
//    /**
//     * Computes the unscaled DCT type III on the specified array, returning a new array.
//     * The array length can be any value, starting from zero. The returned array has the same length.
//     * <p>For the formula, see <a href="https://en.wikipedia.org/wiki/Discrete_cosine_transform#DCT-III">
//     * Wikipedia: Discrete cosine transform - DCT-III</a>.</p>
//     *
//     * @param vector the vector of numbers to transform
//     * @return an array representing the DCT of the given vector
//     * @throws NullPointerException if the array is {@code null}
//     */
//    public static double[] inverseTransform(double[] vector) {
//        Objects.requireNonNull(vector);
//        double[] result = new double[vector.length];
//        double factor = Math.PI / vector.length;
//        for (int i = 0; i < vector.length; i++) {
//            double sum = vector[0] / 2;
//            for (int j = 1; j < vector.length; j++)
//                sum += vector[j] * Math.cos(j * (i + 0.5) * factor);
//            result[i] = sum;
//        }
//        return result;
//    }
//
//}
public final class DCT {

    /**
     * Computes the unscaled DCT type II on the specified array in place.
     * The array length must be a power of 2.
     * <p>For the formula, see <a href="https://en.wikipedia.org/wiki/Discrete_cosine_transform#DCT-II">
     * Wikipedia: Discrete cosine transform - DCT-II</a>.</p>
     *
     * @param vector the vector of numbers to transform
     * @return vector itself
     * @throws NullPointerException if the array is {@code null}
     */
    public static double[] transform(double[] vector) {
        Objects.requireNonNull(vector);
        int n = vector.length;
        if (Integer.bitCount(n) != 1)
            throw new IllegalArgumentException("Length must be power of 2");
        transform(vector, 0, n, new double[n]);
        return vector;
    }


    private static void transform(double[] vector, int off, int len, double[] temp) {
        // Algorithm by Byeong Gi Lee, 1984. For details, see:
        // See: http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.118.3056&rep=rep1&type=pdf#page=34
        if (len == 1)
            return;
        int halfLen = len / 2;
        for (int i = 0; i < halfLen; i++) {
            double x = vector[off + i];
            double y = vector[off + len - 1 - i];
            temp[off + i] = x + y;
            temp[off + i + halfLen] = (x - y) / (Math.cos((i + 0.5) * Math.PI / len) * 2);
        }
        transform(temp, off, halfLen, vector);
        transform(temp, off + halfLen, halfLen, vector);
        for (int i = 0; i < halfLen - 1; i++) {
            vector[off + i * 2 + 0] = temp[off + i];
            vector[off + i * 2 + 1] = temp[off + i + halfLen] + temp[off + i + halfLen + 1];
        }
        vector[off + len - 2] = temp[off + halfLen - 1];
        vector[off + len - 1] = temp[off + len - 1];
    }


    /**
     * Computes the unscaled DCT type III on the specified array in place.
     * The array length must be a power of 2.
     * <p>For the formula, see <a href="https://en.wikipedia.org/wiki/Discrete_cosine_transform#DCT-III">
     * Wikipedia: Discrete cosine transform - DCT-III</a>.</p>
     *
     * @param vector the vector of numbers to transform
     * @throws NullPointerException if the array is {@code null}
     */
    public static void inverseTransform(double[] vector) {
        Objects.requireNonNull(vector);
        int n = vector.length;
        if (Integer.bitCount(n) != 1)
            throw new IllegalArgumentException("Length must be power of 2");
        vector[0] /= 2;
        inverseTransform(vector, 0, n, new double[n]);
    }


    private static void inverseTransform(double[] vector, int off, int len, double[] temp) {
        // Algorithm by Byeong Gi Lee, 1984. For details, see:
        // https://www.nayuki.io/res/fast-discrete-cosine-transform-algorithms/lee-new-algo-discrete-cosine-transform.pdf
        if (len == 1)
            return;
        int halfLen = len / 2;
        temp[off + 0] = vector[off + 0];
        temp[off + halfLen] = vector[off + 1];
        for (int i = 1; i < halfLen; i++) {
            temp[off + i] = vector[off + i * 2];
            temp[off + i + halfLen] = vector[off + i * 2 - 1] + vector[off + i * 2 + 1];
        }
        inverseTransform(temp, off, halfLen, vector);
        inverseTransform(temp, off + halfLen, halfLen, vector);
        for (int i = 0; i < halfLen; i++) {
            double x = temp[off + i];
            double y = temp[off + i + halfLen] / (Math.cos((i + 0.5) * Math.PI / len) * 2);
            vector[off + i] = x + y;
            vector[off + len - 1 - i] = x - y;
        }
    }

}

