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
package org.ojalgo.optimisation.integer;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.ModelFileTest;
import org.ojalgo.type.context.NumberContext;

/**
 * Some test cases from MIPLIB added a long time ago. These tests are typically a bit harder than
 * {@linkplain MIPLIBTheEasySet}.
 *
 * @author apete
 */
public class MipCase extends OptimisationIntegerTests implements ModelFileTest {

    private static final NumberContext ACCURACY = NumberContext.of(8, 6);

    private static void doTest(final String modelName, final String expMinValString, final String expMaxValString) {

        ExpressionsBasedModel model = ModelFileTest.makeModel("miplib", modelName, false);

        // model.options.debug(Optimisation.Solver.class);
        // model.options.debug(IntegerSolver.class);
        // model.options.debug(ConvexSolver.class);
        // model.options.debug(LinearSolver.class);
        // model.options.progress(IntegerSolver.class);
        // model.options.validate = false;
        // model.options.mip_defer = 0.25;
        // model.options.mip_gap = 1.0E-5;

        // model.options.integer(IntegerStrategy.DEFAULT.withGapTolerance(NumberContext.of(3)));

        ModelFileTest.assertValues(model, expMinValString, expMaxValString, ACCURACY);
    }

    /**
     * https://miplib.zib.de/instance_details_ej.html
     * <ul>
     * Mac Pro (Early 2009)
     * <li>2019-01-28: 900s terminated without finding any feasible solution</li>
     * </ul>
     * MacBook Pro (16-inch, 2019)
     * <ul>
     * <li>2022-03-08: CPLEX not able to find solution within 5min
     * </ul>
     */
    @Test
    @Tag("slow")
    public void testEj() {
        MipCase.doTest("ej.mps", "25508", null);
    }

    /**
     * https://miplib.zib.de/instance_details_gen-ip002.html
     * <ul>
     * Mac Pro (Early 2009)
     * <li>2019-01-28: 300s expected: <-4783.733392> but was: <-4778.1844607></li>
     * </ul>
     * MacBook Pro (16-inch, 2019)
     * <ul>
     * <li>2022-03-08: CPLEX not able to find solution within 5min
     * </ul>
     */
    @Test
    @Tag("slow")
    public void testGen_ip002() {
        MipCase.doTest("gen-ip002.mps", "-4783.733392", null);
    }

    /**
     * https://miplib.zib.de/instance_details_gen-ip021.html
     * <ul>
     * Mac Pro (Early 2009)
     * <li>2019-01-28: 300s expected: <2361.45419519> but was: <2362.7631500641996></li>
     * </ul>
     * MacBook Pro (16-inch, 2019)
     * <ul>
     * <li>2022-03-08: CPLEX verified solution in 165.6s
     * <li>2022-03-08: ojAlgo returned 2361.4541951915994 after 5min
     * </ul>
     */
    @Test
    @Tag("unstable")
    public void testGen_ip021() {
        MipCase.doTest("gen-ip021.mps", "2361.45419519", null);
    }

    /**
     * https://miplib.zib.de/instance_details_gen-ip036.html
     * <ul>
     * Mac Pro (Early 2009)
     * <li>2019-01-28: 300s expected: <-4606.67961> but was: <-4602.60643892></li>
     * </ul>
     * MacBook Pro (16-inch, 2019)
     * <ul>
     * <li>2022-03-08: CPLEX verified solution in 193.1s
     * <li>2022-03-08: ojAlgo returned -4598.0754963044 after 5min
     * </ul>
     */
    @Test
    @Tag("unstable")
    public void testGen_ip036() {
        MipCase.doTest("gen-ip036.mps", "-4606.67961", null);
    }

    /**
     * https://miplib.zib.de/instance_details_gen-ip054.html
     * <ul>
     * Mac Pro (Early 2009)
     * <li>2019-01-28: 300s expected: <6840.966> but was: <6852.1883509></li>
     * </ul>
     * MacBook Pro (16-inch, 2019)
     * <ul>
     * <li>2022-03-08: CPLEX not able to find solution within 5min
     * </ul>
     */
    @Test
    @Tag("slow")
    public void testGen_ip054() {
        MipCase.doTest("gen-ip054.mps", "6840.966", null);
    }

    @Test
    @Tag("unstable")
    public void testMad() {
        MipCase.doTest("mad.mps", "0.0268", null);
    }

