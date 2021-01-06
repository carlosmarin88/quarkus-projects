package org.acme.getting.started;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.test.Mock;

@ApplicationScoped
/*
    que sea mock lo va hacer mas prioritario en la inyeccion
*/
@Mock
public class MockExpensiveService implements ExpensiveService {

    @Override
    public int calculate() {
        return 20;
    }
    
}
