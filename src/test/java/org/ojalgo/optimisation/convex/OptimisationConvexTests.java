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
package org.ojalgo.optimisation.convex;

import org.ojalgo.TestUtils;
import org.ojalgo.matrix.store.Primitive64Store;
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Optimisation;
import org.ojalgo.type.context.NumberContext;

/**
 * @author apete
 */
public abstract class OptimisationConvexTests {

    static boolean DEBUG = false;

    protected static void assertDirectAndIterativeEquals(final ConvexSolver.Builder builder, final NumberContext accuracy, Optimisation.Options options) {

        if (options == null) {
            options = new Optimisation.Options();
        }

        if (accuracy != null) {
            options.solution = accuracy;
        }

        if (builder.countInequalityConstraints() > 0) {
            // ActiveSetSolver (ASS)

            DirectASS directASS = new DirectASS(builder.getConvexData(Primitive64Store.FACTORY), options);
            Optimisation.Result direct = directASS.solve();

            IterativeASS iterativeASS = new IterativeASS(builder.getConvexData(Primitive64Store.FACTORY), options);
            Optimisation.Result iterative = iterativeASS.solve();

            if (!direct.getState().isFeasible()) {
                TestUtils.assertFalse(iterative.getState().isFeasible());
            } else if (accuracy != null) {
                TestUtils.assertStateAndSolution(direct, iterative, accuracy);
            } else {
                TestUtils.assertStateAndSolution(direct, iterative);
            }
        }
    }

    protected static void assertDirectAndIterativeEquals(final ExpressionsBasedModel model, final NumberContext accuracy) {

        ConvexSolver.Builder builder = ConvexSolver.newBuilder();

        ConvexSolver.copy(model, builder);

        OptimisationConvexTests.assertDirectAndIterativeEquals(builder, accuracy, model.options);
    }

    public static ConvexData<Double> getOptimisationData(final ConvexSolver.Builder convexSolverBuilder) {
        return convexSolverBuilder.getConvexData(Primitive64Store.FACTORY);
    }

}
