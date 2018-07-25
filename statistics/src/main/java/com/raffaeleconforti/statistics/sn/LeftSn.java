package com.raffaeleconforti.statistics.sn;

import com.raffaeleconforti.statistics.StatisticsMeasureAbstract;
import com.raffaeleconforti.statistics.median.Median;
import org.eclipse.collections.impl.list.mutable.primitive.DoubleArrayList;

import java.util.Arrays;

/**
 * Created by Raffaele Conforti (conforti.raffaele@gmail.com) on 22/11/16.
 */
public class LeftSn extends StatisticsMeasureAbstract {

    private Median median = new Median();

    @Override
    public double evaluate(Double val, double... values) {
        try {
            values = Arrays.copyOf(values, values.length);
            Arrays.sort(values);

            DoubleArrayList v = new DoubleArrayList();
            for(int i = 0; i < values.length && values[i] <= val; i++) {
                DoubleArrayList v1 = new DoubleArrayList();
                for(int j = 0; j < values.length && values[j] <= val; j++) {
                    v1.add(Math.abs(values[i] - values[j]));
                }
                v.add(median.evaluate(null, v1.toArray()));
            }
            return 1.1925 * median.evaluate(null, v.toArray());
        }catch (ArrayIndexOutOfBoundsException e) {

        }
        return 0;
    }

}
