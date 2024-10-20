package org.project;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("v1/agent")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgentResource {

    @Inject
    AgentService agentService;

    @GET
    public Response getAllAgents() {
        try {
            List<Agent> agents = agentService.getAllAgents();
            return Response.ok(agents).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new AgentService.ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getAgentById(@PathParam("id") int id) {
        try {
            Agent agent = agentService.getAgentById(id);
            return Response.ok(agent).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new AgentService.ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    public Response createAgent(Agent agent) {
        try {
            Agent createdAgent = agentService.createAgent(agent);
            return Response.status(Response.Status.CREATED)
                    .entity(createdAgent)
                    .build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new AgentService.ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateAgent(@PathParam("id") int id, Agent updatedAgent) {
        try {
            Agent agent = agentService.updateAgent(id, updatedAgent);
            return Response.ok(agent).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new AgentService.ErrorResponse("No content found to update for Agent with id " + id))
                    .build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new AgentService.ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @Path("/{id}")
    @DELETE
    public Response deleteAgentById(@PathParam("id") int id) {
        try {
            agentService.deleteAgent(id);
            return Response.ok("Agent with id " + id + " was successfully deleted!")
                    .build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new AgentService.ErrorResponse(e.getMessage()))
                    .build();
        }
    }
}

// logger
// throwing exception
// interceptor
// swagger UI