    /**
     * https://miplib.zib.de/instance_details_markshare_5_0.html
     * <ul>
     * Mac Pro (Early 2009)
     * <li>2013-04-01: (suffice=4h abort=8h) Stopped with optimal solution after 4h</li>
     * <li>2018-01-08: (suffice=5min abort=1h) Stopped with integer solution 2.0 rather than 1.0 after
     * 5min</li>
     * <li>2018-02-07: (suffice=5min, abort=15min, mip_gap=0.001) Sufficed with optimal solution</li>
     * <li>2018-08-16: sufficed with optimal solution</li>
     * <li>2019-01-28: 300s expected: <1.0> but was: <1.9999999999999953></li>
     * </ul>
     * MacBook Pro (16-inch, 2019)
     * <ul>
     * <li>2022-03-08: CPLEX not able to find solution within 5min
     * </ul>
     */
    @Test
    @Tag("slow")
    public void testMarkshare_5_0() {
        MipCase.doTest("markshare_5_0.mps", "1.00000000e+00", null);
    }

    /**
     * https://miplib.zib.de/instance_details_markshare1.html
     * <ul>
     * Mac Pro (Early 2009)
     * <li>2013-04-01: (suffice=4h abort=8h) Stopped with integer solution 6.0 rather than 1.0 after 4h
     * expected:<1.0> but was:<5.999999999999929></li>
     * <li>2018-01-08: (suffice=5min abort=1h) Stopped with integer solution 5.0 rather than 1.0 after
     * 5min</li>
     * <li>2018-02-07: (suffice=5min, abort=15min, mip_gap=0.001) Sufficed with expected:<1.0> but
     * was:<5.0></li>
     * <li>2018-08-16: sufficed: <1.0> but was: <8.0></li>
     * <li>2019-01-28: 300s expected: <1.0> but was: <6.000000000000018></li>
     * </ul>
     * MacBook Pro (16-inch, 2019)
     * <ul>
     * <li>2022-03-08: CPLEX not able to find solution within 5min
     * </ul>
     */
    @Test
    @Tag("slow")
    public void testMarkshare1() {
        MipCase.doTest("markshare1.mps", "1.00000000e+00", null);
    }

    /**
     * <ul>
     * <li>2013-04-01: MacPro (suffice=4h abort=8h) Stopped with integer solution 14.0 rather than 1.0
     * solution after 4h expected:<1.0> but was:<14.000000000000192></li>
     * <li>2018-01-08: MacPro (suffice=5min abort=1h) Stopped with integer solution 19.0 rather than 1.0 after
     * 5min</li>
     * <li>2018-02-07: MacPro (suffice=5min, abort=15min, mip_gap=0.001) Sufficed with expected:<1.0> but
     * was:<19.00000000000008></li>
     * <li>2018-08-16: MacPro sufficed: <1.0> but was: <14.0></li>
     * <li>2019-01-28: MacPro sufficed 300s - expected: <1.0> but was: <16.00000000000005></li>
     * </ul>
     * MacBook Pro (16-inch, 2019)
     * <ul>
     * <li>2022-03-08: CPLEX not able to find solution within 5min
     * </ul>
     *
     * @see RelaxedMIPCase#testMarkshare2()
     */
    @Tag("slow")
    @Test
    public void testMarkshare2() {
        MipCase.doTest("markshare2.mps", "1", null);
    }

    /**
     * <ul>
     * <li>2012-11-30: Solved to optimality 40005.05414200003. Don't now how fast, but within 3 hours.</li>
     * <li>2013-03-28: (MacPro) Had a time limit of 1h and that resulted in 40116.054142000015 rather than
     * 40005.0541</li>
     * <li>2013-04-01: MacPro (suffice=4h abort=8h) Stopped with optimal integer solution after 4h</li>
     * <li>2013-12-10: MacPro (suffice=4h abort=8h) Stopped with optimal integer solution after 29min</li>
     * <li>2015-08-28: MacPro (suffice=4h abort=8h) Stopped with optimal integer solution after 97min</li>
     * <li>2015-11-07: MacPro (suffice=4h abort=8h) Stopped with optimal integer solution after 107min</li>
     * <li>2018-01-08: MacPro (suffice=5min abort=1h) Stopped with optimal integer solution after 5min</li>
     * <li>2018-02-07: MacPro (suffice=5min, abort=15min, mip_gap=0.001) Sufficed with optimal solution</li>
     * <li>2018-08-16: MacPro sufficed with optimal solution</li>
     * <li>2019-01-28: MacPro sufficed with optimal solution after 300s</li>
     * </ul>
     * MacBook Pro (16-inch, 2019)
     * <ul>
     * <li>2022-03-08: CPLEX verified solution in 10.0s
     * <li>2022-03-08: ojAlgo ran full 5min, but optimal solution found after about 20s
     * </ul>
     *
     * @see RelaxedMIPCase#testMas76()
     */
    @Test
    @Tag("unstable")
    public void testMas76() {
        MipCase.doTest("mas76.mps", "4.00050541e+04", null);
    }

