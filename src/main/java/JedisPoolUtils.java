import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * JedisPool工具类：加载配置文件，提供获取连接的方法
 * */
public class JedisPoolUtils {
    private final static JedisPool jedisPool;
    static {
        //读取配置文件
        InputStream is = Jedis.class.getClassLoader().getResourceAsStream("jedis.properties");
        //创建Properties对象
        Properties pro = new Properties();
        //关联文件
        try {
            pro.load(is);
        } catch (IOException e) {
            System.out.println("配置时发生异常");
            e.printStackTrace();
        }
        Integer maxTotal = Integer.parseInt(pro.getProperty("maxTotal"));
        Integer maxIdle = Integer.parseInt(pro.getProperty("maxIdle"));
        String host = pro.getProperty("host");
        Integer port = Integer.parseInt(pro.getProperty("port"));
        //获取数据，设置到JedisPoolConfig中
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxTotal);
        config.setMaxIdle(maxIdle);
        //初始化JedisPool
        jedisPool = new JedisPool(config,host,port);
    }
    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
}
