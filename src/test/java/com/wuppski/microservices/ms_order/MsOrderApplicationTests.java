package com.wuppski.microservices.ms_order;

import com.wuppski.microservices.ms_order.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//port 0 means a random port will be used
@AutoConfigureWireMock(port = 0)
class MsOrderApplicationTests {

	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mySQLContainer.start();
	}

	@Test
	void submitOrderTest() {
		String submitJSON = """
				{
				    "skuCode" : "Bosch_Extractor2",
				    "price" : 249.00,
				    "quantity" : 1
				}
				""";

		//WireMock stub to run instead of calling the actually WS
		InventoryClientStub.stubInventoryCall("Bosch_Extractor2", 1);

		String responseBodyString = RestAssured.given()
				.contentType("application/json")
				.body(submitJSON)
				.when()
				.post("/api/order")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body().asString();

		MatcherAssert.assertThat(responseBodyString, Matchers.is("Order placed successfully"));
	}

}
