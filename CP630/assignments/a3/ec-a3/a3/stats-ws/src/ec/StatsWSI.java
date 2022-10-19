package ec;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface StatsWSI {
    @WebMethod
    Double getCount();
    
    @WebMethod
    Double getMin();
    
    @WebMethod
    Double getMax();
    
    @WebMethod
    Double getMean();
    
    @WebMethod
    Double getSTD();
}
