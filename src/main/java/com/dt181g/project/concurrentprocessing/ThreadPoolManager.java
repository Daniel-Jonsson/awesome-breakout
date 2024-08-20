    package com.dt181g.project.concurrentprocessing;

    import com.dt181g.project.models.powerup.BasePowerup;
    import com.dt181g.project.support.Appconfig;

    import java.util.concurrent.ThreadPoolExecutor;
    import java.util.concurrent.BlockingQueue;
    import java.util.concurrent.LinkedBlockingQueue;
    import java.util.concurrent.TimeUnit;
    import java.util.concurrent.Future;
    import java.util.concurrent.Callable;

    /**
     * The {@code ThreadPoolManager} class is responsible for managing different
     * thread pools within the application. It provides the ability to execute
     * producer tasks, consumer tasks, and sound tasks.
     * @author Daniel Jönsson
     */
    public final class ThreadPoolManager {

        /**
         * Different thread pools.
         */
        private final ThreadPoolExecutor producerThreadPool;
        private final ThreadPoolExecutor consumerThreadPool;
        private final ThreadPoolExecutor soundThreadPool;

        /**
         * Constructs a ThreadPoolManager with three different ThreadPoolExecutor
         * instances: producerThreadPool, consumerThreadPool, and soundThreadPool
         * . These pools have different purposes such as handling creation of
         * powerups, consumption of powerups and handling of sound.
         */
        public ThreadPoolManager(){
            BlockingQueue<Runnable> producerTaskQueue = new LinkedBlockingQueue<>();
            BlockingQueue<Runnable> consumerTaskQueue = new LinkedBlockingQueue<>();
            BlockingQueue<Runnable> soundTaskQueue = new LinkedBlockingQueue<>();
            this.producerThreadPool = new ThreadPoolExecutor(Appconfig.THREAD_POOL_MIN_SIZE,
                    Appconfig.THREAD_POOL_MAX_SIZE,
                    1, TimeUnit.SECONDS, producerTaskQueue);
            this.consumerThreadPool = new ThreadPoolExecutor(Appconfig.THREAD_POOL_MIN_SIZE,
                    Appconfig.THREAD_POOL_MAX_SIZE,
                    1, TimeUnit.SECONDS, consumerTaskQueue);
            this.soundThreadPool = new ThreadPoolExecutor(Appconfig.THREAD_POOL_MIN_SIZE,
                            Appconfig.THREAD_POOL_MAX_SIZE, 1,
                    TimeUnit.SECONDS, soundTaskQueue);
        }

        /**
         * Executes a given runnable command in the producerThreadPool. Used to
         * produce powerups to the powerupPool.
         * @param command the runnable task to be executed
         */
        public void execute(final Runnable command){
            this.producerThreadPool.submit(command);
        }

        /**
         * Executes a given runnable command in the soundThreadPool, used to play
         * sounds on different threads to offload the EDT.
         * @param command the runnable task to be executed
         */
        public void executeSound(final Runnable command){
            this.soundThreadPool.submit(command);
        }

        /**
         * Executes a given callable command in the consumerThreadPool and
         * returns the Future result(a powerup) of the task.
         * @param command the callable task to be executed
         * @return a Future representing the pending result of the task
         */
        public Future<BasePowerup> execute(final Callable<BasePowerup> command){
            return this.consumerThreadPool.submit(command);
        }
    }
