package com.zhaofujun.nest.ddd.context;

public interface Transaction {
   void commit();

   void begin();

   void rollback();

   public class DefaultTransaction implements Transaction {

      @Override
      public void commit() {

      }

      @Override
      public void begin() {

      }

      @Override
      public void rollback() {

      }

   }
}
