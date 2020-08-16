package com.mapreduce.examples.topn;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.PriorityQueue;

/**
 * Custom Mapper to filter out top 3 influential
 * users from the social network
 *
 * @author jimil
 */
public class Map extends Mapper<LongWritable, Text, NullWritable, Text> {

    // element with the highest priority is at the head of the queue
    // elements of the queue must be comparable with each other
    // Element with highest priority is user with least followers in the top N
    private PriorityQueue<User> followersPriorityQueue = new PriorityQueue<>();

    /**
     * Custom map logic that reads in text records and for
     * every text record, if that user has more followers than
     * the user with the least followers in the top 3, add that
     * user to the queue and remove the one at the head of the
     * queue.
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
        String[] data = value.toString().split(",");
        int followers = Integer.parseInt(data[1]);

        // For every record read in from the input file, compare with
        // the user with least number of followers in the current top N
        // This will determine if there is a new top N ranking
        User user = followersPriorityQueue.peek();

        if(followersPriorityQueue.size() <= 3 || followers > user.getFollowers()) {
            followersPriorityQueue.add(new User(followers, new Text(value)));

            if(followersPriorityQueue.size() > 3) {
                followersPriorityQueue.poll();
            }
        }
    }

    /**
     * called once at the end of map task on
     * each node; in case of topN, you write out
     * intermediate results only once the entire
     * map task is complete and one subset of data
     * has been completely processed.
     *
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void cleanup(Context context)
            throws IOException, InterruptedException {
        while(!followersPriorityQueue.isEmpty()) {
            context.write(NullWritable.get(), followersPriorityQueue.poll().getRecord());
        }
    }
}