    /**
     * <ul>
     * <li>2013-04-01: MacPro (suffice=4h abort=8h) Stopped after 4h expected:<2.07405081E7> but was:
     * <2.2769621121583242E7></li>
     * <li>2018-01-08: MacPro (suffice=5min abort=1h) Stopped with integer solution 2.626301334355273E7 rather
     * than 2.07405081E7 after 5min</li>
     * <li>2018-02-07: MacPro (suffice=5min, abort=15min, mip_gap=0.001) Sufficed with expected:<2.07405081E7>
     * but was:<2.45509922222574E7></li>
     * <li>2018-08-16: MacPro sufficed: <2.07405081E7> but was: <2.4337382015089516E7></li>
     * <li>2019-01-28: MacPro sufficed 300s - expected: <2.07405081E7> but was: <2.4548449266857613E7></li>
     * </ul>
     * MacBook Pro (16-inch, 2019)
     * <ul>
     * <li>2022-03-08: CPLEX verified solution in 0.3s
     * <li>2022-03-08: ojAlgo returned 2.158101453372183E7 after 5min
     * </ul>
     *
     * @see RelaxedMIPCase#testModglob()
     */
    @Test
    @Tag("unstable")
    public void testModglob() {
        MipCase.doTest("modglob.mps", "2.07405081e+07", null);
    }

    /**
     * <ul>
     * <li>2015-02-08: MacPro (suffice=4h abort=8h) Stopped after 4.75h: expected:<54.76> but was:<220.69>
     * </li>
     * <li>2018-02-07: MacPro (suffice=5min, abort=15min, mip_gap=0.001) Aborted with no integer solution</li>
     * <li>2018-08-16: MacPro aborted with no integer solution</li>
     * <li>2019-01-28: MacPro aborted with no integer solution 900s</li>
     * </ul>
     * MacBook Pro (16-inch, 2019)
     * <ul>
     * <li>2022-03-08: CPLEX not able to find solution within 5min
     * <li>2022-03-08: ojAlgo returned 78.36 after 5min
     * </ul>
     *
     * @see RelaxedMIPCase#testNeos911880()
     */
    @Test
    @Tag("slow")
    @Tag("unstable")
    public void testNeos911880() {
        MipCase.doTest("neos-911880.mps", "54.76", null);
    }

    /**
     * https://github.com/optimatika/ojAlgo/issues/120
     * <ul>
     * <li>2013-04-01: MacPro (suffice=4h abort=8h) Stopped with optimal integer solution after 4h</li>
     * <li>2013-11-29: MacPro (suffice=4h abort=8h) Stopped after 4h: expected:<-41.0> but was:<-40.0></li>
     * <li>2018-01-08: MacPro (suffice=5min abort=1h) Stopped with optimal integer solution after 5min</li>
     * <li>2018-02-07: MacPro (suffice=5min, abort=15min, mip_gap=0.001) Sufficed with optimal solution</li>
     * <li>2018-08-16: MacPro sufficed with optimal solution</li>
     * <li>2019-01-28: MacPro sufficed 300s - expected: <-41.0> but was: <-40.0></li>
     * </ul>
     * MacBook Pro (16-inch, 2019)
     * <ul>
     * <li>2022-03-08: CPLEX verified solution in 17.1s
     * <li>2022-03-08: ojAlgo returned -14 after 7.3s (Wrong solution!)
     * </ul>
     *
     * @see RelaxedMIPCase#testNoswot()
     */
    @Test
    @Tag("unstable")
    public void testNoswot() {
        MipCase.doTest("noswot.mps", "-4.10000000e+01", null);
    }

