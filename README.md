# yuanlin-spring-framework
Built a lightweight Java framework that replicates core Spring features including Inversion of Control (IoC) and Aspect-Oriented Programming (AOP).

- Designed an annotation-driven IoC container supporting dependency injection, lifecycle management, and custom annotations such as @Component, @Autowired, @Lazy, and @Scope.
- Implemented dynamic proxy-based AOP with modular configuration and support for multiple advice types (e.g., @Before, @After).
- Developed a simplified BeanPostProcessor to enable bean customization after instantiation.
- Gained in-depth understanding of Java reflection, annotation parsing, classpath scanning, and proxy design patterns.
## Project Structure
```
yuanlin-spring-framework
├ spring-demo
│  └─ src
│     └─ main
│        ├─ java
│        │  └─ github.yuanlin
│        │       ├─ Main.java
│        │       ├─ advice
│        │       │    ├─ LogArgsAdvice.java
│        │       │    └─ LogResultAdvice.java
│        │       ├─ model
│        │       │    ├─ Order.java
│        │       │    └─ User.java
│        │       └─ service
│        │             ├─ OrderService.java
│        │             ├─ OrderServiceImpl.java
│        │             ├─ UserService.java
│        │             └─ UserServiceImpl.java
│        └─resources
│                spring.xml
└─ spring-framework
    └─ src
       └─ main
          ├─ java
          │  └─ github.yuanlin
          │          ├─ aop
          │          ├─ beans
          │          └─ context
          └─ resources
                ├─ spring-annotation.xml
                ├─ spring-aop.xml
                └─ spring.xml

```
