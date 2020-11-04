package gateway.router;

import java.util.List;
import java.util.Random;

public class RandomRouter {
    private final Random random;

    public RandomRouter() {
        this.random = new Random();
    }

    public String route(List<String> endpoints) {
        int num = random.nextInt() % (endpoints.size() - 1);
        return endpoints.get(num);
    }

}
