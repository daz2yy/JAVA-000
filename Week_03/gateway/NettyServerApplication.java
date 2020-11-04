package gateway;

import gateway.inbound.HttpInboundServer;

public class NettyServerApplication {
    private final static String GATEWAY_NAME = "NIOGateway";
    private final static String GATEWAY_VERSION = "1.0.0";

    public static void main(String[] args) {
        String proxyServer = System.getProperty("server", "http://www.baidu.com");
        String port = System.getProperty("port", "8888");

        //  http://localhost:8888/api/hello  ==> gateway API, 转发到 backend service
        //  http://localhost:8088/api/hello  ==> backend service

        int p = Integer.parseInt(port);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" starting...");
        HttpInboundServer server = new HttpInboundServer(p, proxyServer);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" started at http://localhost:" + port + " for server:" + proxyServer);
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
