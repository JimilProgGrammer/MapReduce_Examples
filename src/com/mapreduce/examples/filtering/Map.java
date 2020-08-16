package com.mapreduce.examples.filtering;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Custom Mapper that reads in a tab-separated
 * input file and filters out records for the
 * category = Books.
 * The mapper emits {Null, <Line_From_File>} pairs.
 *
 * @author jimil
 */
public class Map extends Mapper<LongWritable, Text, NullWritable, Text> {

    /**
     * Custom implementation for map phase.
     *
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        // Get line from text file
        String line = value.toString();
        // Split on tab
        String[] data = line.split(",");
        // If criteria satisfied
        if(data[2].equalsIgnoreCase("Books")) {
            // Write out to intermediate output using context
            context.write(NullWritable.get(), value);   // NullWritable has a singleton instance that you can access
                                                        // using the .get() method
        }
    }
}
