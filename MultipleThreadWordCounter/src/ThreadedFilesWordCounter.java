

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;


public class ThreadedFilesWordCounter extends AbstractConcurrencyFactorProvider
		implements FilesWordCounter {

	private List<Thread> threadList;
	private Map<String, String> files;
	private String word;
	private FilesWordCounter.Callback callback;
	private String[] fileNames;
	AtomicInteger filenum = new AtomicInteger(-1);

	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}

	public void setCallback(FilesWordCounter.Callback callback) {
		this.callback = callback;
	}

	public void setFiles(Map<String, String> files) {
		this.files = files;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public ThreadedFilesWordCounter(int concurrencyFactor) {
		super(concurrencyFactor);
		this.threadList = new ArrayList<Thread>(concurrencyFactor);
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					String fileName = null;
					ThreadedWordCounter threadWordCounter = null;
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
								threadWordCounter = new ThreadedWordCounter(
										concurrencyFactor);
								threadWordCounter.count(fileContents, word,
										new WordCounter.Callback() {

											@Override
											public void counted(long count) {
								
												latch.countDown();
											}
										});
							}
							latch.await();
							if (threadWordCounter != null) {
								callback.counted(fileName,
										threadWordCounter.getCount());
							}
						} else
							break;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						Thread.currentThread().interrupt();
						e.printStackTrace();
					} finally {
						if (threadWordCounter != null)
							threadWordCounter.stop();
					}
				}
			}
		};
		for (int i = 0; i < concurrencyFactor; ++i) {
			Thread t = new Thread(runnable);
			threadList.add(t);
		}
	
	}

	@Override
	public void count(Map<String, String> files, String word, Callback callback) {
		// TODO - implement this class using Thread objects; one Thread per
		
		setFiles(files);
		setFileNames(files.keySet().toArray(new String[files.size()]));
		setCallback(callback);
		setWord(word);
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
