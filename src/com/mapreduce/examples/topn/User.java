package com.mapreduce.examples.topn;

import org.apache.hadoop.io.Text;

/**
 * Helper class to represent one user record
 * in the map and reduce phases;
 * Objects of this class are used to sort
 * and store on a priority queue.
 *
 * @author jimil
 */
public class User implements Comparable<User> {

    // Number of followers is used to maintain priority queue order
    private int followers;
    // Original record from the input file
    private Text record;

    /**
     * Setting values via constructors
     * at the time of instantiation
     *
     * @param followers
     * @param record
     */
    public User(int followers, Text record) {
        this.followers = followers;
        this.record = record;
    }

    /**
     * Returns the number of followers that this
     * User has
     *
     * @return
     */
    public int getFollowers() {
        return this.followers;
    }

    /**
     * Returns the original text record from input
     * file for this User
     *
     * @return
     */
    public Text getRecord() {
        return this.record;
    }

    /**
     * return:
     *  <0 if current user has less followers than User o
     *  =0 if current user has same followers as User o
     *  >0 if current user has more followers than User o
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(User o) {
        return this.followers - o.followers;
    }
}
