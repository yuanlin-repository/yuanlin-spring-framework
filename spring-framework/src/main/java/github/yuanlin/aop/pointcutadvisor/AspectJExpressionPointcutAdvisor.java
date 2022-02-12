package github.yuanlin.aop.pointcutadvisor;

import github.yuanlin.aop.advice.Advice;
import github.yuanlin.aop.pointcut.AspectJExpressionPointcut;
import github.yuanlin.aop.pointcut.Pointcut;

/**
 * @author yuanlin
 * @date 2022/02/12/13:25
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    // 切面
    private AspectJExpressionPointcut pointcut;

    // 对方法的增强
    private Advice advice;

    // 表达式
    private String expression;

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public Pointcut getPointcut() {
        if (pointcut == null) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

    public void setPointcut(AspectJExpressionPointcut pointcut) {
        this.pointcut = pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
