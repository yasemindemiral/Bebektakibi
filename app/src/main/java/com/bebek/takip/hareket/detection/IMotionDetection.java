package com.bebek.takip.hareket.detection;

/**
 * This interface is used to represent a class that can detect motion
 *
 * Created by yasemin on 5/2/14.
 */
public interface IMotionDetection {public int[] getPrevious();

    /**
     * Detect motion.
     * @param data integer array representing an image.
     * @param width Width of the image.
     * @param height Height of the image.
     * @return boolean True is there is motion.
     * @throws NullPointerException if data integer array is NULL.
     */
    public boolean detect(int[] data, int width, int height);
}

