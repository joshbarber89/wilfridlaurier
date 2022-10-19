package ec;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/")
@RequestScoped
public class StatsRS {
    @EJB 
    StatsEJBStatelessLocal stats;    

    @GET
    @Path("/getCount")
    @Produces({ "application/json" })
    public String getCount() {
        return "{\"result\":\"" + stats.getCount() + "\"}";
    }

    @GET
    @Path("/getMin")
    @Produces({ "application/json" })
    public String getMin() {
        return "{\"result\":\"" + stats.getMin() + "\"}";
    }
    
    @GET
    @Path("/getMax")
    @Produces({ "application/json" })
    public String getMax() {
        return "{\"result\":\"" + stats.getMax() + "\"}";
    }
    
    @GET
    @Path("/getMean")
    @Produces({ "application/json" })
    public String getMean() {
        return "{\"result\":\"" + stats.getMean() + "\"}";
    }
    
    @GET
    @Path("/getSTD")
    @Produces({ "application/json" })
    public String getSTD() {
        return "{\"result\":\"" + stats.getSTD() + "\"}";
    }
}
