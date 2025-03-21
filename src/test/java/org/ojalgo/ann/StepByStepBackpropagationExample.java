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
package org.ojalgo.ann;

import static org.ojalgo.ann.ArtificialNeuralNetwork.Activator.SIGMOID;
import static org.ojalgo.ann.ArtificialNeuralNetwork.Error.HALF_SQUARED_DIFFERENCE;
import static org.ojalgo.function.constant.PrimitiveMath.HALF;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.ojalgo.TestUtils;
import org.ojalgo.matrix.store.PhysicalStore.Factory;
import org.ojalgo.matrix.store.Primitive64Store;
import org.ojalgo.structure.Access1D;
import org.ojalgo.type.context.NumberContext;

/**
 * https://mattmazur.com/2015/03/17/a-step-by-step-backpropagation-example/
 * https://github.com/mattm/simple-neural-network
 *
 * @author apete
 */
public class StepByStepBackpropagationExample extends BackPropagationExample {

    private static final NumberContext PRECISION = NumberContext.of(8, 8);

    public StepByStepBackpropagationExample() {
        super();
    }

    @Test
    public void testStepByStepBackpropagationExample() {

        NumberContext precision = this.precision();
        Factory<Double, Primitive64Store> factory = Primitive64Store.FACTORY;

        ArtificialNeuralNetwork.Error errorMeassure = HALF_SQUARED_DIFFERENCE;

        ArtificialNeuralNetwork network = this.getInitialNetwork(Primitive64Store.FACTORY);

        NetworkTrainer trainer = network.newTrainer();
        NetworkInvoker invoker = network.newInvoker();

        Data testCase = this.getTestCases().get(0);

        Primitive64Store givenInput = testCase.input;
        Primitive64Store targetOutput = testCase.target;
        Access1D<Double> expectedOutput = testCase.expected;
        Access1D<Double> actualOutput = invoker.invoke(givenInput);

        TestUtils.assertEquals(expectedOutput, actualOutput, precision);

        double expectedError = 0.298371109;
        double actualError = errorMeassure.invoke(targetOutput, actualOutput);

        TestUtils.assertEquals(expectedError, actualError, precision);

        trainer.rate(HALF).train(givenInput, targetOutput);

        // 0.40 w5
        TestUtils.assertEquals(0.35891648, network.getWeight(1, 0, 0), precision);
        // 0.45 w6
        TestUtils.assertEquals(0.408666186, network.getWeight(1, 1, 0), precision);
        // 0.50 w7
        TestUtils.assertEquals(0.511301270, network.getWeight(1, 0, 1), precision);
        // 0.55 w8
        TestUtils.assertEquals(0.561370121, network.getWeight(1, 1, 1), precision);

        // 0.15 w1
        TestUtils.assertEquals(0.149780716, network.getWeight(0, 0, 0), precision);
        // 0.20 w2
        TestUtils.assertEquals(0.19956143, network.getWeight(0, 1, 0), precision);
        // 0.25 w3
        TestUtils.assertEquals(0.24975114, network.getWeight(0, 0, 1), precision);
        // 0.30 w4
        TestUtils.assertEquals(0.29950229, network.getWeight(0, 1, 1), precision);

        double expectedErrorAfterTraining = 0.291027924;
        double actualErrorAfterTraining = errorMeassure.invoke(targetOutput, invoker.invoke(givenInput));

        // In the example the bias are not updated, ojAlgo does update them
        // This should give more aggressive learning in this single step
        TestUtils.assertTrue(expectedErrorAfterTraining > actualErrorAfterTraining);
        TestUtils.assertTrue(actualError > actualErrorAfterTraining);

        // Create a larger, more complex network, to make sure there are no IndexOutOfRangeExceptions or similar..
        ArtificialNeuralNetwork largerNetwork = ArtificialNeuralNetwork.builder(2).layer(5).layer(3).layer(4).layer(2).get();

        NetworkTrainer largerTrainer = largerNetwork.newTrainer();
        NetworkInvoker largerInvoker = largerNetwork.newInvoker();

        Access1D<Double> preTrainingOutput = factory.rows(largerInvoker.invoke(givenInput));
        largerTrainer.rate(HALF).train(givenInput, targetOutput);
        Access1D<Double> postTrainingOutput = factory.rows(largerInvoker.invoke(givenInput));

        // Even in this case training should reduce the error
        TestUtils.assertTrue(errorMeassure.invoke(targetOutput, preTrainingOutput) > errorMeassure.invoke(targetOutput, postTrainingOutput));
    }

    @Override
    protected ArtificialNeuralNetwork getInitialNetwork(final Factory<Double, ?> factory) {

        ArtificialNeuralNetwork network = ArtificialNeuralNetwork.builder(factory, 2).layer(2, SIGMOID).layer(2, SIGMOID).get();

        NetworkTrainer trainer = network.newTrainer();

        trainer.weight(0, 0, 0, 0.15);
        trainer.weight(0, 1, 0, 0.20);
        trainer.weight(0, 0, 1, 0.25);
        trainer.weight(0, 1, 1, 0.30);

        trainer.bias(0, 0, 0.35);
        trainer.bias(0, 1, 0.35);

        trainer.weight(1, 0, 0, 0.40);
        trainer.weight(1, 1, 0, 0.45);
        trainer.weight(1, 0, 1, 0.50);
        trainer.weight(1, 1, 1, 0.55);

        trainer.bias(1, 0, 0.60);
        trainer.bias(1, 1, 0.60);

        return network;
    }

    @Override
    protected List<Data> getTestCases() {

        Data retVal = new Data(0.5);

        retVal.input(0.05, 0.10);
        retVal.target(0.01, 0.99);
        retVal.expected(0.75136507, 0.772928465);

        return Collections.singletonList(retVal);
    }

    @Override
    protected NumberContext precision() {
        return PRECISION;
    }

}
