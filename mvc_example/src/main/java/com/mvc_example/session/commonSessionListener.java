package com.mvc_example.session;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.EventListener;

@WebListener
public class commonSessionListener implements HttpSessionListener, HttpSessionAttributeListener {
  public commonSessionListener() {
    System.out.println("session 리스너 생성자 호출");
  }
  
  //  세션 생성, 소멸
  public void sessionCreated(HttpSessionEvent event) {
    System.out.println("=====> 세션 생성됨");
  }
  
  public void sessionDestroyed(HttpSessionEvent event) {
    System.out.println("=====> 세션 소멸됨");
  }
  
  //  세션 인자값 추가, 변경, 삭제
  public void attributeAdded(HttpSessionBindingEvent event) {
    System.out.println("=====> 세션 속성 추가 :: " + "세션 명 : " + event.getSession().getId() + " :: "
                                            + "속성 명 : " + event.getName() + " :: "
                                            + "속성 값 : " + event.getValue());
  }
  
  public void attributeReplaced(HttpSessionBindingEvent event) {
    System.out.println("=====> 세션 속성 변경 :: " + "세션 명 : " + event.getSession().getId() + " :: "
                                            + "속성 명 : " + event.getName() + " :: "
                                            + "속성 값 : " + event.getValue());
  }
  
  public void attributeRemoved(HttpSessionBindingEvent event) {
    System.out.println("=====> 세션 속성 삭제 :: " + "세션 명 : " + event.getSession().getId() + " :: "
                                            + "속성 명 : " + event.getName() + " :: "
                                            + "속성 값 : " + event.getValue());
  }
}
