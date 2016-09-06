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

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;

/**
 * @author Tercio Gaudencio Filho (terciofilho [at] gmail.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class LogTest {

  @Mock
  private Appender<ILoggingEvent> mockAppender;

  private Logger root;

  @Before
  public void setupLogger() {
    root = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
    when(mockAppender.getName()).thenReturn("Mock Appender");
    root.addAppender(mockAppender);
  }

  @After
  public void tearLogger() {
    root.detachAppender(mockAppender);
  }

  @Test
  public void testLogInterceptor() {
    final MyComponent component = DaggerMyComponent.builder().build();
    component.myInterface().logDoSomething();
    component.myInterface().logDoSomethingLong();

    verify(mockAppender).doAppend(argThat(new ArgumentMatcher<ILoggingEvent>() {
      @Override
      public boolean matches(final Object argument) {
        final LoggingEvent loggingEvent = (LoggingEvent) argument;
        return loggingEvent.getFormattedMessage().contains("did some work!") //
            && loggingEvent.getLevel() == Level.INFO;
      }
    }));
    verify(mockAppender).doAppend(argThat(new ArgumentMatcher<ILoggingEvent>() {
      @Override
      public boolean matches(final Object argument) {
        final LoggingEvent loggingEvent = (LoggingEvent) argument;
        return loggingEvent.getFormattedMessage().equals("#logDoSomething(): null") //
            && loggingEvent.getLevel() == Level.DEBUG;
      }
    }));
    verify(mockAppender).doAppend(argThat(new ArgumentMatcher<ILoggingEvent>() {
      @Override
      public boolean matches(final Object argument) {
        final LoggingEvent loggingEvent = (LoggingEvent) argument;
        final String message = loggingEvent.getFormattedMessage();
        final String time = message.substring(message.lastIndexOf(" ") + 1, message.length() - 2);
        return loggingEvent.getFormattedMessage().contains("#logDoSomethingLong(): null in ") //
            && Double.parseDouble(time) >= 1000 //
            && loggingEvent.getLevel() == Level.ERROR;
      }
    }));
  }

}
