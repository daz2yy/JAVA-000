package gateway.router;

import java.util.List;

public interface IHttpEndpointRouter {
    
    String route(List<String> endpoints);
    
}
