package me.mikex86.scalargrad;

import me.mikex86.scalargrad.nn.Linear;
import me.mikex86.scalargrad.nn.Module;
import me.mikex86.scalargrad.nn.Sigmoid;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class SimpleNNTest {

    public static void main(String[] args) {

        class BobNet implements Module {

            private final Sigmoid act = new Sigmoid();
            private final Linear fc1 = new Linear(1, 1);
            private final Linear fc2 = new Linear(1, 1);

            Value[][] forward(Value[][] x) {
                Value[][] h;
                h = fc1.forward(x);
                h = act.forward(h);
                h = fc2.forward(h);
                return h;
            }

            @Override
            public List<Value> getParameters() {
                return Stream
                        .concat(fc1.getParameters().stream(), fc2.getParameters().stream())
                        .toList();
            }
        }

        BobNet bobNet = new BobNet();

        Random random = new Random(123);

        int batchSize = 32;

        // training loop
        for (int step = 0; step < 4_000; step++) {
            Value[][] x = new Value[batchSize][1];
            Value[][] y = new Value[batchSize][1];
            for (int i = 0; i < batchSize; i++) {
                // f(x) = 2 * x^2 + 0.5
                float xVal = random.nextFloat();
                float yVal = 2 * (xVal * xVal) + 0.5f;
                x[i][0] = new Value(xVal);
                y[i][0] = new Value(yVal);
            }
            Value[][] yHat = bobNet.forward(x);
            Value loss = yHat[0][0].minus(y[0][0]).pow(new Value(2));
            loss.backward();

            if (step % 100 == 0) {
                System.out.println("step = " + step + ", loss = " + loss.getValue());
            }

            // sgd
            for (Value parameter : bobNet.getParameters()) {
                parameter.setValue(parameter.getValue() - 0.1 * parameter.getGrad());
                parameter.zeroGrad();
            }
        }

        // test
        Value[][] x = new Value[][]{{new Value(0.13)}};
        Value[][] yHat = bobNet.forward(x);
        System.out.println("yHat: " + yHat[0][0].getValue() + " (expected: 0.5169)");
    }
}
