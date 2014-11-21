package sample.simple;

import java.net.URI;

import org.springframework.stereotype.Service;
import com.justinsb.etcd.EtcdClient;
import com.justinsb.etcd.EtcdClientException;
import com.justinsb.etcd.EtcdResult;

@Service
class AppRepository {
	
	EtcdClient client = null;
	EtcdResult resultSet = null;

	public void init() {
		
		try {
			    client = new EtcdClient(URI.create("http://127.0.0.1:4001/"));
			    resultSet = client.set("new", "1");
		}

		catch(EtcdClientException e) {
        e.printStackTrace();
    }
	}
}