package sample.simple;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

//import sample.simple.AppRepository;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.util.concurrent.ListenableFuture;
import com.justinsb.etcd.EtcdClient;
import com.justinsb.etcd.EtcdClientException;
import com.justinsb.etcd.EtcdResult;
//import com.justinsb.etcd.EtcdNode;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/api/v1")
public class HelloController   {

    EtcdClient client = new EtcdClient(URI.create("http://127.0.0.1:4001/"));

    @RequestMapping(value = "/counter", method = RequestMethod.GET)
    public String counter() {
        String ctr = " ";

      try {
        EtcdResult res = client.get("wen");
        if (!res.isError())
            ctr = getCounter();
        else
            ctr = init();
      } 
      catch(EtcdClientException e) {
         e.printStackTrace();
        } 
        return ctr;
    }

    public String init()
    {
        String finalRes = " ";
        try {
        EtcdResult result = client.set("wen", "0");
        result = client.get("wen");
        finalRes = result.value;
        }
        catch(EtcdClientException e) {
         e.printStackTrace();
        }
        return finalRes;
    }

    public String getCounter() {
    
    String finalRes = " ";

    try {

    EtcdResult resultGet = client.get("wen");

    String val = resultGet.value;
    
    int ctr = Integer.parseInt(val);
    System.out.println("ctr"+ctr); 

    ctr = ctr + 1;

    String upd = Integer.toString(ctr);

    client.set("wen",upd);

    resultGet = client.get("wen");

    System.out.println("new val = "+resultGet.value);

    finalRes = resultGet.value;
    }
    catch(EtcdClientException e) {
        e.printStackTrace();
    }
    return finalRes;
    
    }
}
