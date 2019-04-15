# Metrofit

The power of Retrofit and the speed of Rx combine to give life to Metrofit!
Metrofit is a lightweight and easy to use library!
Perform your HTTP requests easily and effectively!

Metrofit is a Java library that also uses Gson to convert a JSON string to an equivalent Java object.

### Goals
  * Provide simple `HTTP` methods to communicate with a REST server
  * Be easy to use
  * Extensive support of Java Generics
  * Allow custom representations for objects
  * Few code
  * Fast
  * Support arbitrarily complex objects (with deep inheritance hierarchies and extensive use of generic types)

### Download

Gradle:

Step 1. Add it in your root build.gradle at the end of repositories :
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
Step 2. Add the dependency in app/build.gradle :

```
dependencies {
        implementation 'com.github.fl0wo:Metrofit:0.0.1'
}
```

Maven:
```xml
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

	<dependency>
	    <groupId>com.github.fl0wo</groupId>
	    <artifactId>Metrofit</artifactId>
	    <version>Tag</version>
	</dependency>
```

[Metrofit jar]() will be soon avaible from Maven Central.

### Documentation
 
 Init
 ```
  Metrofit<PojoService> m = new Metrofit(PojoService.class,PojoService.BASE_URL);
 ```
 
 Make a HTTP GET request
 
```
m.doThis(m.pojo().getById(2))
              .then(result -> {
                  System.out.println("Obj n " + 2 + " : " + response);
              })
              .handleError(result -> {
                  System.err.println(((Throwable) result).getMessage());
              })
              .bind();
  ```
  Or 
  
  
  ```
   m.doThis(m.pojo().getById(3))
                  .handle(new ResponseHandler<Response>() {
                      @Override
                      public void handleError(Throwable t) {
                          System.err.println(t.getMessage());
                      }

                      @Override
                      public void handleResult(Response result) {
                          System.out.println("Obj n " + 2 + " : " + result);
                      }
                  })
                  .bind();
  ```
Works with any type of result!

```

   m.doThis(m.pojo().getAll())
                  .then(result -> {

                          List<Response> responses = (List<Response>) result;

                          int i =0;
                          for(Response r : responses) {
                              System.out.println("Obj n " + (i++) + " : " + r);
                          }

                      })
                  .handleError(result -> {
                      System.err.println(((Throwable) result).getMessage());
                  })
                  .bind();
```
 
Please use the 'metrofit' tag on StackOverflow to discuss Metrofit or to post questions.

### Related Content Created by Third Parties
  nothing yet
  email me if you used this library in your projects :)

### License

Gson is released under the [Flo 2.0 license](LICENSE).

```
Copyright 2008 Flo Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

### Disclaimer

This is an officially supported Flo product.

### Contact

email : fsabani00@gmail.com


