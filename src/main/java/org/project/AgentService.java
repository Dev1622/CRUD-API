package org.project;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;


@ApplicationScoped
public class AgentService {

    Logger logger = LoggerFactory.getLogger(AgentService.class);

    public List<Agent> getAllAgents(){
        List<Agent> agents = Agent.listAll();
        if (agents.isEmpty()) {
            logger.warn("No agents found");
            throw new NotFoundException("No agents found");
        }
        logger.info("Agents were fetched");
        return agents;

    }

    public Agent getAgentById(int id){
        Agent agent = Agent.findById(id);
        if (agent == null) {
            throw new NotFoundException("Agent with id " + id + " not found");
        }
        logger.info("The agent was fetched");
        return agent;

    }

    @Transactional
    public Agent createAgent(Agent agent){
        if (agent == null) {
            throw new BadRequestException("Cannot create a null agent");
        }
        if (agent.name == null || agent.skill == null) {
            throw new BadRequestException("Agent's name and skill must not be null");
        }
        agent.persist();
        logger.info("The agent was created");
        return agent;
    }

    @Transactional
    public Agent updateAgent(int id, Agent updatedAgent){
        Agent agent = Agent.findById(id);
        if (agent == null) {
            throw new NotFoundException("Agent with id " + id + " not found");
        }
        if (updatedAgent == null || updatedAgent.name == null || updatedAgent.skill == null) {
            throw new BadRequestException("Updated agent information cannot be null");
        }
        agent.name = updatedAgent.name;
        agent.age = updatedAgent.age;
        agent.skill = updatedAgent.skill;
        agent.persist();
        logger.info("The agent was updated");
        return agent;
    }

    @Transactional
    public void deleteAgent(int id) {
        boolean deleted = Agent.deleteById(id);
        if (!deleted) {
            throw new NotFoundException("Agent with id " + id + " not found, unable to delete");
        }
        logger.info("The agent was deleted");
    }

    public static class ErrorResponse{
        public String error;

        public ErrorResponse(String error){
            this.error = error;
        }
    }
}
