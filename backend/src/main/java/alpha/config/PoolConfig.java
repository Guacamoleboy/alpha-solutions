package alpha.config;

import java.net.http.HttpClient;
import java.util.concurrent.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Created by: Guacamoleboy
// ________________________
// Last updated: 20/02-2026
// By: Guacamoleboy

public class PoolConfig {

    // POOL SIZE -> CPU Cores + 1 -> Number
    // static final -> CONSTANT_CASE

    // Attributes
    private static final Logger LOGGER = LoggerFactory.getLogger(PoolConfig.class);

    // Only set to 4 as 9 seems unreasonable for now. If the system is being overloaded our PoolLog
    // will tell us, and we can act upon it. Not to mention we won't Rate Limit external APIs.
    // Too many can trigger a safety response from the external APIs and cause IPs being banned, Rate Limit etc.
    private static final int POOL_SIZE = 4;

    // Set to 100 to avoid long wait times in case queue is full
    // Causes instant RejectedExecutionException that can then be use in UI to tell
    // the user the system is overloaded and that they should try again in a few seconds.
    // If we set it to 500 the task would get added to the queue, and the user would sit and wait
    // for potentially a long time. So by limiting it to what a max (Millisecond) response time should
    // be, we can create a better and more UI friendly system that allows users to know exactly what's
    // going on without thinking their internet is having issues or whatever they might think.
    // Gotta keep in mind the average user doesn't know these things, so we gotta feed them the information.
    private static final int QUEUE_CAPACITY = 100;

    // Bakery Example
    // ______________
    // POOL_SIZE = Available Cashiers
    //
    // LinkedBlockingQueue = Customers in a queue waiting for an available cashier
    //      - <> = <Runnable>. Java knows by default, so no need to write it.
    //      - Same as newFixedThreadPool() but that method has no QUEUE_CAPACITY max
    //
    // POOL_SIZE (1), POOL_SIZE (2)
    //      - (1) = Cashiers always at work
    //      - (2) = Max Cashiers at any given time
    //      - They are the same to create a Fixed Pool Size to ensure stability.
    //
    // 0L
    //      - 0 = 0 extra cashiers can be added if the shop is super busy
    //      - L = Long
    //      - Why 0 and not 10? We're using a 1:1 of POOL_SIZE meaning our shop won't need to add additional
    //      - cashiers in case it gets super busy.
    //
    // TimeUnit.MILLISECONDS belongs with idleTimeout (0L). Just a formatter to tell idleTimeout if it's
    // in Seconds, Hours, Milliseconds and so on.
    //
    // QUEUE_CAPACITY is a safety feature in case the system is being maxed out.
    //      - We have 100 tasks in queue and 4 active customers being helped. This means our System is at fully
    //      - capacity. If we allow any more customers in the shop at this given state of time the shop would cave in.
    //      - To avoid this we set a limit so that we protect against OutOfMemoryError with a
    //      - RejectedExecutionException.

    private static final PoolLog EXECUTOR = new PoolLog(POOL_SIZE, POOL_SIZE, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(QUEUE_CAPACITY));

    // DeserializationFeature
    //      - Read (GET) | API to Java
    //      - FAIL_ON_UNKNOWN_PROPERTIES, false | Preventing crashes in case there's a field we haven't
    //      - specified in our DTO for example.
    //
    // SerializationFeature
    //      - Send (POST) | Java -> Frontend
    //      - INDENT_OUTPUT, true | Pretty formatting by default on POST().
    //      - WRITE_DATES_AS_TIMESTAMPS, false | Write date as String format to JSON
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    // Using HTTP_1_1 for stability in case a system doesn't have HTTP/2 enabled.
    // If we try to HTTP/2 on a HTTP_1_1 we would have to wait for HTTP/2 to fail and degrade to HTTP_1_1
    // so by using HTTP_1_1 by default, we tell the system, that we don't want to try HTTP/2 first.
    private static final HttpClient CLIENT =
    HttpClient.newBuilder()
    .executor(EXECUTOR)
    .version(HttpClient.Version.HTTP_1_1)
    .build();

    // _________________________________________________________________________________________________________________
    // Non-instantiatable for safety | singleton

    private PoolConfig() {
    }

    // _________________________________________________________________________________________________________________

    public static ExecutorService getExecutor() {
        return EXECUTOR;
    }

    // _________________________________________________________________________________________________________________

    public static HttpClient getClient() {
        return CLIENT;
    }

    // _________________________________________________________________________________________________________________

    public static ObjectMapper getMapper() {
        return OBJECT_MAPPER;
    }

    // _________________________________________________________________________________________________________________

    public static void shutdown() {
        LOGGER.info("Shutting down thread pool...");
        // Close for new tasks
        // LinkedBlockingQueue tasks will continue to run till done
        EXECUTOR.shutdown();
        try {
            // 5sec delay for tasks being executed (if any)
            if (!EXECUTOR.awaitTermination(5, TimeUnit.SECONDS)) {
                EXECUTOR.shutdownNow();
                LOGGER.warn("Pool did not terminate in time, forcing shutdown");
            }
        } catch (InterruptedException e) {
            // Worst case -> Shut down instantly
            LOGGER.error("Shutdown was interrupted unexpectedly: {}", e.getMessage());
            EXECUTOR.shutdownNow();

            // Explained in everyday terms
            // ___________________________
            //
            // 1. You're driving a series of cars (Threads). Your boss (System) presses a button that enables
            //    the "STOP DRIVING" lamp in your cars dashboard...
            //
            // 2. You can't just BRAKE and STOP instantly, so you wait for a safe spot to pull over.
            //    (finish current Thread)
            //
            // 3. You're now parked for 5sec waiting for the rest of the cars to park their car aswell...
            //
            // 4. While you're waiting (5sec) you're hit by an unexpected car. Your cars electricity fails
            //    and your "STOP DRIVING" lamp isn't working anymore and you can't remember if it was ON or OFF.
            //
            // 5. Your car has a feature (Thread.currentThread().interrupt()) that re-enables the system as
            //    it was pre-crash.
            //
            // 6. Your "STOP DRIVING" lamp is now ON again, and you are now able to remember where you left off.
            //    from. (awaitTermination)
            //
            // 7. Without (Thread.currentThread().interrupt()) your system wouldn't reboot and your "STOP DRIVING"
            //    lamp would be off. Since you can't remember if it was ON or OFF pre-crash you're just assuming
            //    it was off, so you continue your task (Thread) as it was before which would cause a leak.
            //
            // 8. It's important to handle leaks as they can be resource heavy and use RAM / cause Database Connectivity
            //    issues which is a big problem. So by handling the leak we're able to shut down our program in a
            //    efficient way without causing any subtle or worst yet... unknown leaks.

            Thread.currentThread().interrupt();
        }

    }

}