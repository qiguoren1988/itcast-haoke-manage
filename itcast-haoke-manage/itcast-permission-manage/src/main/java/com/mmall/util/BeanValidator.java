package com.mmall.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mmall.exception.paramException;
import org.apache.commons.collections.MapUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * @author solang
 * 在接口处请求进来的时候使用注解的方式对 Java Bean （单个类参数）进行约束验证
 * @date 2020-01-09 9:20
 */
public class BeanValidator {

    //定义全局Validator工厂
    public static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    /*
     * 孙延楠
     * @Param: [t：可以传入很多类型进来, groups：回头再讲]
     * @Return: Map<Key, Value>：Key：相当于告知哪个字段有问题，Value：有什么问题
     * @Author: Administrator
     * @Date: 2020/1/9 9:38
     */
    public static <T> Map<String, String> validate(T t, Class... groups) {
        //首先从工厂中获取validator
        Validator validator = validatorFactory.getValidator();
        //拿到validator之后可以自动的获取校验结果,通常只需要传入类型即可
        Set validateResult = validator.validate(t, groups);
        //isEmpty()：判断某种容器是否有元素,默认：false
        if (validateResult.isEmpty()) {
            //空值的话就直接return空的emptyMap
            return Collections.emptyMap();
        } else {
            //划重点：将里面值输出
            LinkedHashMap errors = Maps.newLinkedHashMap();
            Iterator iterator = validateResult.iterator();
            //hasNext():判断是否存在下一个元素,如有值则取出
            while (iterator.hasNext()) {
                ConstraintViolation violation = (ConstraintViolation) iterator.next();
                //封装校验数据
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            //返回时可以看到Map中key：有问题的字段，value：错误信息
            return errors;
        }
    }

    /*
     * 孙延楠
     * @Param: [conllection：List父类，用来囊括各种list用的]
     * @Return: java.util.Map<java.lang.String,java.lang.String>：key：相当于告知哪个字段有问题，value：有什么问题
     * @Author: Administrator
     * @Date: 2020/1/9 10:07
     */
    public static Map<String, String> validateList(Collection<?> conllection) {
        //首先判断当前传入的conllection是否为空？
        Preconditions.checkNotNull(conllection);
        Map errors;
        //继续遍历当前conllection
        Iterator<?> iterator = conllection.iterator();
        do {
            if (!iterator.hasNext()) {
                return Collections.emptyMap();
            }
            Object object = iterator.next();
            //new Class[0]：为了参数跟类一致,能符合API的调用
            errors = validate(object, new Class[0]);
        } while (errors.isEmpty());
        return errors;
    }

    /*
     * 孙延楠
     * 目标：任何校验使用这个方法就能囊括
     * @Param: [first, objects：object数组]
     * @Return: java.util.Map<java.lang.String,java.lang.String>
     * @Author: Administrator
     * @Date: 2020/1/9 10:29
     */
    public static Map<String, String> validateObject(Object first, Object... objects) {
        if (objects != null && objects.length > 0) {
            return validateList(Lists.asList(first, objects));
        } else {
            return validate(first, new Class[0]);
        }
    }

    public static void check(Object param){
        Map<String, String> map = BeanValidator.validateObject(param);
        if(MapUtils.isNotEmpty(map)){
            throw new paramException(map.toString());
        }
    }
}