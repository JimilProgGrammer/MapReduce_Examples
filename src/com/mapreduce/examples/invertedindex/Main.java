package com.mapreduce.examples.invertedindex;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class Main extends Configured implements Tool {

    @Override
    public int run(String[] strings) throws Exception {
        Job job = Job.getInstance(getConf());
        job.setJobName("Building Inverted Index");
        job.setJarByClass(Main.class);

        // Configuration flag to indicate that a custom separator
        // is to be used in the output file and not the default tab.
        job.getConfiguration().set(
                "mapreduce.output.textoutputformat.separator", " | "
        );

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(Map.class);
        job.setCombinerClass(Reduce.class);
        job.setReducerClass(Reduce.class);

        Path inputFile = new Path("D:/MapReduce_Pluralsight/src");
        Path outputFile = new Path("D:/MapReduce_Pluralsight/data/index_output");

        FileInputFormat.addInputPath(job, inputFile);
        FileOutputFormat.setOutputPath(job, outputFile);
        // We want our job to check sub-directories recursively
        // under the input directory
        FileInputFormat.setInputDirRecursive(job, true);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new Main(), args);
        System.exit(exitCode);
    }
}
