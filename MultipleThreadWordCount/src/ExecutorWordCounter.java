

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ExecutorWordCounter extends AbstractConcurrencyFactorProvider
		implements WordCounter {

	private AtomicInteger count;
	private ExecutorService executor;

	public int getCount() {
		return count.get();
	}

	public ExecutorWordCounter(int concurrencyFactor) {
		super(concurrencyFactor);
		this.count = new AtomicInteger(0);
		this.executor = Executors.newFixedThreadPool(concurrencyFactor);
	}

	@Override
	public void count(String fileContents, String word, Callback callback) {
		// TODO - implement this class using calls to an ExecutorService; one
		// call per {@link #concurrencyFactor}
		// HINT - break up {@linkplain fileContents} and distribute the work
		// across the calls
		// HINT - do not create the ExecutorService object in this method
		final String[] content = fileContents.split("\n");
		AtomicInteger line = new AtomicInteger(-1);
		for (int i = 0; i < this.getConcurrencyFactor(); ++i) {
			executor.execute(new Runnable() {

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
			});
		}

	}

	@Override
	public void stop() {
		// TODO - stop the executor
		executor.shutdown();
	}
}
