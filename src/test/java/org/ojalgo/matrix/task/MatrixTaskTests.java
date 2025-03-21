/*
 * Copyright 1997-2023 Optimatika
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.ojalgo.matrix.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.ojalgo.TestUtils;
import org.ojalgo.matrix.decomposition.MatrixDecompositionTests;

/**
 * @author apete
 */
public abstract class MatrixTaskTests {

    static final boolean DEBUG = false;

    public static List<DeterminantTask<Double>> getPrimitiveFull() {

        final ArrayList<DeterminantTask<Double>> retVal = new ArrayList<>();

        Collections.addAll(retVal, MatrixDecompositionTests.getPrimitiveLU());

        Collections.addAll(retVal, MatrixDecompositionTests.getPrimitiveEigenvalueGeneral());

        Collections.addAll(retVal, MatrixDecompositionTests.getPrimitiveQR());

        return retVal;
    }

    public static List<DeterminantTask<Double>> getPrimitiveSymmetric() {

        final ArrayList<DeterminantTask<Double>> retVal = new ArrayList<>();

        Collections.addAll(retVal, MatrixDecompositionTests.getPrimitiveCholesky());

        Collections.addAll(retVal, MatrixDecompositionTests.getPrimitiveEigenvalueSymmetric());

        Collections.addAll(retVal, MatrixDecompositionTests.getPrimitiveLU());

        Collections.addAll(retVal, MatrixDecompositionTests.getPrimitiveEigenvalueGeneral());

        Collections.addAll(retVal, MatrixDecompositionTests.getPrimitiveQR());

        return retVal;
    }

    @BeforeEach
    public void setUp() {
        TestUtils.minimiseAllBranchLimits();
    }
}
