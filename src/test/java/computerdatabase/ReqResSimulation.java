package computerdatabase;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import utils.Constant;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

/**
 * In this simulation we gona test this app
 * url: <a href="https://reqres.in/">reqres.in</a>
 */
public class ReqResSimulation extends Simulation {

	HttpProtocolBuilder httpBuilder = http.baseUrl(Constant.URL)
		.acceptHeader("application/json").header("Content-Type", "application/json");

	public ChainBuilder createUser() {
		return exec(
			http("Add new user")
				.post("/api/users/")
				.body(StringBody("{\"name\": \"Angel\", \"job\": \"Developer\"}".strip()
				)).asJson()
		);
	}

	public ChainBuilder getUserById() {
		return exec(
			http("Get user by ID")
				.get("/api/users/2")
		);
	}

	public ChainBuilder getUserPerPage() {
		return exec(
			http("Get user per page 1 - 100")
			.get("/api/users?page=1&per_page=100")
		);
	}

	ScenarioBuilder users = scenario("Users")
		.exec(createUser()
		.exec(getUserById())
		.exec(getUserPerPage())
	);
	ScenarioBuilder admins = scenario("Admins")
		.exec(createUser()
		.exec(getUserById())
		.exec(getUserPerPage())
	);

	{
		setUp(
			users.injectOpen(rampUsers(10).during(10)),
			admins.injectOpen(rampUsers(10).during(10))
		).protocols(httpBuilder);
	}

}
