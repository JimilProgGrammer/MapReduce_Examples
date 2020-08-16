package com.mapreduce.examples.numericsummerization;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Driver class to submit the MR job
 * to the Hadoop ecosystem
 *
 * @author jimil
 */
public class Main extends Configured implements Tool {

    /**
     * Custom implementation to setup MR job and
     * submit to ecosystem.
     *
     * @param strings
     * @return
     * @throws Exception
     */
    @Override
    public int run(String[] strings) throws Exception {
        // Initialize new Job, set name and jar
        Job job = Job.getInstance(getConf());
        job.setJobName("Numeric Summarization");
        job.setJarByClass(Main.class);

        // set type of map output, and final output
        // if map output type not specified, assumed
        // it is same as final output
        job.setMapOutputValueClass(NumPair.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        // set mapper, reducer and combiner
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        job.setCombinerClass(Combine.class);

        // set up input file path
        Path inputFilePath = new Path("D:/MapReduce_Pluralsight/data/input/census.txt");
        // set up path to output directory
        // output directory should not exist beforehand
        Path outputFilePath = new Path("D:/MapReduce_Pluralsight/data/output_with_combiner");

        // configure job to read from input path
        FileInputFormat.addInputPath(job, inputFilePath);
        // configure job to write to output path
        FileOutputFormat.setOutputPath(job, outputFilePath);

        // return 0 if job completed else return 1
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        // additionally when using Hadoop with Windows
        System.setProperty("hadoop.home.dir", "c:\\winutils\\");
        // Run Tool job using ToolRunner
        int exitCode = ToolRunner.run(new Main(), args);
        // exit if successful.
        System.exit(exitCode);
    }
}
