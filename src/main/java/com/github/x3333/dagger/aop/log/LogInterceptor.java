/*
 * Copyright (C) 2016 Tercio Gaudencio Filho
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package com.github.x3333.dagger.aop.log;

import com.github.x3333.dagger.aop.MethodInterceptor;
import com.github.x3333.dagger.aop.MethodInvocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

/**
 * @author Tercio Gaudencio Filho (terciofilho [at] gmail.com)
 */
public class LogInterceptor implements MethodInterceptor {

  private static final Joiner JOINER = Joiner.on(", ");

  @Override
  @SuppressWarnings("unchecked")
  public <T> T invoke(final MethodInvocation invocation) throws Throwable {
    final Logger logger = LoggerFactory.getLogger(invocation.getInstance().getClass());

    final Log log = invocation.annotation(Log.class);

    final long startTime = System.currentTimeMillis();
    final Object result = invocation.proceed();
    final long endTime = System.currentTimeMillis();

    final String format = "#{}({}): {}" + (log.trackTime() ? " in {}ms" : "");
    final Object[] arguments = new Object[] { invocation.getMethod().getName(), //
        JOINER.join(invocation.getArguments()), //
        result, //
        endTime - startTime }; // Ignored if not trackTime=true

    switch (log.value()) {
      case DEBUG:
        logger.debug(format, arguments);
        break;
      case ERROR:
        logger.error(format, arguments);
        break;
      case INFO:
        logger.info(format, arguments);
        break;
      case TRACE:
        logger.trace(format, arguments);
        break;
      case WARN:
        logger.warn(format, arguments);
        break;
      default:
        break;
    }

    return (T) result;
  }

}
