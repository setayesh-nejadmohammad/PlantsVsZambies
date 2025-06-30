package game.plantsvszambies;

import game.plantsvszambies.Zombie;

import java.util.Random;

public class ZombieFactory {
    private static final Random random = new Random();

    public static Zombie createZombie(ZombieType type, int row, Map map) {
        return switch (type) {
            case NORMAL -> new NormalZombie(row, map);
            case CONEHEAD -> new ConeheadZombie(row, map);
            case SCREEN_DOOR -> new ScreenDoorZombie(row, map);
            case IMP -> new ImpZombie(row, map);
        };
    }

    public static Zombie createRandomZombie(int currentPhase, int row, Map map) {
        ZombieType[] availableTypes = switch (currentPhase) {
            case 1 -> new ZombieType[]{ZombieType.NORMAL};
            case 2 -> new ZombieType[]{ZombieType.NORMAL, ZombieType.CONEHEAD};
            case 3 -> new ZombieType[]{ZombieType.NORMAL, ZombieType.CONEHEAD, ZombieType.SCREEN_DOOR};
            case 4 -> ZombieType.values();
            default -> throw new IllegalArgumentException("Invalid phase");
        };

        return createZombie(availableTypes[random.nextInt(availableTypes.length)], row, map);
    }

    public enum ZombieType { NORMAL, CONEHEAD, SCREEN_DOOR, IMP }
}
