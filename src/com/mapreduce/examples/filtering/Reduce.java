package com.mapreduce.examples.filtering;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Custom Reducer implementation for conditional
 * filtering.
 *
 * @author jimil
 */
public class Reduce extends Reducer<NullWritable, Text, NullWritable, Text> {

    /**
     * The Reducer is an identity function that simply
     * writes it's input to the result file.
     *
     * @param key
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(NullWritable key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        for(Text value: values) {
            context.write(NullWritable.get(), value);
        }
    }
}
