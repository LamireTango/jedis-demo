import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisTest {
    /**
     * 快速入门
     * */
    @Test
    public void test1(){
        //1.获取连接
        Jedis jedis = new Jedis("localhost",6379);
        //2.操作
        jedis.set("name","zhangsan");
        //3.关闭连接
        jedis.close();
    }
    /**
     * String数据结构操作
     * */
    @Test
    public void test2(){
        //1.获取连接
        Jedis jedis = new Jedis();//默认参数是localhost和6379
        //2.操作
        //存储
        jedis.set("name","zhangsan");
        //获取
        String username = jedis.get("name");
        System.out.println(username);
        //setex()存储指定过期时间
        jedis.setex("activecode",20,"hhh");//20秒后删除
        //3.关闭连接
        jedis.close();
    }
    /**
     * hash 数据结构操作
     * */
    @Test
    public void test3(){
        //1.获取连接
        Jedis jedis = new Jedis();//默认参数是localhost和6379
        //2.操作
        //存储
        jedis.hset("myhash","name","abc");
        jedis.hset("myhash","age","23");
        jedis.hset("myhash","gender","male");
        //获取hash
        String name = jedis.hget("myhash","age");
        System.out.println(name);
        Map<String,String> map = jedis.hgetAll("myhash");
        System.out.println(map);
        //3.关闭连接
        jedis.close();
    }
    /**
     * list 数据结构操作
     * */
    @Test
    public void test4(){
        //1.获取连接
        Jedis jedis = new Jedis();//默认参数是localhost和6379
        //2.操作
        jedis.lpush("mylist","a");//在Redis 2.4版本以前的 LPUSH 命令，都只接受单个 value 值。
        jedis.rpush("mylist", "1");
        //获取
        List<String> mylist = jedis.lrange("mylist", 0, -1);
        System.out.println(mylist);
        //弹出
        String element = jedis.lpop("mylist");
        System.out.println(element);
        //3.关闭连接
        jedis.close();
    }
    /**
     * set 数据结构操作
     * */
    @Test
    public void test5(){
        //1.获取连接
        Jedis jedis = new Jedis();//默认参数是localhost和6379
        //2.操作
        jedis.sadd("myset","java","cplus");
        Set<String> myset = jedis.smembers("myset");
        System.out.println(myset);
        //3.关闭连接
        jedis.close();
    }
    /**
     * sorted set 数据结构操作
     * */
    @Test
    public void test6(){
        //1.获取连接
        Jedis jedis = new Jedis();//默认参数是localhost和6379
        //2.操作
        jedis.zadd("mysortedset",3,"李");
        jedis.zadd("mysortedset",50,"吴");
        jedis.zadd("mysortedset",1,"天");
        Set<String> mysortedset = jedis.zrange("mysortedset", 0, -1);
        System.out.println(mysortedset);
        //3.关闭连接
        jedis.close();
    }
    /**
     * JedisPool连接池
     * */
    @Test
    public void test7(){
        //0.创建一个配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(50);//最大活动对象数
        config.setMaxIdle(10);//最大连接数
        //1.创建连接池对象
        JedisPool jedisPool = new JedisPool(config,"localhost",6379);
        //2.获取连接
        Jedis jedis = jedisPool.getResource();
        //3.使用
        jedis.set("mykey","myvalue");
        //4.关闭 归还到连接池中
        jedis.close();
    }
    /**
     * 通过JedisPool连接池工具类获取
     * */
    @Test
    public void test8(){
        Jedis jedis = JedisPoolUtils.getJedis();
        jedis.set("tool","tool123456");
        jedis.close();
    }
}
