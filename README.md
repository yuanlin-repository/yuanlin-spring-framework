# yuanlin-spring-framework
抽取 Spring 框架 IOC，AOP 的核心功能实现的一个简版 Spring 框架。

## 项目结构

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
          │          │  ├─ AdvisedSupport.java
          │          │  ├─ ClassFilter.java
          │          │  ├─ MethodMatcher.java
          │          │  ├─ TargetSource.java
          │          │  ├─ advice
          │          │  │  ├─ Advice.java
          │          │  │  ├─ AfterAdvice.java
          │          │  │  ├─ AfterReturningAdvice.java
          │          │  │  ├─ BeforeAdvice.java
          │          │  │  ├─ MethodBeforeAdvice.java
          │          │  │  ├─ ThrowsAdvice.java
          │          │  │  └─interceptor
          │          │  │        ├─ AfterReturningAdviceInterceptor.java
          │          │  │        ├─ interceptor.java
          │          │  │        ├─ MethodBeforeAdviceInterceptor.java
          │          │  │        ├─ MethodInterceptor.java
          │          │  │        └─ ThrowsAdviceInterceptor.java
          │          │  ├─ advisor
          │          │  │    ├─ Advisor.java
          │          │  │    ├─ AspectJExpressionPointcutAdvisor.java
          │          │  │    └─ PointcutAdvisor.java
          │          │  ├─ autoproxy
          │          │  │    ├─ AbstractAutoProxyCreator.java
          │          │  │    ├─ BeanFactoryAdvisorRetrievalHelper.java
          │          │  │    └─ DefaultAdvisorAutoProxyCreator.java
          │          │  ├─ exception
          │          │  │    └─ AopInvocationException.java
          │          │  ├─ invocation
          │          │  │    ├─ ProxyMethodInvocation.java
          │          │  │    └─ ReflectiveMethodInvocation.java
          │          │  ├─ pointcut
          │          │  │    ├─ AspectJExpressionPointcut.java
          │          │  │    └─ Pointcut.java
          │          │  ├─ proxy
          │          │  │    ├─ AopProxy.java
          │          │  │    ├─ AopProxyFactory.java
          │          │  │    ├─ CglibAopProxy.java
          │          │  │    ├─ DefaultAopProxyFactory.java
          │          │  │    ├─ JdkDynamicAopProxy.java
          │          │  │    └─ ProxyFactory.java
          │          │  └─ targetsource
          │          │        ├─ EmptyTargetSource.java
          │          │        └─ SingletonTargetSource.java
          │          ├─beans
          │          │  ├─ BeanWrapper.java
          │          │  ├─ PropertyAccessor.java
          │          │  │
          │          │  ├─ exception
          │          │  │    ├─ BeanCreationException.java
          │          │  │    ├─ BeanCurrentlyInCreationException.java
          │          │  │    ├─ BeanDefinitionStoreException.java
          │          │  │    ├─ BeanIsNotAFactoryException.java
          │          │  │    ├─ BeanNotOfRequiredTypeException.java
          │          │  │    ├─ BeansException.java
          │          │  │    └─ NoSuchBeanDefinitionException.java
          │          │  └─ factory
          │          │      ├─ AutowireCapableBeanFactory.java
          │          │      ├─ BeanFactory.java
          │          │      ├─ FactoryBean.java
          │          │      ├─ ListableBeanFactory.java
          │          │      ├─ annotation
          │          │      │    ├─ Autowired.java
          │          │      │    ├─ Qualifier.java
          │          │      │    └─ Value.java
          │          │      ├─ config
          │          │      │    ├─ BeanDefinition.java
          │          │      │    ├─ BeanDefinitionHolder.java
          │          │      │    ├─ GenericBeanDefinition.java
          │          │      │    ├─ PropertyValue.java
          │          │      │    ├─ PropertyValues.java
          │          │      │    └─ RuntimeBeanReference.java
          │          │      ├─ io
          │          │      │    ├─ Resource.java
          │          │      │    ├─ ResourceLoader.java
          │          │      │    └─ UrlResource.java
          │          │      ├─ lifecycle
          │          │      │  ├─ InitializingBean.java
          │          │      │  ├─ aware
          │          │      │  │    ├─ Aware.java
          │          │      │  │    ├─ BeanFactoryAware.java
          │          │      │  │    └─ BeanNameAware.java
          │          │      │  └─ processor
          │          │      │        ├─ BeanPostProcessor.java
          │          │      │        └─ InstantiationAwareBeanPostProcessor.java
          │          │      └─ support
          │          │            ├─ AbstractBeanDefinitionReader.java
          │          │            ├─ AbstractBeanFactory.java
          │          │            ├─ BeanDefinitionReader.java
          │          │            ├─ DefaultListableBeanFactory.java
          │          │            └─ XmlBeanDefinitionReader.java
          │          └─ context
          │              ├─ ApplicationContext.java
          │              ├─ annotation
          │              │    ├─ Lazy.java
          │              │    └─ Scope.java
          │              ├─ stereotype
          │              │    ├─ Component.java
          │              │    ├─ Repository.java
          │              │    └─ Service.java
          │              └─ support
          │                    ├─ AbstractApplicationContext.java
          │                    └─ ClassPathXmlApplicationContext.java
          └─ resources
                ├─ spring-annotation.xml
                ├─ spring-aop.xml
                └─ spring.xml

```
