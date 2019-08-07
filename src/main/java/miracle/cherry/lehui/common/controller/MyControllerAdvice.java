package miracle.cherry.lehui.common.controller;

import miracle.cherry.lehui.common.tools.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-16 16:29
 * @Modified:
 * @Description:
 */
@ControllerAdvice  //不指定包默认加了@Controller和@RestController都能控制
public class MyControllerAdvice {

    /**
     * 全局异常处理，反正异常返回统一格式的map
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public String exceptionHandler(Exception ex){
        ex.printStackTrace();
        return new Result(Result.ERROR,ex.toString(),"程序异常请联系管理员").toJson();
    }
}
