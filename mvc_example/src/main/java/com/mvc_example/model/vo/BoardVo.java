package com.mvc_example.model.vo;

public class BoardVo {
  private int index;
  private String title;
  private String content;
  private String writer;
  private String date;
  
  public BoardVo() {
  }
  
  public int getIndex() {
    return this.index;
  }
  
  public void setIndex(int index) {
    this.index = index;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public String getWriter() {
    return this.writer;
  }
  
  public void setWriter(String writer) {
    this.writer = writer;
  }
  
  public String getDate() {
    return this.date;
  }
  
  public void setDate(String date) {
    this.date = date;
  }
}
