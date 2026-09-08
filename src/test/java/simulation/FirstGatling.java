package simulation;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class FirstGatling extends Simulation {

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://www.staging1.sliderstock.com")
            .acceptHeader("application/json");

    private ScenarioBuilder scn = scenario("First gatling script")
            .exec(http("Get the auction page")
                    .get("/pitchsidefeast26"));

    {
        setUp(
                scn.injectOpen(atOnceUsers(500))
        ).protocols(httpProtocol);
    }
}