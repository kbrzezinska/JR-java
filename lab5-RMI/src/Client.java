
import sorting.FloatElement;
import sorting.IElement;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.WeakHashMap;

import static java.lang.Thread.sleep;

public class Client {



    static List<IElement> generate(int n)
    {
        List<IElement> list=new ArrayList<>();
        Random random=new Random();
        for(int i=0;i<n;i++)
        {
            list.add(new FloatElement(Integer.toString(i),(float)random.nextFloat()));
        }
        return list;

    }
    public static void main(String[] args)
    {

        try {
            ISpis iSpis=(ISpis) Naming.lookup("//localhost:2003/Spis");

            Random random=new Random();
            int size=iSpis.getServers().size();
            OS os=iSpis.getServers().get(random.nextInt(size));

            List<OS> servers=iSpis.getServers();

            for (int i = 0; i < servers.size(); i++) System.out.println(servers.get(i).name+" "+servers.get(i).alg);

            System.out.println("Server: "+os.name+"  alg: "+os.alg+" ");

            IServer server=(IServer) Naming.lookup("//localhost:2003/"+os.name);

            while (true) {
                servers=iSpis.getServers();
                if(servers.size()>size) {
                    for (int i = 0; i < servers.size(); i++)
                        System.out.println(servers.get(i).name + " " + servers.get(i).alg);
                    size=servers.size();

                }
                sleep(1000);
                List<IElement> list=generate(random.nextInt(4)+2);
                System.out.println("wygenerowano:");

                for (int i = 0; i < list.size(); i++) System.out.print(list.get(i).getValue()+" ");
                list = server.solve(list);

                System.out.println("\n rozwiązano:");
                for (int i = 0; i < list.size(); i++) System.out.print(list.get(i).getValue()+" ");
                System.out.println("Server: "+os.name+"  alg: "+os.alg+" ");
                sleep(1000);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
