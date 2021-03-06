package app.core.sessions;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SessionContext {

	@Autowired
	private ApplicationContext ctx; // to get new session beans
	private Map<String, Session> sessions = new ConcurrentHashMap<>(); // thread safe map
	private Timer timer = new Timer(); // needed for removal of expired sessions
	@Value("${session.remove.expired.period:20}")
	private int removeExpiredSessionsPeriod; // time between each removal task run (in seconds)

	private boolean isSessionExpired(Session session) {
		long now = System.currentTimeMillis();
		long lastAccessed = session.getLastAccessed();
		long maxInactiveInterval = session.getMaxInactiveInterval();
		return now - lastAccessed > maxInactiveInterval;
	}

	@PostConstruct
	private void init() {
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				System.out.println("removing expired sessions");//for testing
				for (String token : sessions.keySet()) {
					Session session = sessions.get(token);
					if (isSessionExpired(session)) {
						removeSession(session);
					}
				}
			}
		};

		timer.schedule(task, 5000, TimeUnit.SECONDS.toMillis(removeExpiredSessionsPeriod));
	}

	@PreDestroy
	private void destroy() {
		timer.cancel();
		System.out.println("sesion removal thread stopped");//for testing
	}

	public Session createSession() {
		Session session = ctx.getBean(Session.class);
		sessions.put(session.token, session);
		return session;
	}

	public void removeSession(Session session) {
		sessions.remove(session.token);
	}

	public Session getSession(String token) {//TODO: check if need to throw an exception instead of null
		Session session = sessions.get(token);
		if (session != null) {
			session.resetLastAccessed();
		} else {
			session = createSession();
		}
		return session;
	}

}
