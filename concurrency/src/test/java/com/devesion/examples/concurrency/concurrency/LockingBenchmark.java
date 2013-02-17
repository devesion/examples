/*
 * Copyright (C) 2013 devesion.com
 *
 * Contact: Lukas Dembinski <dembol@devesion.com>
 * Initial developer: Lukas Dembinski <dembol@devesion.com>
 * Contributor(s):
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.devesion.examples.concurrency.concurrency;

import com.google.caliper.Param;
import com.google.caliper.Runner;
import com.google.caliper.SimpleBenchmark;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author dembol
 * @version $Revision$ $Date$
 */
@SuppressWarnings("unused")
public class LockingBenchmark extends SimpleBenchmark {

    @Param({  "1", "2", "4", "8", "16" })
    private int numThreads;

    private static final int LOOPS = 100 * 1000;

    private static final Object guard = new Object();

    private static final Lock lock = new ReentrantLock();

    private static final Semaphore sem = new Semaphore(1);

    private Random r;

    @Override
    protected void setUp() throws Exception {
        r = new Random();
    }

    public void testAlgo(final int reps, final Runnable task) {
        ExecutorService s = Executors.newFixedThreadPool(numThreads);
        for (int t = 0; t < numThreads; t++) {
            s.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < reps; i++) {
                        for (int j = 0; j < LOOPS; j++) {
                            task.run();
                        }
                    }
                }
            });
        }

        s.shutdown();
        while (!s.isTerminated()) {
            try {
                s.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {

            }
        }
    }

    public void timeSynchronized(final int reps) {
        testAlgo(reps, new Runnable() {
            @Override
            public void run() {
                synchronized (guard) {
                    r.nextInt();
                }
            }
        });
    }

    public void timeLock(final int reps) {
        testAlgo(reps, new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    r.nextInt();
                } finally {
                    lock.unlock();
                }
            }
        });
    }

    public void timeSemaphore(final int reps) {
        testAlgo(reps, new Runnable() {
            @Override
            public void run() {
                sem.acquireUninterruptibly();
                try {
                    r.nextInt();
                } finally {
                    sem.release();
                }
            }
        });
    }

    public static void main(String[] args) {
        Runner.main(LockingBenchmark.class, args);
    }
}
