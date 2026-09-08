package simulation;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class LaunchDriveLoadTest extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://staging2.zealousys.com")
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .acceptLanguageHeader("en-US,en;q=0.9")
            .userAgentHeader("Mozilla/5.0");


    ScenarioBuilder scn = scenario("Launch Drive Load Test")
            .exec(
                    http("Open Bidmaster Launch Drive")
                            .get("/SliderStock-optimize/SliderStock/public/bidmaster-launch-drive")
                            .check(status().is(200))
            );

    {
        setUp(
                scn.injectOpen(
                        atOnceUsers(200)
                )
        ).protocols(httpProtocol);
    }
}