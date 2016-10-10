import com.ywkj.nest.core.exception.CustomException;
import com.ywkj.nest.ddd.IUnitOfWork;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Jove on 2016/9/28.
 */
public class SpringAspect {
    @Autowired
    IUnitOfWork unitOfWork;

    public Object aroundMethod(ProceedingJoinPoint joinpoint) {
        Object result=null;

        try {
            result = joinpoint.proceed();
            unitOfWork.commit();

        } catch (Throwable throwable) {
            throwable.printStackTrace();
            unitOfWork.rollback();
        }
        return result;
    }
}
