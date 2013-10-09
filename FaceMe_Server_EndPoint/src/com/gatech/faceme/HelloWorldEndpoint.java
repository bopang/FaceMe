package com.gatech.faceme;

import java.util.ArrayList;

import com.google.api.server.spi.config.Api;

@Api(name = "example")
public class HelloWorldEndpoint {
 public Container getText() {
  Container c = new Container();
  c.Text = "Hello world!";
  c.Num = 100;
  c.URLs = new ArrayList<String>();
  c.URLs.add("AAA");
  c.URLs.add("BBB");
  return c;
 }
  
 
 
 public class Container {
  public String Text;
  public int Num;
  public ArrayList<String> URLs;
 }
}
