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
package org.ojalgo.matrix;

import org.junit.jupiter.api.Test;
import org.ojalgo.TestUtils;

public class SpecialTest extends MatrixTests {

    @Test
    public void testDenseReceiver() {

        MatrixQ128.DenseReceiver rDR = MatrixQ128.FACTORY.makeDense(5, 7);
        MatrixR032.DenseReceiver p32DR = MatrixR032.FACTORY.makeDense(5, 7);

        rDR.set(1, 1, 1D);
        p32DR.set(1, 1, 1D);

        rDR.set(3, 5, 1D);
        p32DR.set(3, 5, 1D);

        rDR.set(4, 2, 1D);
        p32DR.set(4, 2, 1D);

        TestUtils.assertEquals(rDR.get(), p32DR.get());
    }

    @Test
    public void testSparseReceiver() {

        MatrixQ128.SparseReceiver rSR = MatrixQ128.FACTORY.makeSparse(5, 7);
        MatrixR032.SparseReceiver p32SR = MatrixR032.FACTORY.makeSparse(5, 7);

        rSR.set(1, 1, 1D);
        p32SR.set(1, 1, 1D);

        rSR.set(3, 5, 1D);
        p32SR.set(3, 5, 1D);

        rSR.set(4, 2, 1D);
        p32SR.set(4, 2, 1D);

        TestUtils.assertEquals(rSR.get(), p32SR.get());
    }

}
