package alpha.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.*;

// Created by: Guacamoleboy
// ________________________
// Last updated: 20/02-2026
// By: Guacamoleboy

public class PoolLog extends ThreadPoolExecutor {

    // Attributes
    private static final Logger LOGGER = LoggerFactory.getLogger(PoolLog.class);

    // _________________________________________________________________________________________________________________
    // BlockingQueue<Runnable> is used to try and make it as generic as possible. We can use any BlockingQueue on it
    // as long as it follows the principles of it being a BlockingQueue<>.
    // Not to mention we extend ThreadPoolExecutor who demand it in super() call.

    public PoolLog(int minThreads, int maxThreads, long idleTimeout, TimeUnit unit, BlockingQueue<Runnable> taskQueue) {
        super(minThreads, maxThreads, idleTimeout, unit, taskQueue);
    }

    // _________________________________________________________________________________________________________________

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);

        // Async call exception tracker validation
        if (t == null && r instanceof Future<?>) {
            try {
                // Typecast r to Future<?> as r is only a Runnable by default
                Future<?> future = (Future<?>) r;
                // isDone() != success. It just means the Thread has finished running.
                // By using get() on it we can force the exception to be shown. Else it would go by
                // unnoticed which isn't optimal for debugging or fixing issues.
                // If no exception is hidden and the Thread wasn't a fail we simply skip the exception checker.
                if (future.isDone()) future.get();
            } catch (ExecutionException ee) {
                // get() exception catch where getCause() gets the specific issue
                t = ee.getCause();
            } catch (InterruptedException ie) {
                // See PoolConfig.java for explanation
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                // Default / All other exceptions
                t = e;
            }
        }

        // Log the error if one was found
        if (t != null) {
            LOGGER.error("Task execution failed: {}", t.getMessage(), t);
        }

        // Setup for Warning Logs
        int active = getActiveCount();
        int max = getMaximumPoolSize();
        int queued = getQueue().size();
        int remaining = getQueue().remainingCapacity();

        /* // Track all calls
        if (active >= max || queued > 0) {
            LOGGER.warn("ThreadPool pressure | Active: {}/{} | Queued: {} | Free space in queue: {}",
                    active, max, queued, remaining);
        }
        */

        // Pressure detected Warning Log
        if (active >= max || queued > 0) {
            LOGGER.warn("ThreadPool pressure detected | Active: {}/{} | Queue: {}/{}",
                    active, max, queued, (queued + remaining));
        }

        // LinkedBlockingQueue remaining spots till max capacity Warning Log.
        if (remaining < 10) {
            LOGGER.error("CRITICAL: Queue almost full! Only {} slots remaining!", remaining);
        }

    }

}