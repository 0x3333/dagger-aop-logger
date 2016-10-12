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

import com.github.x3333.dagger.aop.InterceptorHandler;

import java.lang.annotation.Annotation;

import com.google.auto.service.AutoService;

/**
 * Method Interceptor Handler for {@link Log} and {@link LogInterceptor}.
 * 
 * <p>
 * This binds both and is registered as {@link InterceptorHandler} so can be used by the dagger-aop processor.
 * 
 * @author Tercio Gaudencio Filho (terciofilho [at] gmail.com)
 */
@AutoService(InterceptorHandler.class)
public class LogInterceptorHandler implements InterceptorHandler {

  @Override
  public Class<? extends Annotation> annotation() {
    return Log.class; // The Annotation
  }

  @Override
  public Class<LogInterceptor> methodInterceptorClass() {
    return LogInterceptor.class; // The MethodInterceptor itself
  }

}
