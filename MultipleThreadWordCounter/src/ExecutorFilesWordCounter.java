

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


public class ExecutorFilesWordCounter extends AbstractConcurrencyFactorProvider
		implements FilesWordCounter {

	private ExecutorService executor;

	public ExecutorFilesWordCounter(int concurrencyFactor) {
		super(concurrencyFactor);
		this.executor = Executors.newFixedThreadPool(concurrencyFactor);
	}

	@Override
	public void count(Map<String, String> files, String word, Callback callback) {
		// TODO - implement this class using calls to an ExecutorService; with

		String[] fileNames = files.keySet().toArray(new String[files.size()]);
		AtomicInteger filenum = new AtomicInteger(-1);
		int concurrencyFactor = this.getConcurrencyFactor();
		for (int i = 0; i < concurrencyFactor; ++i) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while (true) {
						String fileName = null;
						ExecutorWordCounter executorWordCounter = null;
						try {
							int currentFileNum = filenum.addAndGet(1);
							if (currentFileNum < fileNames.length) {
								fileName = fileNames[currentFileNum];
								String fileContents = null;
								fileContents = files.get(fileName);
								String[] splits = fileContents.split("\n");
								CountDownLatch latch = new CountDownLatch(
										splits.length);
								if (fileContents != null) {
									executorWordCounter = new ExecutorWordCounter(
											concurrencyFactor);
									executorWordCounter.count(fileContents,
											word, new WordCounter.Callback() {

												@Override
												public void counted(long count) {

													latch.countDown();
												}
											});
								}
								latch.await();
								if (executorWordCounter != null) {
									callback.counted(fileName,
											executorWordCounter.getCount());
								}
							} else
								break;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							Thread.currentThread().interrupt();
							e.printStackTrace();
						} finally {
							if (executorWordCounter != null)
								executorWordCounter.stop();
						}
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
