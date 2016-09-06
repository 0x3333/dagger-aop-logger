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

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Methods annotated by {@link Log} will generate a SLF4J entry every time the method has been called.
 *
 * <p>
 * You can define the logger level using {@link #value() #value(Log.Level.XXXXX)}.
 * 
 * <p>
 * It can also track how long it took to run by {@link #trackTime() #trackTime(true)}.
 * 
 * @author Tercio Gaudencio Filho (terciofilho [at] gmail.com)
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface Log {

  /**
   * Represent a Level used by Logger.
   */
  public static enum Level {
    DEBUG, ERROR, INFO, TRACE, WARN;
  }

  /**
   * {@link Level} that will be used to log the method access.
   * 
   * @return LogLevel
   */
  Level value() default Level.TRACE;

  /**
   * If Interceptor should track time when invoking the method.
   * 
   * @return boolean
   */
  boolean trackTime() default true;

}
