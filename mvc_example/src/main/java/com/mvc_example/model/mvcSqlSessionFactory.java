package com.mvc_example.model;

import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class mvcSqlSessionFactory {
  static String resource = "mybatisConfig.xml";
  private static SqlSessionFactory singleton;
  
  public mvcSqlSessionFactory() {
  }
  
  public static SqlSessionFactory getInstance() throws IOException {
    if (singleton == null) {
      System.out.println("make new one");
      
      InputStream inputStream = Resources.getResourceAsStream(resource);
      singleton = (new SqlSessionFactoryBuilder()).build(inputStream);
    }
    return singleton;
  }
}
