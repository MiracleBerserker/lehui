package miracle.cherry.lehui.common.tools;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-16 15:04
 * @Modified:
 * @Description:
 */


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 通用信息传递机制类，定义了处理后的结果状态以及用来传递数据的对象
 *
 * @author heshun
 * @version 2.0 李其云 2017.11.16 增加toJson的directCopyStringData参数，以避免已经是json串的属性再次被序列化使得结果多出一个引号导致angularjs（其他js未测试）无法将该json串属性转换为对象；增加toJson的dateFormat参数，以将日期转为为所需的字符串格式；
 */
public class Result {
    protected static final Logger logger = LoggerFactory.getLogger(Result.class);

    public static SerializerFeature[] DEFAULT_SERIALIZER_FEATURE = {SerializerFeature.WriteMapNullValue};
    public static SerializerFeature[] None_FEATURE = new SerializerFeature[0];
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 状态：成功
     */
    public static final String SUCCESS = "success";
    /**
     * 状态：错误 因为逻辑错误（如邮箱已存在，年龄不满足条件等）返回BusinessException时。
     */
    public static final String ERROR = "error";
    /**
     * 状态：失败 ，返回非BusinessException（未明确处理的异常）时，或者一些不可预计的、系统级的错误导致的操作失败，如数据库断电，服务器内存溢出等。
     */
    public static final String FAIL = "fail";

    /**
     * 状态，若状态是成功/失败/错误，则采用ReturnValue.SUCCESS、FAILED、ERROR。若非这3种状态，请自行定义。
     */
    private String status;
    /**
     * 数据，可为对象、对象集合。
     */
    private Object data;
    /**
     * 执行结果信息
     */
    private String message;
    /**
     * 执行异常时的堆栈信息。
     */
    private String stackTraceMessage;

    /**
     * 无参构造器
     */
    public Result() {
        super();
    }

    /**
     * 正常用构造器
     *
     * @param status  状态
     * @param data    数据
     * @param message 信息
     */
    public Result(String status, Object data, String message, String stackTraceMessage) {
        super();
        setStatus(status);
        setData(data);
        setMessage(message);
        setStackTraceMessage(stackTraceMessage);
    }

    /**
     * 正常用构造器
     *
     * @param status 状态
     */
    public Result(String status) {
        this(status, null, null, null);
    }

    /**
     * 正常用构造器
     *
     * @param status 状态
     * @param data   数据
     */
    public Result(String status, Object data) {
        this(status, data, null, null);
    }

    /**
     * 正常用构造器
     *
     * @param status 状态
     * @param data   数据
     */
    public Result(String status, Object data, String message) {
        this(status, data, message, null);
    }

    /**
     * 异常用构造器
     *
     * @param status 状态
     * @param e      异常
     */


    /**
     * 使用默认特性将自身转换为JSON字符串
     * <p>
     * 该字符串返回客户端后采用
     * eval()或其他方法将该字符串转换成JSON对象，采用该JSON对象.status、JSON对象.data即可获取状态和信息。
     * </p>
     *
     * @return 封装好的JSON字符串
     */
    public String toJson() {
        return toJson(DEFAULT_DATE_FORMAT, DEFAULT_SERIALIZER_FEATURE);
    }

    public String toJsonWithoutNull() {
        return toJson(DEFAULT_DATE_FORMAT, None_FEATURE);
    }

    public String toJson(SerializerFeature... features) {
        return toJson(DEFAULT_DATE_FORMAT, features);
    }

    public String toJson(boolean directCopyStringData) {
        return toJson(directCopyStringData, DEFAULT_DATE_FORMAT, DEFAULT_SERIALIZER_FEATURE);
    }

    public String toJson(String dateFormat) {
        return toJson(dateFormat, DEFAULT_SERIALIZER_FEATURE);
    }

    public String toJson(boolean directCopyStringData, String dateFormat) {
        return toJson(directCopyStringData, dateFormat, DEFAULT_SERIALIZER_FEATURE);
    }

    public String toJson(boolean directCopyStringData, SerializerFeature... features) {
        return toJson(directCopyStringData, DEFAULT_DATE_FORMAT, features);
    }

    public String toJson(String dateFormat, SerializerFeature... features) {
        return JSON.toJSONStringWithDateFormat(this, dateFormat, features);
    }

