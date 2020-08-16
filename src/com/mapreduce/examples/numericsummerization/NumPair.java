package com.mapreduce.examples.numericsummerization;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Custom WritableComparable to represent
 * tuple information which is modelled as:
 * (DoubleWritable sum, IntWritable count)
 *
 *  - sum specifies the total number of hours
 *      individuals belonging to each category have
 *      worked
 *  - count specifies the total number of
 *      individuals belonging to that category that
 *      have worked for "sum" hours
 *
 * @author jimil
 */
public class NumPair implements WritableComparable<NumPair> {

    // Total number of hours
    private DoubleWritable first;
    // count
    private IntWritable second;

    /**
     * Default constructor
     */
    public NumPair() {
        set(new DoubleWritable(), new IntWritable());
    }

    /**
     * Parameterized constructor 1
     *
     * @param first
     * @param second
     */
    public NumPair(Double first, Integer second) {
        set(new DoubleWritable(first), new IntWritable(second));
    }

    /**
     * Parameterized constructor 2
     *
     * @param first
     * @param second
     */
    public NumPair(DoubleWritable first, IntWritable second) {
        set(first, second);
    }

    /**
     * Generic set method to set values of both
     * data members
     *
     * @param first
     * @param second
     */
    private void set(DoubleWritable first, IntWritable second) {
        this.first = first;
        this.second = second;
    }

    /**
     * get value of sum
     *
     * @return
     */
    public DoubleWritable getFirst() {
        return this.first;
    }

    /**
     * get value of count
     *
     * @return
     */
    public IntWritable getSecond() {
        return this.second;
    }

    /**
     * Delegate write task to each member since they
     * are WritableComparables themselves
     *
     * @param dataOutput
     * @throws IOException
     */
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        first.write(dataOutput);
        second.write(dataOutput);
    }

    /**
     * Delegate read tasks to each member as well
     *
     * @param dataInput
     * @throws IOException
     */
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        first.readFields(dataInput);
        second.readFields(dataInput);
    }

    /**
     * Sort by total number of hours, if equal
     * then sort by count
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(NumPair o) {
        int cmp = first.compareTo(o.getFirst());
        if(cmp != 0) {
            return cmp;
        }
        return second.compareTo(o.getSecond());
    }

    /**
     * Common practise to override hashcode if
     * you are overriding equals since we want to
     * ensure that two equal objects (as per
     * our overriden criteria) hash to the
     * same bucket.
     *
     * @return
     */
    @Override
    public int hashCode() {
        return first.hashCode()*163 + second.hashCode();
    }

    /**
     * Return true if total number of hours and sum equals,
     * otherwise return false
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof NumPair) {
            NumPair numPair = (NumPair) obj;
            return first.equals(numPair.first) && second.equals(numPair.second);
        }
        return false;
    }

    @Override
    public String toString() {
        return first + "\t" + second;
    }
}
