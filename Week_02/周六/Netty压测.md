// 命令
sb -u http://localhost:8808/test -c 40 -N 30

效果如图：

![netty_sb](D:\Study\Java\JAVA-000\Week_02\images\netty_sb.png)



HttpClient 的代码如下：

```java
public class HttpTest {
    public void doGetTest() {
        // Http 客户端
        HttpClientBuilder builder = HttpClientBuilder.create();
        CloseableHttpClient httpClient = builder.build();
        // URL
        HttpGet get = new HttpGet("http://localhost:8808/test");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            System.out.println("\n\nStatus: " + response.getStatusLine());
            System.out.println("Content-Type: " + entity.getContentType());
            System.out.println("Content-Length: " + entity.getContentLength());
            System.out.println("Content: " + EntityUtils.toString(entity));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        HttpTest test = new HttpTest();
        while(true) {
            test.doGetTest();
            Thread.sleep(1000);
        }
    }
}
```

