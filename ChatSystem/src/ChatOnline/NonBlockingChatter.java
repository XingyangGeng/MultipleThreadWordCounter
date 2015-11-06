package ChatOnline;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;


public class NonBlockingChatter implements Chatter {

	private final SocketChannel chatServerChannel;

	private final Pipe.SourceChannel userInput;

	public NonBlockingChatter(SocketChannel chatServerChannel,
			Pipe.SourceChannel userInput) {
		this.chatServerChannel = chatServerChannel;
		this.userInput = userInput;
	}

	@Override
	public void run() {
		// TODO - read from userInput and write data to chatServerOutput
		// TODO - read from chatServerChannel and print values
		try {
			Charset UTF8 = Charset.forName("UTF-8");
			ByteBuffer readbuff = ByteBuffer.allocate(1024);
			ByteBuffer writebuff = ByteBuffer.allocate(1024);
			ByteBuffer printbuff = ByteBuffer.allocate(1024);
			Selector selector = Selector.open();
			SelectionKey userkey = userInput.register(selector,
					SelectionKey.OP_READ);
			 chatServerChannel.register(selector,
						SelectionKey.OP_READ | SelectionKey.OP_WRITE);
			while (!Thread.currentThread().isInterrupted()) {
				int events = selector.select();
				if (events == 0) {
					continue;
				}
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
				while (keyIterator.hasNext()) {
					SelectionKey key = keyIterator.next();
					keyIterator.remove();
					if (key.equals(userkey)) {
							Pipe.SourceChannel userchannel = (Pipe.SourceChannel) key
									.channel();
							printbuff.clear();
							int read = userchannel.read(printbuff);
							if (read == -1)
								continue;
							printbuff.flip();
							writebuff.clear();
							writebuff.put(printbuff);
							writebuff.put((byte) '\n');
					} else {
						if (key.isReadable()) {
							SocketChannel socketchannel = (SocketChannel) key.channel();
							readbuff.clear();
							int read = socketchannel.read(readbuff);
							if (read == -1)
								continue;
							readbuff.flip();
							CharsetDecoder decoder = UTF8.newDecoder();
							CharBuffer charBuffer = decoder.decode(readbuff);
							System.out.printf("%s%n", charBuffer.toString());
						} else if (key.isWritable()) {
							SocketChannel socketchannel = (SocketChannel) key.channel();
							if ((writebuff == null)|| (writebuff.position() == 0)) {
								continue;
							}
							writebuff.flip();
							socketchannel.write(writebuff);
							writebuff.clear();
						}
					}


				}
			}
		} catch (IOException e) {
			System.out.printf("Fail to connect -%s%n",e.getMessage());
		}

	}

}
