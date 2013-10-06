package com.gatech.faceme;

import com.google.api.server.spi.config.Api;

@Api(name = "example")
public class HelloWorldEndpoint {
 public Container getThing() {
  Container c = new Container();
  c.Text = "Hello world!";
  return c;
 }
 
 public class Container {
  public String Text;
 }
}
