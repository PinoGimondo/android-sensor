package com.gimus.gimus_lib.apiclient;

public class ApiObject {
   public String uid;
   public Object tag;
   public ApiObject() {
       uid=java.util.UUID.randomUUID().toString();
   }
}
