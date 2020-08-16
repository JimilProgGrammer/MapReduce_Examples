package com.mapreduce.examples.numericsummerization;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Reducer class to implement the reduce process
 * that emits {key,value} pairs of
 * {marital_status, AVG hours/week_working} on the
 * US census data.
 *
 * @author jimil
 */
public class Reduce extends Reducer<Text, NumPair, Text, DoubleWritable> {

    /**
     * Custom reduce implementation that receives map output;
     * Receives all values for a particular key -> find average
     * and write the desired {key,value} pair to the output file
     * using the context.
     *
     * @param key
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<NumPair> values, Context context)
            throws IOException, InterruptedException {
        // Initialize variables
        Double sum = 0.0;
        Integer count = 0;
        // Find sum and count
        for(NumPair value: values) {
            sum += value.getFirst().get();
            count += value.getSecond().get();
        }
        // Write average along with key using context
        context.write(key, new DoubleWritable(sum/count));
    }
}
