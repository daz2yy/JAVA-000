package gateway.router;

import java.util.Random;

public class RandomRouter {
    private final Random random;

    public RandomRouter() {
        this.random = new Random();
    }

    public String getRandomServer(String[] servers) {
        int num = random.nextInt() % (servers.length - 1);
        return servers[num];
    }
}
