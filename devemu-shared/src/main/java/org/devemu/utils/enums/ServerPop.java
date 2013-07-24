package org.devemu.utils.enums;

public enum ServerPop {
	RECOMMENDED((byte)0),
    AVERAGE((byte)1),
    HIGH((byte)2),
    LOW((byte)3),
    FULL((byte)4);

    private final byte population;

    private ServerPop(byte pop) {
        population = pop;
    }

    public static ServerPop get(int pop) {
        if (pop == 1)
            return AVERAGE;
        else if (pop == 2)
            return HIGH;
        else if (pop == 3)
            return LOW;
        else if (pop == 4)
            return FULL;
        return RECOMMENDED;
    }

    public byte getPopulation() {
        return population;
    }
}