    /**
     * 使用自定义特性将自身转换为JSON字符串
     * <p>常用特性如下：
     * SerializerFeature.DisableCircularReferenceDetect
     * SerializerFeature.PrettyFormat
     * SerializerFeature.UseSingleQuotes
     * SerializerFeature.WriteNullBooleanAsFalse
     * SerializerFeature.WriteNullStringAsEmpty
     * SerializerFeature.WriteNullListAsEmpty
     * SerializerFeature.WriteNullNumberAsZero
     * </p>
     *
     * @param features
     * @return
     */
    public String toJson(boolean directCopyStringData, String dateFormat, SerializerFeature... features) {
        //忽略字段
//        PropertyFilter profilter = new PropertyFilter(){
//            @Override
//            public boolean apply(Object object, String name, Object value) {
//                if(name.equals("data") && object instanceof String){
//                    //false表示字段将被排除在外
//                    return false;
//                }
//                return true;
//            }
//
//        };
        String strResult;
        if (directCopyStringData && getData() instanceof String) {
            String strData = ((String) getData()).trim();
            String qutote = strData.startsWith("{") && strData.endsWith("}") || strData.startsWith("[") && strData.endsWith("]") ? "" : "\"";
            strData = qutote + strData + qutote;
            setData(null);
            if (StringUtils.isEmpty(dateFormat))
                strResult = JSON.toJSONString(this, features);
            else
                strResult = JSON.toJSONStringWithDateFormat(this, dateFormat, features);
            return "{\"data\":" + strData + "," + strResult.substring(1);
        } else {
            if (StringUtils.isEmpty(dateFormat))
                strResult = JSON.toJSONString(this, features);
            else
                strResult = JSON.toJSONStringWithDateFormat(this, dateFormat, features);
        }
        return strResult;
    }

    /**
     * 获取状态<br/>
     * 如果状态为系统定义的成功/失败/错误，则直接调用ReturnValue.SUCCESS、FAILED、ERROR得到状态值。<br/>
     * 如果状态为用户传入的自定义值，则直接返回该值<br/>
     *
     * @return 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态<br/>
     * 可传入系统定义的三种状态值(成功/失败/错误),调用方式：ReturnValue.SUCCESS、FAILED、ERROR<br/>
     * 也可自定义状态值<br/>
     *
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取信息,信息类型：<br>
     * 1.从异常中提取出的错误信息。<br>
     * 2.自定义的信息。<br>
     * 3.对象、对象集合。<br>
     *
     * @return 信息
     */
    public Object getData() {
        return data;
    }

    /**
     * 设置信息,信息类型：<br>
     * 1.从异常中提取出的错误信息。<br>
     * 2.自定义的信息。<br>
     * 3.对象、对象集合。<br>
     *
     * @param data 信息
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the stackTraceMessage
     */
    public String getStackTraceMessage() {
        return stackTraceMessage;
    }

    /**
     * @param stackTraceMessage the stackTraceMessage to set
     */
    public void setStackTraceMessage(String stackTraceMessage) {
        this.stackTraceMessage = stackTraceMessage;
    }

    public static String success() {
        return (new Result(SUCCESS)).toJson();
    }

    public static String successWithDateFormat(String dateFormat) {
        return (new Result(SUCCESS)).toJson(dateFormat);
    }

    public static String success(Object data) {
        return (new Result(SUCCESS, data)).toJson();
    }

    public static String successWithDateFormat(Object data, String dateFormat) {
        return (new Result(SUCCESS, data)).toJson(dateFormat);
    }

    public static String success(Object data, boolean directCopyStringData) {
        return (new Result(SUCCESS, data)).toJson(directCopyStringData);
    }

    public static String successWithDateFormat(Object data, boolean directCopyStringData, String dateFormat) {
        return (new Result(SUCCESS, data)).toJson(directCopyStringData, dateFormat);
    }

    public static String success(Object data, String message) {
        return (new Result(SUCCESS, data, message)).toJson();
    }

    public static String successWithDateFormat(Object data, String message, String dateFormat) {
        return (new Result(SUCCESS, data, message)).toJson(dateFormat);
    }

    public static String success(Object data, String message, boolean directCopyStringData) {
        return (new Result(SUCCESS, data, message)).toJson(directCopyStringData);
    }

    public static String successWithDateFormat(Object data, String message, boolean directCopyStringData, String dateFormat) {
        return (new Result(SUCCESS, data, message)).toJson(directCopyStringData, dateFormat);
    }

    public static String success(Object data, String message, boolean directCopyStringData, SerializerFeature... features) {
        return (new Result(SUCCESS, data, message)).toJson(directCopyStringData, features);
    }

    public static String successWithDateFormat(Object data, String message, boolean directCopyStringData, String dateFormat, SerializerFeature... features) {
        return (new Result(SUCCESS, data, message)).toJson(directCopyStringData, dateFormat, features);
    }

    public static String error(String message) {
        return (new Result(ERROR, null, message)).toJson();
    }

    public static String fail(String message) {
        return (new Result(FAIL, null, message)).toJson();
    }

}
