package ChatOnline;

import java.io.*;


public class BlockingChatter implements Chatter {

    private final InputStream chatServerInput;

    private final OutputStream chatServerOutput;

    private final InputStream userInput;

    public BlockingChatter(InputStream chatServerInput, OutputStream chatServerOutput, InputStream userInput) {
        this.chatServerInput = chatServerInput;
        this.chatServerOutput = chatServerOutput;
        this.userInput = userInput;
    }

    @Override public void run() {
        // TODO - read from userInput and write data to chatServerOutput
        // TODO - read from chatServerInput and print values
    	BufferedReader userin = new BufferedReader(new InputStreamReader(userInput));
    	BufferedReader chatserverin = new BufferedReader(new InputStreamReader(chatServerInput));
    	BufferedWriter chatserverout = new BufferedWriter(new OutputStreamWriter(chatServerOutput));
  	
    	
    	Thread systeminThread = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					String userstr;
					try {
						userstr = userin.readLine();
						chatserverout.write(userstr);
						chatserverout.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.printf("Fail to send -%s%n",e.getMessage());
					}
				}
			}
    		
    	});
    	Thread serverinThread = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					String chatserverinputstr;
					try {
						chatserverinputstr = chatserverin.readLine();
						System.out.println(chatserverinputstr);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.printf("Fail to receive -%s%n",e.getMessage());
					}

				}
			}
    		
    	});
    	serverinThread.start();
    	systeminThread.start();

    }

}
