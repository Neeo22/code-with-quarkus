package org.acme;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.time.Duration;
import java.util.List;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Multi<Long> streamUserCount() {
        return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
                .onOverflow().drop()
                .onItem().transformToUniAndConcatenate(
                        tick -> User.count())
                .log();
    }

    @POST
    @Path("/add")
    @Produces("*/*")
    @Consumes(MediaType.TEXT_PLAIN)
    @WithTransaction
    public Uni<Void> addUser() {
        Log.info("Adding user...");
        User u = new User("test");
        return User.persist(u).invoke(() -> Log.info("User added"));
    }

    @GET
    @Path("/get")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<List<User>> getAllUser() {
        Log.info("Get all user");
        return User.findAll().list();
    }
}
