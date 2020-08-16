package com.mapreduce.examples.numericsummerization;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Mapper class to implement the map process
 * that emits {key,value} pairs of
 * {marital_status, hours/week_working} on the
 * US census data.
 *
 * @author jimil
 */
public class Map extends Mapper<LongWritable, Text, Text, NumPair> {

    /**
     * Custom map implementation that receives one line
     * from the text file; Need to extract key and value
     * pairs from the input line and write the desired
     * {key,value} pair to the intermediate output file
     * using the context.
     *
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // Get the line of text
        String line = value.toString();
        // Tokenize the input line
        String[] data = line.split(",");

        try {
            // Extract marital status
            String maritalStatus = data[5];
            // Extract number of working hours per week
            Double hrs = Double.parseDouble(data[12]);
            // Write {marital_status,working_hours} pair to the intermediate output file
            context.write(new Text(maritalStatus), new NumPair(hrs, 1));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
