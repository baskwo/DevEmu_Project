package org.devemu.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author Blackrush
 */
public class Stopwatch implements AutoCloseable {
    private final com.google.common.base.Stopwatch stopwatch;
    private final Logger log;
    private final String msg;
    private final TimeUnit unit;

    private Stopwatch(com.google.common.base.Stopwatch stopwatch, Logger log, String msg, TimeUnit unit) {
        this.stopwatch = stopwatch;
        this.log = log;
        this.msg = msg;
        this.unit = unit;
    }

    public static Stopwatch start(Logger log, String msg, TimeUnit unit) {
        return new Stopwatch(new com.google.common.base.Stopwatch().start(), log, msg, unit);
    }

    public static Stopwatch start(String msg, TimeUnit unit) {
        return start(LoggerFactory.getLogger(""), msg, unit);
    }

    public static Stopwatch start(String msg) {
        return start(msg, TimeUnit.MILLISECONDS);
    }

    public static Stopwatch start() {
        return start("it tooks %d milliseconds");
    }

    public static Stopwatch start(Logger log, String msg) {
        return start(log, msg, TimeUnit.MILLISECONDS);
    }

    @Override
    public void close() {
        stopwatch.stop();

        log.info(msg, stopwatch.elapsed(unit));
    }
}
