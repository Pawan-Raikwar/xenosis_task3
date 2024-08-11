import java.io.*;
import java.net.Socket;

public class Client {
 
      Socket socket;  /// variable
      BufferedReader br;
      PrintWriter out;

     public Client(){
         try {
            System.out.println("Sending request to server");
            socket=new Socket("127.0.0.1",7777);
            System.out.println("connection done.");

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
         } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
         }
     }

     /// metod
   public void startReading(){
    // thread -- read karke deta rahega 
     // creating thread
     Runnable r1=()->{
       System.out.println("reader started..");
       
       try{
       while (true) {
           String msg;
           
               msg = br.readLine();
               if(msg.equals("exit")){
                   System.out.println("Server terminated the chat");
                   break;
               }
               System.out.println("Server :"+msg);
           } 
          
       }catch (IOException e) {
        // TODO Auto-generated catch block
       // e.printStackTrace();
       System.out.println("connection is closed");

    }
     };
     // call to the thread
     new Thread(r1).start();
  
  }

  public void startWriting(){
       // creating thread
       Runnable r2=()->{
            System.out.println("writer started..");
            try{
             while (!socket.isClosed()) {
                     
                   BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                   String content=br1.readLine();
                   out.println(content);
                   out.flush();

                   if(content.equals("exit")){
                      socket.close();
                      break;
                   }
                }
             }
            catch(Exception e){
                // e.printStackTrace();
                System.out.println("connection is closed");

             }
       };

       // call to the tread
       new Thread(r2).start();
  }
    
    public static void main(String[] args) {
        System.out.println("this is client");
        new Client(); 
    }
}
