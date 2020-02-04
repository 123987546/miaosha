package com.miaosha.day1.redis;

public abstract class BasePrefix implements KeyPrefix {

    private int expireSeconds;
    private String prefix;

    public BasePrefix(int expireSeconds,String prefix){
        this.expireSeconds=expireSeconds;
        this.prefix=prefix;
    }
    public BasePrefix(String prefix){
        this.expireSeconds=0;
        this.prefix=prefix;
    }
    public String getPrefix(){
        String className=this.getClass().getSimpleName();
        return className+":"+prefix;
    }
    @Override
    public int expireSeconds() {//默认0为永不过期
        return expireSeconds;
    }


}
