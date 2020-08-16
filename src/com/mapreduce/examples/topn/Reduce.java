package com.mapreduce.examples.topn;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.PriorityQueue;

/**
 * Custom reducer that receives localized topN from
 * all mappers; there is a single reducer that aggregates
 * global topN from these localized subsets.
 * All received localized subsets have the same key, so
 * the single reducer can write the output immediately after
 * all items in the Iterable have been processed.
 *
 * @author jimil
 */
public class Reduce extends Reducer<NullWritable, Text, NullWritable, Text> {

    // Same priority queue structure to maintain topN
    private PriorityQueue<User> followersPriorityQueue = new PriorityQueue<>();

    /**
     * Custom reduce implementation that finds out global topN
     * from localized subsets received from each mapper node.
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
            String[] data = value.toString().split(",");
            int followers = Integer.parseInt(data[1]);

            User user = followersPriorityQueue.peek();
            if(followersPriorityQueue.size() <= 3 || followers > user.getFollowers()) {
                followersPriorityQueue.add(new User(followers, new Text(value)));

                if(followersPriorityQueue.size() > 3) {
                    followersPriorityQueue.remove(user);
                }
            }
        }

        /**
         * Write the reduce output at the end of the reduce
         * phase itself since all mappers output the same key
         * and since there is only one reducer, we have the
         * global topN at the end of the one and only reduce
         * itself.
         */
        while(!followersPriorityQueue.isEmpty()) {
            context.write(NullWritable.get(), followersPriorityQueue.poll().getRecord());
        }
    }
}
