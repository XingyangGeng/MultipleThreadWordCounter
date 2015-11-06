

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ThreadedWordCounter extends AbstractConcurrencyFactorProvider
		implements WordCounter {
	private AtomicInteger count;
	private AtomicInteger line = new AtomicInteger(-1);
	private List<Thread> threadList;
	private String[] content;
	private String word;
	private WordCounter.Callback callback;

	public int getCount() {
		return count.get();
	}

	public void setContent(String[] content) {
		this.content = content;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setCallback(WordCounter.Callback callback) {
		this.callback = callback;
	}

	public ThreadedWordCounter(int concurrencyFactor) {
		super(concurrencyFactor);
		this.count = new AtomicInteger(0);
		this.threadList = new ArrayList<Thread>(concurrencyFactor);
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					int curline = line.addAndGet(1);
					if (curline < content.length) {
						Pattern pattern = Pattern.compile(word,
								Pattern.CASE_INSENSITIVE);
						Matcher matcher = pattern.matcher(content[curline]);
						int appears = 0;
						while (matcher.find()) {
							appears++;
						}
						callback.counted(count.addAndGet(appears));
					} else
						break;
				}
			}
		};
		for (int i = 0; i < concurrencyFactor; ++i) {
			Thread t = new Thread(runnable);
			threadList.add(t);
		}
	}

	@Override
	public void count(String fileContents, String word, Callback callback) {
		// TODO - implement this class using Thread objects; one Thread per
		// {@link #concurrencyFactor}
		// HINT - break up {@linkplain fileContents} and distribute the work
		// across the threads
		// HINT - do not create the Thread objects in this method
		setContent(fileContents.split("\n"));
		setWord(word);
		setCallback(callback);
		for (int i = 0; i < this.getConcurrencyFactor(); ++i) {
			threadList.get(i).start();
		}
	}

	@Override
	public void stop() {
		// TODO - stop the threads
		for (int i = 0; i < threadList.size(); ++i) {
			threadList.get(i).interrupt();
		}
	}

}
