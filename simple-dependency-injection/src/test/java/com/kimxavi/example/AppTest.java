package com.kimxavi.example;

import com.kimxavi.example.container.ServiceContainer;
import com.kimxavi.example.service.SoccerService;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void shouldNotNull() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        SoccerService soccerService = ServiceContainer.getObject(SoccerService.class);

        Assert.assertNotNull(soccerService);
        Assert.assertNotNull(soccerService.soccerRepository);
        Assert.assertNull(soccerService.basketRepository);
    }
}
