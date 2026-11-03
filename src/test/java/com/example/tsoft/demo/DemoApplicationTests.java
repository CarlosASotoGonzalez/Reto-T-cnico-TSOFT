package com.example.tsoft.demo;

import com.example.tsoft.demo.client.PetClientTest;
import com.example.tsoft.demo.controller.PetControllerTest;
import com.example.tsoft.demo.service.PetServiceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
		PetClientTest.class,
		PetControllerTest.class,
		PetServiceTest.class
})
class DemoApplicationTests {
}