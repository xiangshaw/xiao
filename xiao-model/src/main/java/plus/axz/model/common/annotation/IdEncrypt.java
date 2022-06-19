package plus.axz.model.common.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiaoxiang
 * @date 2022年06月17日
 *
 * jackson序列化和反序列化:
 * 当后端响应给前端的数据中包含了id或者特殊标识（可自定义）的时候，把当前数据进行转换为String类型
 * 当前端传递后后端的dto中有id或者特殊标识（可自定义）的时候，把当前数据转为Integer或Long类型。
 * 特殊标识类说明：
 * IdEncrypt 自定义注解 作用在需要转换类型的字段属性上，用于非id的属性上 在model包下
 */
@JacksonAnnotation
@Retention(RetentionPolicy.RUNTIME) // 运行时的注解，
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})// 作用在方法（成员变量、参数、实体类、dto）上
public @interface IdEncrypt {
}
