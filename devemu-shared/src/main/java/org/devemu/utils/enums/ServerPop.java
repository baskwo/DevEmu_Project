package org.devemu.utils.enums;

public enum ServerPop {
	RECOMMENDED((byte)0),
    AVERAGE((byte)1),
    HIGH((byte)2),
    LOW((byte)3),
    FULL((byte)4);

    private final byte population;

    private ServerPop(byte arg0) {
        population = arg0;
    }

    public static ServerPop get(int arg0) {
        if (arg0 == 1)
            return AVERAGE;
        else if (arg0 == 2)
            return HIGH;
        else if (arg0 == 3)
            return LOW;
        else if (arg0 == 4)
            return FULL;
        return RECOMMENDED;
    }

    public byte getPopulation() {
        return population;
    }
}
