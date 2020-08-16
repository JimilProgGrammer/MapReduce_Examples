package com.mapreduce.examples.numericsummerization;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Combiner class that implements the combine phase
 * and emits {key, value} pairs of
 * {marital_status, (sum, count)} on the
 * map outputs.
 *
 * @author jimil
 */
public class Combine extends Reducer<Text, NumPair, Text, NumPair> {

    /**
     * Custom combiner logic that takes in {text, numpair}
     * and emits {text, numpair} values.
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
        // For all NumPair objects for a key, find out sum and count
        for(NumPair value: values) {
            sum += value.getFirst().get();
            count += value.getSecond().get();
        }
        // write out <text,numpair> for each key for each partition
        context.write(key, new NumPair(sum, count));
    }
}
