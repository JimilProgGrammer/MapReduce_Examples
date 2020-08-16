package com.mapreduce.examples.topn;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Driver class to run topN task
 *
 * @author jimil
 */
public class Main extends Configured implements Tool {

    /**
     * Instantiate and configure a task, submit it
     * to Hadoop for running.
     *
     * @param strings
     * @return
     * @throws Exception
     */
    @Override
    public int run(String[] strings) throws Exception {
        Job job = Job.getInstance(getConf());
        job.setJobName("Get TopN");
        job.setJarByClass(Main.class);

        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        Path inputFile = new Path("D:/MapReduce_Pluralsight/data/input/socialnetwork.txt");
        Path outputFile = new Path("D:/MapReduce_Pluralsight/data/topn_output");

        FileInputFormat.addInputPath(job, inputFile);
        FileOutputFormat.setOutputPath(job, outputFile);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    /**
     * Driver function for topN task.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new Main(), args);
        System.exit(exitCode);
    }
}
