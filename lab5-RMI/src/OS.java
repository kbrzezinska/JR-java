import java.io.Serializable;

public class OS implements Serializable {

    public String name;
    public String alg;

    public OS(String name,String alg)
    {
        this.name=name;
        this.alg=alg;
    }


}
