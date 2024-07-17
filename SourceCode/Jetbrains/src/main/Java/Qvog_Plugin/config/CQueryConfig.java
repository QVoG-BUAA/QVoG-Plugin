package Qvog_Plugin.config;

public class CQueryConfig {
    public gremlin gremlin;
    public cache cache;
}

class gremlin {
    public String host;
    public int port;
}

class cache {
    public String type;
    public redis redis;
}

class redis {
    public String host;
    public int port;
    public String password;
    public int db;
    public int shard;
}