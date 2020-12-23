package com.example.accountservice.services;

import com.example.accountservice.queries.CustomerQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @QueryHandler
   public  void customerSummery(CustomerQuery customerQuery)
    {

    }
}
