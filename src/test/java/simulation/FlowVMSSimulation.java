package simulation;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class FlowVMSSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://flowstaging.impulsedecisions.com")
            .acceptHeader("text/html,application/xhtml+xml,application/xml")
            .acceptLanguageHeader("en-US,en;q=0.9")
            .userAgentHeader("Mozilla/5.0");

    ScenarioBuilder scn = scenario("Flow Login")
            .exec(
                    http("Open Login Page")
                            .get("/login")
                            .check(
                                    status().is(200),
                                    css("meta[name='csrf-token']", "content")
                                            .saveAs("csrfToken")
                            )
            )
            .exec(
                    http("Login")
                            .post("/login")
                            .header("X-CSRF-TOKEN", "#{csrfToken}")
                            .header("X-Requested-With", "XMLHttpRequest")
                            .header("X-Requested-With", "XMLHttpRequest")
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .formParam("email", "testing.zealousys@gmail.com")
                            .formParam("password", "Admin@123")
                            .check(status().in(200, 302))
            )
            .exec(
                    http("Items")
                            .get("/items")
                            .check(status().is(200))
            );

    {
        setUp(
                scn.injectOpen(
                        atOnceUsers(20)
                )
        ).protocols(httpProtocol);
    }
}