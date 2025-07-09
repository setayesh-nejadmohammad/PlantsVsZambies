package game.plantsvszambies;

import game.plantsvszambies.Zombie;

import java.util.Random;

public class ZombieFactory {
    private static final Random random = new Random();

    public static Zombie createZombie(ZombieType type, int row) {
        return switch (type) {
            case NORMAL -> new NormalZombie(row);
            case CONEHEAD -> new ConeheadZombie(row);
            case SCREEN_DOOR -> new ScreenDoorZombie(row);
            case IMP -> new ImpZombie(row);
        };
    }

    public static Zombie createRandomZombie(int currentPhase, int row) {
        ZombieType[] availableTypes = switch (currentPhase) {
            case 1 -> new ZombieType[]{ZombieType.NORMAL};
            case 2 -> new ZombieType[]{ZombieType.NORMAL, ZombieType.CONEHEAD};
            case 3 -> new ZombieType[]{ZombieType.NORMAL, ZombieType.CONEHEAD, ZombieType.SCREEN_DOOR};
            case 4 -> ZombieType.values();
            default -> throw new IllegalArgumentException("Invalid phase");
        };

        return createZombie(availableTypes[random.nextInt(availableTypes.length)], row);
    }

    public enum ZombieType { NORMAL, CONEHEAD, SCREEN_DOOR, IMP }
}