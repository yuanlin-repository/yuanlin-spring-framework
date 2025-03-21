# yuanlin-spring-framework
After reading the Spring source code, implement the core functions of Spring framework IOC and AOP.

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
