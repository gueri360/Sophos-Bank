package com.sophos.bootcamp.bankapi.entities.enums;

public enum AccountStatus {
     ACTIVE("ACTIVE"),INACTIVE("INACTIVE"),CANCELLED("CANCELLED");

     private String status;

     AccountStatus(String status){
          this.status = status;
     }

     public String getStatus() {
          return status;
     }
}
