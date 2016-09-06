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

import static com.github.x3333.dagger.aop.log.Log.Level.DEBUG;
import static com.github.x3333.dagger.aop.log.Log.Level.ERROR;

/**
 * @author Tercio Gaudencio Filho (terciofilho [at] gmail.com)
 */
public abstract class MyClass implements MyInterface {

  private final SomeDependency some;

  public MyClass(final SomeDependency some) {
    this.some = some;
  }

  @Override
  @Log(value = DEBUG, trackTime = false)
  public void logDoSomething() {
    some.doWork();
  }

  @Override
  @Log(value = ERROR, trackTime = true)
  public void logDoSomethingLong() {
    try {
      Thread.sleep(1000);
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }
  }

}
