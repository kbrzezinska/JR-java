import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Spis extends UnicastRemoteObject implements ISpis {

    List<OS>servers=new ArrayList<>();

    public Spis() throws RemoteException {
    }

    @Override
    public boolean register(OS os) {
        if(servers.contains(os)) return false;
        servers.add(os);
        System.out.println("zarejestrowano "+os.name+" "+os.alg);
        return true;
    }

    @Override
    public List<OS> getServers() {
       // System.out.println("wylistowano serwery ");

        return servers;
    }
    public static void main(String[] args)
    {

        try {
            LocateRegistry.createRegistry(2003);
            Spis spis=new Spis();
            Naming.bind("//localhost:2003/Spis",spis);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }


    }

}
