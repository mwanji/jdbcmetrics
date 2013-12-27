/******************************************************
 * JDBCMetrics
 *
 *
 * Copyright (C) 2013 by Magnus Lundberg (http://magnuslundberg.com) & Peter Hedenskog (http://peterhedenskog.com)
 *
 ******************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 *******************************************************
 */
package com.soulgalore.jdbcmetrics;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.Timer;

/**
 * Class responsible for holding all the Yammer Metrics.
 * The default MetricRegistry is used by default, if you want a new to be created
 * add a System property
 * <em>com.soulgalore.jdbcmetrics.MetricRegistry</em> with the value <em>new</em>.
 *
 */
public class JDBCMetrics {

	public static final String REGISTRY_PROPERTY_NAME = "com.soulgalore.jdbcmetrics.MetricRegistry";

	private interface MetricGrouper {
	  Timer getReadTimerForQuery(String sql);
	}

	private static final MetricGrouper NOOP_METRIC_GROUPER = new MetricGrouper() {
	  private final Timer readTimer = new Timer();

    @Override
    public Timer getReadTimerForQuery(String sql) {
      return readTimer;
    }
	};

	private static final String GROUP = "jdbc";
	private static final String GROUP_POOL = "connectionpool";
	private static final String TYPE_READ = "read";
	private static final String TYPE_WRITE = "write";
	private static final String TYPE_READ_OR_WRITE = "readorwrite";

	private final MetricRegistry registry;

	private final Histogram readCountsPerRequest;
	private final Histogram writeCountsPerRequest;

	private final Timer writeTimer;
	private final Timer readTimer;
	private final Timer writeTimerPerRequest;
	private final Timer readTimerPerRequest;
	private final Timer connectionPoolTimer;

	private final MetricGrouper metricGrouper;

	private static final JDBCMetrics INSTANCE = new JDBCMetrics();

	private JDBCMetrics() {
		Properties properties = new Properties();
		InputStream propertiesStream = getClass().getResourceAsStream("/jdbcmetrics.properties");
		if (propertiesStream != null) {
		  try {
		    properties.load(propertiesStream);

		  } catch (Exception e) {
		    throw new RuntimeException(e);
		  } finally {
		    try {
		      propertiesStream.close();
		    } catch (IOException e) {
		      // ignore
		    }
		  }
		}

		String propertyValue = System.getProperty(REGISTRY_PROPERTY_NAME);
		if (propertyValue == null) {
		  propertyValue = properties.getProperty("metricRegistry.name");
		}

		if (propertyValue != null)
			registry = SharedMetricRegistries.getOrCreate(propertyValue);
		else
			registry = new MetricRegistry();

		readCountsPerRequest = registry.histogram(createName(
				GROUP, TYPE_READ, "read-counts-per-request"));

		 writeCountsPerRequest = registry.histogram(createName(
					GROUP, TYPE_WRITE, "write-counts-per-request"));

		 writeTimer = registry.timer(createName(GROUP,
					TYPE_WRITE, "write-time"));

		 readTimer = registry.timer(createName(GROUP,
					TYPE_READ, "read-time"));


		 if (Boolean.parseBoolean(properties.getProperty("timer.perQuery", Boolean.FALSE.toString()))) {
		   this.metricGrouper = new MetricGrouper() {
		     private final ConcurrentMap<String, Timer> timers = new ConcurrentHashMap<String, Timer>();

		     @Override
		     public Timer getReadTimerForQuery(String sql) {
		       if (sql != null && !timers.containsKey(sql)) {
		         timers.put(sql, registry.timer(createName(GROUP, "query", sql)));
		       }

		       return timers.get(sql);
		     }
      };
		 } else {
		   this.metricGrouper = NOOP_METRIC_GROUPER;
		 }

		 writeTimerPerRequest = registry.timer(createName(GROUP,
					TYPE_WRITE, "write-time-per-request"));

		 readTimerPerRequest = registry.timer(createName(GROUP,
					TYPE_READ, "read-time-per-request"));

		 connectionPoolTimer = registry.timer(createName(GROUP_POOL,
		     TYPE_READ_OR_WRITE, "wait-for-connection"));
	}

	/**
	 * Get the instance.
	 *
	 * @return the singleton instance.
	 */
	public static JDBCMetrics getInstance() {
		return INSTANCE;
	}


	public Histogram getReadCountsPerRequest() {
		return readCountsPerRequest;
	}

	public Histogram getWriteCountsPerRequest() {
		return writeCountsPerRequest;
	}

	public Timer getWriteTimer() {
		return writeTimer;
	}

	public Timer getReadTimer() {
		return readTimer;
	}

	public Timer getReadTimer(String sql) {
	  return metricGrouper.getReadTimerForQuery(sql);
	}

	public Timer getWaitForConnectionInPool() {
		return connectionPoolTimer;
	}

	public MetricRegistry getRegistry() {
		return registry;
	}

	public Timer getWriteTimerPerRequest() {
		return writeTimerPerRequest;
	}

	public Timer getReadTimerPerRequest() {
		return readTimerPerRequest;
	}

  private static String createName(String group, String type, String name) {
	return 	MetricRegistry.name(group,type,name);
  }
}