    /**
     * <ul>
     * <li>2013-04-01: MacPro (suffice=4h abort=8h) Stopped after 4h expected:<7350.0> but was:<9180.0></li>
     * <li>2013-11-30: MacPro (suffice=4h abort=8h) Stopped after 4h expected:<7350.0> but was:<8020.0></li>
     * <li>2018-02-07: MacPro (suffice=5min, abort=15min, mip_gap=0.001) Sufficed with expected:<7350.0> but
     * was:<7580.0></li>
     * <li>2018-08-16: MacPro sufficed: <7350.0> but was: <7490.0></li>
     * <li>2019-01-28: MacPro sufficed 300s: expected: <7350.0> but was: <7490.0></li>
     * </ul>
     * MacBook Pro (16-inch, 2019)
     * <ul>
     * <li>2022-03-08: CPLEX verified solution in 0.5s
     * <li>2022-03-08: ojAlgo returned 7780.0 after 5min
     * </ul>
     *
     * @see RelaxedMIPCase#testPp08a()
     */
    @Test
    @Tag("unstable")
    public void testPp08a() {
        MipCase.doTest("pp08a.mps", "7.35000000e+03", null);
    }

    /**
     * <ul>
     * <li>2013-04-01: MacPro (suffice=4h abort=8h) Stopped after 4h expected:<7350.0> but was:<8080.0></li>
     * <li>2018-02-07: MacPro (suffice=5min, abort=15min, mip_gap=0.001) Sufficed with expected:<7350.0> but
     * was:<7500.0></li>
     * <li>2018-08-16: MacPro sufficed: <7350.0> but was: <7580.0></li>
     * <li>2019-01-28: MacPro sufficed 300s: expected: <7350.0> but was: <7550.000000000001></li>
     * </ul>
     * MacBook Pro (16-inch, 2019)
     * <ul>
     * <li>2022-03-08: CPLEX verified solution in 0.6s
     * <li>2022-03-08: ojAlgo returned 7430 after 5min
     * </ul>
     *
     * @see RelaxedMIPCase#testPp08aCUTS()
     */
    @Test
    @Tag("unstable")
    public void testPp08aCUTS() {
        MipCase.doTest("pp08aCUTS.mps", "7.35000000e+03", null);
    }

    /**
     * <ul>
     * <li>2013-11-24: MacPro (suffice=4h abort=8h) Stopped after 4h expected:<764772.0> but was:
     * <1012900.999999></li>
     * <li>2018-02-07: MacPro (suffice=5min, abort=15min, mip_gap=0.001) Aborted with no integer solution</li>
     * <li>2018-08-16: MacPro sufficed: <764772.0> but was: <1256281.99999962></li>
     * <li>2019-01-28: MacPro sufficed 300s: expected: <764772.0> but was: <1256281.99999962></li>
     * </ul>
     * MacBook Pro (16-inch, 2019)
     * <ul>
     * <li>2022-03-08: CPLEX verified solution in 82.0s
     * <li>2022-03-08: ojAlgo no integer solution within 15min
     * </ul>
     *
     * @see RelaxedMIPCase#testTimtab1()
     */
    @Test
    @Tag("unstable")
    public void testTimtab1() {
        MipCase.doTest("timtab1.mps", "7.64772000e+05", null);
    }

    /**
     * <ul>
     * <li>2013-04-01: MacPro (suffice=4h abort=8h) Stopped after 4h expected:<13.75> but was:<20.25></li>
     * <li>2013-11-24: MacPro (suffice=4h abort=8h) Stopped after 4h expected:<13.75> but was:<16.5></li>
     * <li>2018-01-08: MacPro (suffice=5min abort=1h) Stopped after 5min expected:<13.75> but was:<16.25></li>
     * <li>2018-02-07: MacPro (suffice=5min, abort=15min, mip_gap=0.001) Sufficed with expected:<13.75> but
     * was:<16.0></li>
     * <li>2018-08-16: MacPro sufficed: <13.75> but was: <16.5></li>
     * <li>2019-01-28: MacPro sufficed 300s: <13.75> but was: <16.5></li>
     * </ul>
     * MacBook Pro (16-inch, 2019)
     * <ul>
     * <li>2022-03-08: CPLEX verified solution in 0.2s
     * <li>2022-03-08: ojAlgo returned 14.5 after 5min
     * </ul>
     *
     * @see RelaxedMIPCase#testVpm2()
     */
    @Test
    @Tag("unstable")
    public void testVpm2() {
        MipCase.doTest("vpm2.mps", "1.37500000e+01", null);
    }

}
