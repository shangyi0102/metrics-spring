/**
 * Copyright (C) 2012 Ryan W Tenney (ryan@10e.us)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ryantenney.metrics.spring.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import io.dropwizard.metrics.servlets.HealthCheckServlet;

import io.dropwizard.metrics.MetricRegistry;
import io.dropwizard.metrics.health.HealthCheckRegistry;
import io.dropwizard.metrics.servlets.MetricsServlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MetricsServletsContextListener implements ServletContextListener {

	@Autowired
	private MetricRegistry metricRegistry;

	@Autowired
	private HealthCheckRegistry healthCheckRegistry;

	private final MetricsServletContextListener metricsServletContextListener = new MetricsServletContextListener();
	private final HealthCheckServletContextListener healthCheckServletContextListener = new HealthCheckServletContextListener();

	@Override
	public void contextInitialized(ServletContextEvent event) {
		WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);

		metricsServletContextListener.contextInitialized(event);
		healthCheckServletContextListener.contextInitialized(event);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {}

	class MetricsServletContextListener extends MetricsServlet.ContextListener {

		@Override
		protected MetricRegistry getMetricRegistry() {
			return metricRegistry;
		}

	}

	class HealthCheckServletContextListener extends HealthCheckServlet.ContextListener {

		@Override
		protected HealthCheckRegistry getHealthCheckRegistry() {
			return healthCheckRegistry;
		}

	}

}
