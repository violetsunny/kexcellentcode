package top.kexcellent.back.code.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DefaultThreadPool {

	private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(5, 10, 2000, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.AbortPolicy());

	//静态内部类写单例
	private static final class DefaultThreadPoolThreadPoolHolder {
		private static final DefaultThreadPool INSTANCE = new DefaultThreadPool();
	}

	public static DefaultThreadPool getInstance() {
		return DefaultThreadPoolThreadPoolHolder.INSTANCE;
	}

	/**
	 * 获取线程池
	 *
	 * @return
	 */
	public ThreadPoolExecutor getExecutor() {
		return EXECUTOR;
	}

	/**
	 * 执行线程
	 *
	 * @param command
	 */
	public void execute(Runnable command) {
		EXECUTOR.execute(command);
	}
}