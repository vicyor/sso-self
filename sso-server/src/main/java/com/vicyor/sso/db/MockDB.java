package com.vicyor.sso.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者:姚克威
 * 时间:2020/3/19 14:34
 **/
public class MockDB {
    private static Map<String, List<String>>relations=new HashMap<>();
    public  static  void putRelation(String token,String app){
        if(relations.get(token)==null){
            relations.put(token,new ArrayList<>());
        }
        List<String> apps = relations.get(token);
        apps.add(app);
        relations.put(token,apps);
    }
    public static List<String> getRelation(String token){
        return relations.get(token);
    }
}
