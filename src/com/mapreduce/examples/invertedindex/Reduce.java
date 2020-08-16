package com.mapreduce.examples.invertedindex;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Reduce extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        // Concatenating strings creates new objects in memory
        // every time and thus is inefficient.
        // Use StringBuilder instead.
        StringBuilder builder = new StringBuilder();

        // Iterate over all URLs for every word and concatenate
        // those URLs into a single string separated by "|"
        for(Text value: values) {
            builder.append(value.toString());
            if(values.iterator().hasNext()) {
                builder.append(" | ");
            }
        }

        // Write out using context
        context.write(key, new Text(builder.toString()));
    }
}
