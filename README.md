# dagger-aop-logger

***dagger-aop-logger*** will log to SLF4J everytime a method annotated with `@Log` is called. It can track how long it took to execute the method.

## Usage

If using Maven, add a dependency:

```xml
  <dependency>
      <groupId>com.github.0x3333.dagger.aop</groupId>
      <artifactId>dagger-aop-logger</artifactId>
      <version>1.0-SNAPSHOT</version>
  </dependency>
```

Annotate your methods with `@Log`.

```java
public abstract class MyClass implements MyService {

  @Log(value = Log.Level.WARN, trackTime = false)
  public String doSomething() {
    return some.doWork();
  }

}
```

Add to you Dagger Module the `InterceptorModule` and a `@Provides` for `LogInterceptor`. ***This will change in a next release. I'm still studing how to avoid this.***
```java
// Adding InterceptorModule
@Module(includes = { InterceptorModule.class })
public abstract class MyModule {

  @Binds
	MyService providesMyService(MyClass impl);
	
  @Provides
  static LogInterceptor providesLogInterceptor() {
    return new LogInterceptor();
  }

}
```

Now, everytime you call `#doSomething()` you will get logged:

```
20:18:06.417 [main] WARN com.github.x3333.dagger.aop.log.Interceptor_MyClass - #doSomething(): workDone in 12ms
```

License
-------

    Copyright (C) 2016 Tercio Gaudencio Filho

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
