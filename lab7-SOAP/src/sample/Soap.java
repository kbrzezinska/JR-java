package sample;

import com.sun.xml.internal.ws.resources.SoapMessages;

import javax.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.Endpoint;
import java.io.*;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class Soap extends Thread{

    public static MessageFactory messageFactory;
    public Socket socketAccept;
    public static Socket socket;
    public ServerSocket serverSocket;
    public static String name;
    public static String to;
    public String portListen;
    public static String portSend;
    private static PrintWriter out;
    private BufferedReader in;
    public static Controller controller;
    public static Soap instance;

    public Soap() throws SOAPException {
        messageFactory = MessageFactory.newInstance();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Soap(String name, String to, String portSend, String portListen)
    {
        this.instance=this;
        this.name=name;
        this.to=to;
        this.portSend=portSend;
        this.portListen=portListen;

        try {
            messageFactory = MessageFactory.newInstance();

        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }


    public static SOAPMessage createMessage(String message,String toNode,String from)
    {


        SOAPMessage soapMessage = null;
        try {
             soapMessage = messageFactory.createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope soapEnvelope = soapPart.getEnvelope();

            SOAPBody soapBody = soapEnvelope.getBody();
            Name bodyName =  SOAPFactory.newInstance().createName("text");
            SOAPBodyElement soapBodyElement=soapBody.addBodyElement(bodyName);
            soapBodyElement.addTextNode(message);


            SOAPHeader header = soapEnvelope.getHeader();
            Name headerName = SOAPFactory.newInstance().createName("send", "hh", "aa");

            SOAPElement soapHeaderElement = header.addChildElement(headerName);
            soapHeaderElement.addTextNode(toNode);

            Name headerName2 = SOAPFactory.newInstance().createName("from", "hh", "aa");

            SOAPElement soapHeaderElement2 = header.addChildElement(headerName2);
            soapHeaderElement2.addTextNode(from);

            soapMessage.saveChanges();
            //soapMessage.writeTo(System.out);

        } catch (SOAPException e) {
            e.printStackTrace();
        }

        return soapMessage;

    }


    public void run()
    {
        System.out.println("kkk");
        try {
            serverSocket=new ServerSocket(Integer.parseInt(portListen));



             while (true) {
                 socketAccept = serverSocket.accept();
                 in = new BufferedReader(new InputStreamReader(socketAccept.getInputStream()));
             if (!socketAccept.isClosed()) {

                 String m = null, response = null;
                 response = in.readLine();

                 while ((m = in.readLine()) != null) {

                     response = response + m;

                 }
                 if (response != null) readFromStream(response);
             }
             in.close();
         }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public static void send(SOAPMessage soapMessage) throws SOAPException, IOException
        {
             socket = new Socket("127.0.0.1", Integer.parseInt(portSend));

            //out = new PrintWriter(socket.getOutputStream(), true);
            PrintStream outt = new PrintStream(socket.getOutputStream(), true);

           soapMessage.writeTo(outt);
          // out.close();
           outt.close();

    }


    public void readFromStream(String message) {


        SOAPMessage soapMessage = null;
        try {

            InputStream is = new ByteArrayInputStream(message.getBytes());
            soapMessage = messageFactory.createMessage(null,is);

            SOAPHeader header = soapMessage.getSOAPPart().getEnvelope().getHeader();
            SOAPBody body = soapMessage.getSOAPPart().getEnvelope().getBody();

            Node node = (Node) header.getElementsByTagNameNS("aa","send").item(0);
            String send = node.getFirstChild().getTextContent();


            node = (Node) header.getElementsByTagNameNS("aa","from").item(0);
            String from = node.getFirstChild().getTextContent();

            node = (Node) body.getElementsByTagName("text").item(0);
            String  text = node.getFirstChild().getTextContent();

            String log=controller.log.getText()+"\n\n";
            if(from.equals(name )&& !send.equals(name))return;;
            if(send.equals(name))
            {
                controller.Receiver.setText(text);
                controller.log.setText("otrzymano wiadomośc od: "+from+"\n jej tresc to: "+text);
            }

            else
                {
                if (send.equals("broadcast") && !from.equals(name)) {
                    controller.Receiver.setText(text);
                    controller.log.setText("Broadcast: \notrzymano wiadomośc od: "+from+"\n jej tresc to: "+text);

                    if(!to.equals(from)) {
                        send(soapMessage);
                        controller.log.setText("Broadcast: \notrzymano wiadomośc od: "+from+"\n jej tresc to: "+text+"\nprzekazano dalej do: "+to);

                    }
                }

                else{
                    send(soapMessage);
                    controller.log.setText("przekazano wiadomośc od: "+from+"\n jej tresc to: "+text+" do: "+send);

                }

                }
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public  void getResponse(SOAPMessage soapResponse)
    {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            Source sourceContent = soapResponse.getSOAPPart().getContent();
            StreamResult result = new StreamResult(System.out);
            transformer.transform(sourceContent, result);


        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (SOAPException e) {
            e.printStackTrace();
        }

    }
}
