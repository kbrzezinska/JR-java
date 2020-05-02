
import sorting.IElement;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IServer extends Remote  {

    List<IElement> solve(List<IElement> list) throws RemoteException;;
}
