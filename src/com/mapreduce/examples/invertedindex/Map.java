package com.mapreduce.examples.invertedindex;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;
import java.util.StringTokenizer;

public class Map extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        // Using context to access the current file we are processing
        // The InputSplit represents one partition of data that is present
        // on one mapper node
        FileSplit currentSplit = ((FileSplit) context.getInputSplit());

        // Extract current filename
        String filenameStr = currentSplit.getPath().getName();
        Text filename = new Text(filenameStr);

        // Split one line of input into words
        String line = value.toString();
        StringTokenizer tokenizer = new StringTokenizer(line);

        // Iterate over all the words present in one file
        // and write out the word along with the filename
        Text word = new Text();
        while(tokenizer.hasMoreTokens()) {
            word.set(tokenizer.nextToken());
            context.write(word, filename);
        }
    }
}
