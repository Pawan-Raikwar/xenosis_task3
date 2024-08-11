import java.net.*;  // for the networking 
import java.io.*;

class Server{

    // Constructor.\\
     ServerSocket server;  // declear the variable 
     Socket socket;   //   this is variable to store the object

     BufferedReader br ; // for read the chat
      PrintWriter out;  // for write the chat
public Server(){
  
    try {

        server=new ServerSocket(7777);         // create the object  
        System.out.println("Server is ready to accept connection ");
        System.out.println("waiting ...");
        socket=server.accept();  // for accept the connecton
  
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
                    System.out.println("Client terminated the chat");
                    socket.close();
                    break;
                }
                System.out.println("Client :"+msg);
            } 
           
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
          //  e.printStackTrace();
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
              }catch(Exception e){
                // e.printStackTrace();
                System.out.println("connection is closed");

             }
        };

        // call to the tread
        new Thread(r2).start();
   }
     
    public static void main(String[] args) {
        System.out.println("this si server");
        new Server();
    }
}