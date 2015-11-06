

import java.util.Map;


public interface FilesWordCounter {

    static interface Callback {

        void counted(String fileName, long count);

    }

    /**
     * Counts {@code word} within each of {@code files} at the {@code concurrencyFactor} provided
     * and for each result (asynchronously) invokes {@linkplain Callback#counted(String, long)}
     * on the {@code callback} object
     * @param files to count
     * @param word to count within {@code files}
     * @param callback on which to alert of the counted file as soon as it is known
     */
    void count(Map<String, String> files, String word, Callback callback);

    /**
     * Called to indicate that all threads/executors should be shutdown and ended.
     */
    void stop();
}
